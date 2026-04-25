# AGENTS.md — AI Agent Guide for selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **TestNG** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

---

## Architecture: 3-Layer Test Pattern

All tests follow a strict three-layer hierarchy — never skip layers:

```
test/          → TestNG test classes (extend BaseTest, use @Feature, @Test(description="..."))
  └── steps/   → Business-level steps (annotated with @Step for Allure tracing)
        └── pages/ → Selenium Page Object classes (PageFactory + @FindBy, interacts with the DOM only)
```

- **`BaseTest`** (`framework/BaseTest.java`): One WebDriver instance per test class via `@BeforeClass(alwaysRun = true)` / `@AfterClass(alwaysRun = true)`. 
`@BeforeMethod(alwaysRun = true)` calls `BaseTestMethods.instantiateDriver()` to self-heal a crashed driver before each test. 
Listeners are wired via `@Listeners` directly on this class.
- **`WebdriverManager`** (`framework/manager/WebdriverManager.java`): `ThreadLocal<WebDriver>` — essential for parallel safety. 
Always access the driver via `WebdriverManager.getDriver()`, never pass it directly.
- **`WebdriverFactory`** (`framework/manager/WebdriverFactory.java`): Switch on `configuration().envBrowser()` (case-insensitive). 
Relies on **Selenium Manager** for automatic driver binary resolution — no manual driver files needed. Add new browsers here.

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

# Filter by TestNG groups
.\gradlew.bat clean test -Dgroups="Regression"
.\gradlew.bat clean test -Dexclude="Flaky"

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

Listeners are wired via **`@Listeners` on `BaseTest`** — do not add them again in subclasses:

| Class                         | Interface                | Purpose                                                                                                   |
|-------------------------------|--------------------------|-----------------------------------------------------------------------------------------------------------|
| `TestExecutionMethodListener` | `IInvokedMethodListener` | Collects per-test result (timing, status, params) into `TestExecutionResultCollector` for Excel reporting |
| `ScreenshotListener`          | `IInvokedMethodListener` | Captures screenshot + page source on failure, attaches to Allure via `@Attachment`                        |
| `ResultExecutionListener`     | `IExecutionListener`     | Writes Allure `environment.properties` on suite start; triggers Excel generation on suite finish          |

`AllureTestListener` (implements `TestLifecycleListener`) is registered separately via Java SPI in
`src/test/resources/META-INF/services/io.qameta.allure.listener.TestLifecycleListener` — prints test start/stop to console.

---

## Parallel Execution & Retry

- TestNG `parallel = 'classes'`, `threadCount = 3` (configured in `build.gradle` `useTestNG {}` block)
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true` (a test that only passes on retry is marked as a failure)

---

## Test Annotations (TestNG)

```java
@Feature("My feature")                     // Allure feature grouping — on the class
public class MyTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() { steps = new MySteps(); }

    @Test(description = "My scenario", groups = {"Regression"})
    public void myTest() { ... }
}
```

- Use `@Test(description = "...")` — not a separate `@Description` annotation.
- Use `@Test(groups = {"Regression"})` / `@Test(groups = {"Flaky"})` for tag filtering.
- Always add `alwaysRun = true` to `@BeforeMethod` so setup runs even when groups are filtered.

---

## Parameterized Tests & Test Data

- Inline data: `@DataProvider(name = "x")` returning `Object[][]` in the same class; reference with `@Test(dataProvider = "x")` (see `BasicOperationsTest` — `additionNumbers()`, `subtractNumbers()`, `multiplyNumbers()`).
- Manual CSV: `@DataProvider` reads `Division.csv` line-by-line via `BufferedReader` inside the test class, using `Constants.TEST_RESOURCES` as the base path (see `BasicOperationsTest.divideNumbers()`).
- Reusable CSV: `@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class)` + `@CsvSource(path = TEST_RESOURCES + "Division.csv")` — `CsvDataProvider` uses OpenCSV to parse the file (see `BasicDivisionTest`).
- Parameters are automatically captured by `TestExecutionMethodListener` via `ITestResult.getParameters()` and stored for Excel reporting.

---

## Adding a New Browser

Add a `case` to the switch in `WebdriverFactory.createInstance()` — that is the only place to change. Selenium Manager resolves the driver binary automatically.

---

## Reporting Outputs

| Report             | Location                                              |
|--------------------|-------------------------------------------------------|
| Allure HTML        | `build/reports/allure-report/allureReport/index.html` |
| TestNG HTML        | `build/reports/testng/`                               |
| Excel              | `build/reports/executionReport_*.xlsx`                |
| Screenshots        | `build/reports/screenshots/`                          |
| Allure raw results | `build/allure-results/`                               |

---

## Key Files Reference

| File                                                   | Role                                                                                                                                            |
|--------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `build.gradle`                                         | TestNG parallel config, retry, group filtering logic, report task wiring                                                                        |
| `framework/BaseTest.java`                              | Required superclass; wires all three TestNG listeners via `@Listeners`                                                                          |
| `framework/config/Configuration.java`                  | All config keys (Owner `@Config.Key`)                                                                                                           |
| `framework/manager/WebdriverManager.java`              | ThreadLocal driver store                                                                                                                        |
| `framework/listeners/ScreenshotListener.java`          | Failure screenshot + Allure attachment                                                                                                          |
| `framework/listeners/ResultExecutionListener.java`     | Suite-level reporting hook (`IExecutionListener`)                                                                                               |
| `framework/listeners/TestExecutionMethodListener.java` | Per-test result collector (`IInvokedMethodListener`)                                                                                            |
| `src/test/resources/general.properties`                | Runtime configuration                                                                                                                           |
| `src/test/resources/META-INF/services/`                | SPI registration for `AllureTestListener` only                                                                                                  |
| `lombok.config`                                        | Lombok project-level config; sets `lombok.jacksonized.jacksonVersion += 2` to resolve the Jackson2/Jackson3 ambiguity warning on `@Jacksonized` |

---

## AI Instructions

### 🤖 Coding Standards
- **Strict 3-Layer Hierarchy**:
    - `pages/` (DOM only, no business logic)
    - `steps/` (Reusable business actions, uses `@Step`)
    - `test/` (TestNG classes, uses `@Feature` and `@Test(description = "...")`)
- **Driver Access**: ALWAYS use `WebdriverManager.getDriver()`. NEVER pass `WebDriver` instances as method arguments.
- **Config Access**: Use `ConfigurationManager.configuration()` for any property. NEVER hardcode URLs, timeouts, or browser names.
- **Page Objects**: Use `PageFactory.initElements(WebdriverManager.getDriver(), this)` in constructors. Use `@FindBy` annotations.

### 🧪 Testing Guidelines
- **Annotations**:
    - Test classes MUST extend `BaseTest`.
    - Use `@Feature` on class level.
    - Use `@Test(description = "...")` for TestNG.
- **Data Driven**:
    - Prefer CSV-based data providers for complex scenarios.
    - Reference files via `Constants.TEST_RESOURCES`.
- **Validation**: Use `org.assertj.core.api.Assertions` for expressive assertions.

### 🛠️ Workflow & Maintenance
- **Environment**: Use Windows PowerShell for terminal commands (e.g., `.\gradlew.bat`).
- **Cleaning**: Always include `clean` in test commands (`.\gradlew.bat clean test`) to ensure reports are fresh.
- **Dependencies**: Before updating dependencies, check `build.gradle` and synchronize versions in `README.md`.
