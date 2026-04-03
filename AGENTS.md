# AGENTS.md — AI Agent Guide for selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **JUnit 6 (Jupiter)** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

---

## Architecture: 3-Layer Test Pattern

All tests follow a strict three-layer hierarchy — never skip layers:

```
test/          → JUnit 6 test classes (extend BaseTest, use @Feature, @Description)
  └── steps/   → Business-level steps (annotated with @Step for Allure tracing)
        └── pages/ → Selenium Page Object classes (PageFactory + @FindBy, interacts with the DOM only)
```

- **`BaseTest`** (`framework/BaseTest.java`): JUnit `@TestInstance(PER_CLASS)` — one WebDriver instance per test class.
`@BeforeAll` creates the driver; `@AfterAll` quits it. `@BeforeEach` calls `BaseTestMethods.instantiateDriver()` to self-heal a crashed driver before each test.
  Listeners are wired via `@ExtendWith` directly on this class.
- **`WebdriverManager`** (`framework/manager/WebdriverManager.java`): `ThreadLocal<WebDriver>` — essential for parallel safety. 
Always access the driver via `WebdriverManager.getDriver()`, never pass it directly.
- **`WebdriverFactory`** (`framework/manager/WebdriverFactory.java`): Switch on `configuration().envBrowser()` (case-insensitive). 
Each case sets `System.setProperty("webdriver.*.driver", ".\\drivers\\*.exe")` before instantiating the driver. Add new browsers here.

---

## WebDriver Binaries

This branch does **not** use Selenium Manager for automatic driver downloads. Binaries are committed to `./drivers/`:

| File                         | Browser           |
|------------------------------|-------------------|
| `drivers/chromedriver.exe`   | Chrome            |
| `drivers/geckodriver.exe`    | Firefox           |
| `drivers/msedgedriver.exe`   | Edge              |
| `drivers/IEDriverServer.exe` | Internet Explorer |

**Drivers must be manually updated** to match the installed browser version. Download from the browser vendor and replace the corresponding file in `./drivers/`.

The `./drivers/` folder also contains `selenium-server-4.24.0.jar` and `.toml` grid configs for running a local Selenium Grid:
```bash
java -jar drivers/selenium-server-4.24.0.jar standalone
java -jar drivers/selenium-server-4.24.0.jar standalone --config drivers/two-browsers-grid-config.toml
# Grid UI: http://localhost:4444/ui
```

## Adding a New Browser
1. Place the driver binary in `./drivers/`.
2. Add a `case` to the switch in `WebdriverFactory.createInstance()` that calls `System.setProperty(...)` with the binary path before yielding the new driver instance.

---

## Configuration

Config is managed via the **Owner** library reading `src/test/resources/general.properties`:

```properties
env.browser = Chrome       # Chrome | Firefox | Edge | IE
env.url = http://calculator.com
default.webdriver.timeout = 180
env.time.zone = Europe/Warsaw
```

`ConfigurationManager.configuration()` returns a cached singleton. **System properties override the file** at runtime:

```bash
.\gradlew.bat test -Denv.browser=Firefox -Denv.url=https://example.com
```

Never hardcode config values in tests — always call `configuration()`.

---

## Key Developer Commands (Windows)

```bash
# Run all tests (also auto-generates Allure + Excel reports)
.\gradlew.bat clean test

# Run a single test class
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.CalculatorSanityTest

# Filter by JUnit tags
.\gradlew.bat clean test -DincludeTags="Regression"
.\gradlew.bat clean test -DexcludeTags="Flaky"

# Regenerate Allure report from existing results
.\gradlew.bat allureReport

# Generate Excel report standalone (runs ReportUtils.main())
.\gradlew.bat excelReport

# Kill stale ChromeDriver processes (Windows)
taskkill /F /IM chromedriver.exe /T
```

`allureReport` and `excelReport` are automatically triggered as `finalizedBy` in `build.gradle` — they run after every `test` task.

---

## Listener & SPI Auto-Registration

`MyTestWatcher` is wired via **`@ExtendWith` on `BaseTest`** — do not add it again in subclasses.
Two additional listeners are registered via **Java SPI** (`META-INF/services/`) — do not add them with `@ExtendWith`:

| Class                      | Interface / Type                        | Purpose                                                                                               |
|----------------------------|-----------------------------------------|-------------------------------------------------------------------------------------------------------|
| `MyTestWatcher`            | `TestWatcher` + `InvocationInterceptor` | Captures screenshot + page source on failure, attaches to Allure via `@Attachment`                    |
| `ResultExecutionListener`  | `TestExecutionListener` (SPI)           | Writes Allure `environment.properties`, saves JSON results, triggers Excel generation at end of suite |
| `AllureTestListener`       | `TestLifecycleListener` (SPI)           | Console log with test name + feature on start/stop                                                    |

---

## Parallel Execution & Retry

- `maxParallelForks = 3` (Gradle JVM forks) + JUnit `parallel.config.strategy=dynamic`
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true` (a test that only passes on retry is marked as a failure)

---

## Test Annotations (JUnit 6)

```java
@Feature("My feature")                     // Allure feature grouping — on the class
public class MyTest extends BaseTest {

    @BeforeEach
    public void setUp() { steps = new MySteps(); }

    @Test
    @Description("My scenario")
    @Tag("Regression")
    public void myTest() { ... }
}
```

- Use `@Description("...")` on each test method for Allure labelling.
- Use `@Tag("Regression")` / `@Tag("Flaky")` for Gradle tag-based filtering.
- Steps object must be instantiated in `@BeforeEach`, not as a class field.

---

## Parameterized Tests & Test Data

- Inline data: `@MethodSource` with a static `Object[][]` method in the same class; reference with `@ParameterizedTest` (see `BasicOperationsTest`).
- File-driven data: `@CsvFileSource(resources = "/Division.csv")` — file lives in `src/test/resources/` (see `BasicOperationsTest.divideNumbers()`).
- Parameters are automatically captured by `MyTestWatcher.interceptTestTemplateMethod()` and stored for Excel reporting.

---

## Adding a New Browser

Add a `case` to the switch in `WebdriverFactory.createInstance()` — that is the only place to change.

---

## Reporting Outputs

| Report             | Location                                              |
|--------------------|-------------------------------------------------------|
| Allure HTML        | `build/reports/allure-report/allureReport/index.html` |
| JUnit HTML         | `build/reports/tests/test/index.html`                 |
| Excel              | `build/excel-results/executionReport_*.xlsx`          |
| Screenshots        | `build/reports/screenshots/`                          |
| Allure raw results | `build/allure-results/`                               |

---

## Key Files Reference

| File                                               | Role                                                                  |
|----------------------------------------------------|-----------------------------------------------------------------------|
| `build.gradle`                                     | JUnit parallel config, retry, tag filtering logic, report task wiring |
| `framework/BaseTest.java`                          | Required superclass; wires `MyTestWatcher` via `@ExtendWith`          |
| `framework/config/Configuration.java`              | All config keys (Owner `@Config.Key`)                                 |
| `framework/manager/WebdriverManager.java`          | ThreadLocal driver store                                              |
| `framework/listeners/MyTestWatcher.java`           | Failure screenshot + Allure attachment (`TestWatcher` + interceptor)  |
| `framework/listeners/ResultExecutionListener.java` | Suite-level reporting hook (`TestExecutionListener`)                  |
| `src/test/resources/general.properties`            | Runtime configuration                                                 |
| `src/test/resources/META-INF/services/`            | SPI registration for `ResultExecutionListener` + `AllureTestListener` |
