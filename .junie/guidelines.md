# JetBrains Junie Guidelines — selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **JUnit 6 (Jupiter)** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

---

## Architecture: 3-Layer Test Pattern

All tests follow a strict three-layer hierarchy — never skip or collapse layers:

```
test/          → JUnit 6 test classes (extend BaseTest, use @Feature, @Description)
  └── steps/   → Business-level steps (annotated with @Step for Allure tracing)
        └── pages/ → Selenium Page Object classes (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

- **`BaseTest`** (`framework/BaseTest.java`): `@TestInstance(PER_CLASS)` — one WebDriver instance per test class.
  `@BeforeAll` creates the driver; `@AfterAll` quits it. `@BeforeEach` calls `BaseTestMethods.instantiateDriver()` to self-heal a crashed driver before each test.
  `MyTestWatcher` is wired via `@ExtendWith(MyTestWatcher.class)` on this class — do not add it again in subclasses.
- **`WebdriverManager`** (`framework/manager/WebdriverManager.java`): `ThreadLocal<WebDriver>` — essential for parallel safety.
  Always access the driver via `WebdriverManager.getDriver()`, never pass it directly.
- **`WebdriverFactory`** (`framework/manager/WebdriverFactory.java`): Switch on `configuration().envBrowser()` (case-insensitive).
  Relies on **Selenium Manager** for automatic driver binary resolution — no manual driver files needed. Add new browsers here.
- **`BaseTestMethods`** (`framework/BaseTestMethods.java`): Sets browser to maximised and applies a **2-second implicit wait** after every driver creation. Do not add additional implicit waits elsewhere.

---

## Coding Standards

### Strict 3-Layer Hierarchy
- `pages/` — DOM only, no business logic, no assertions
- `steps/` — Reusable business actions, uses `@Step`, owns all assertions
- `test/` — JUnit 6 classes, uses `@Feature` and `@Description`

### Driver Access
```java
// CORRECT — always use ThreadLocal accessor
WebDriver driver = WebdriverManager.getDriver();

// WRONG — never inject or pass the driver directly
```

### Configuration Access
```java
// Read config values via the singleton — never hardcode
configuration().environmentUrl()          // env.url
configuration().envBrowser()              // env.browser
configuration().defaultWebdriverTimeout() // default.webdriver.timeout
```

Config is managed via the **Owner** library reading `src/test/resources/general.properties`:
```properties
env.browser = Chrome       # Chrome | Firefox | Edge
env.url = http://calculator.com
default.webdriver.timeout = 180
env.time.zone = Europe/Warsaw
```

### Page Objects
- Constructor: navigate first, then `PageFactory.initElements(WebdriverManager.getDriver(), this)`.
- Use `@FindBy` annotations for stable locators.
- For conditional/dynamic locators use `static final By` constants and call `WebdriverUtils.findElement(By)`.
- Dismiss pop-ups via `WebdriverUtils.clickIfElementShown(By)` — never add hard waits.

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

- **Inline**: `@ParameterizedTest` + `@MethodSource` pointing to a `static Object[][]` method in the same class (see `BasicOperationsTest` — `addNumbers()`, `subtractNumbers()`, `multiplyNumbers()`).
- **Classpath CSV**: `@CsvFileSource(resources = "/Division.csv")` — file lives in `src/test/resources/` (see `BasicOperationsTest.checkCalculatorDivision()`).
- **Filesystem CSV**: `@ParameterizedDataSource(path = TEST_RESOURCES + "Division.csv")` — custom annotation backed by `CSVDataProvider` (OpenCSV `ArgumentsProvider`); path relative to project root via `Constants.TEST_RESOURCES` (see `BasicDivisionTest`).
- Parameters are automatically captured by `MyTestWatcher.interceptTestTemplateMethod()` and stored for Excel reporting.

---

## Validation & Assertions

- Use `org.assertj.core.api.Assertions` for expressive assertions in step methods.
- For soft assertions, use AssertJ `SoftAssertions` — call `softly.assertAll()` at the end.
  See `CalculatorSteps.checkSoftAsserts()` for the canonical example.
- **Assertions belong only in the steps layer** — never in page classes.
- There is **no TestNG dependency** in this project.

---

## Parallel Execution & Retry

- `maxParallelForks = 3` (Gradle JVM forks) + JUnit `parallel.config.strategy=dynamic` + `parallel.config.executor-service=WORKER_THREAD_POOL`.
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation.
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true`.

---

## Listeners — Do Not Touch

`MyTestWatcher` is wired via `@ExtendWith` on `BaseTest` — do not add it again in subclasses.
`ResultExecutionListener` and `AllureTestListener` are registered via Java SPI in
`src/test/resources/META-INF/services/` — do not add them with `@ExtendWith`.

| Class                     | Registration                | Purpose                                                                                              |
|---------------------------|-----------------------------|------------------------------------------------------------------------------------------------------|
| `MyTestWatcher`           | `@ExtendWith` on `BaseTest` | Captures screenshot + page source on failure, attaches to Allure; captures test parameters for Excel |
| `ResultExecutionListener` | Java SPI                    | Writes Allure `environment.properties`; triggers Excel generation at end of suite                    |
| `AllureTestListener`      | Java SPI                    | Console log with test name + feature on start/stop                                                   |

---

## Selenium Manager (Automatic Drivers)

This branch uses **Selenium Manager** — no manual driver binaries are needed.
Do NOT add `System.setProperty("webdriver.*.driver", ...)` calls anywhere.

---

## Adding a New Browser

Add a `case` to the switch in `WebdriverFactory.createInstance()` — that is the only place to change.
Selenium Manager resolves the driver binary automatically — no `System.setProperty(...)` calls needed.

```java
case "chrome" -> {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-search-engine-choice-screen");
    yield new ChromeDriver(options);
}
```

---

## Lombok

- `lombok.config` lives at the project root — do **not** delete it.
- `lombok.jacksonized.jacksonVersion += 2` is required to suppress the *"Ambiguous: Jackson2 and Jackson3"* warning.
- Add new Lombok config keys to `lombok.config` — never inline annotation-processor arguments in `build.gradle`.

---

## Gradle Commands (Windows)

```bash
.\gradlew.bat clean test                                      # full run + Allure + Excel reports
.\gradlew.bat clean test --tests *.CalculatorSanityTest       # single class
.\gradlew.bat clean test -DincludeTags="Regression"           # tag filter
.\gradlew.bat clean test -DexcludeTags="Flaky"                # tag exclusion
.\gradlew.bat allureReport                                    # regenerate HTML report only
.\gradlew.bat excelReport                                     # regenerate Excel report only
taskkill /F /IM chromedriver.exe /T                          # kill stale drivers
```

Always include `clean` in test commands (`.\gradlew.bat clean test`) to ensure reports are fresh.

---

## Reporting Outputs

| Report             | Location                                                              |
|--------------------|-----------------------------------------------------------------------|
| Allure HTML        | `build/reports/allure-report/allureReport/index.html`                 |
| JUnit HTML         | `build/reports/tests/test/index.html`                                 |
| Excel              | `build/reports/consolidatedExecutionReport_ddmmyy_HHmmss.xlsx`        |
| Screenshots        | `build/reports/screenshots/`                                          |
| Allure raw results | `build/allure-results/`                                               |
| Excel raw results  | `build/excel-results/testResult_*.json`                               |

---

## Key Files Reference

| File                                               | Role                                                                                                                                                                          |
|----------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `build.gradle`                                     | JUnit parallel config, retry, tag filtering logic, report task wiring                                                                                                         |
| `framework/BaseTest.java`                          | Required superclass; wires `MyTestWatcher` via `@ExtendWith`; `@TestInstance(PER_CLASS)` — one driver per class                                                               |
| `framework/config/Configuration.java`              | All config keys (Owner `@Config.Key`)                                                                                                                                         |
| `framework/manager/WebdriverManager.java`          | ThreadLocal driver store                                                                                                                                                      |
| `framework/listeners/MyTestWatcher.java`           | Failure screenshot + Allure attachment; captures test parameters (`TestWatcher` + `InvocationInterceptor`)                                                                    |
| `framework/listeners/ResultExecutionListener.java` | Suite-level reporting hook (SPI `TestExecutionListener`) — Allure environment + Excel trigger                                                                                 |
| `framework/utils/WebdriverUtils.java`              | Driver lifecycle + explicit wait helpers: `createNewDriver()`, `quitDriver()`, `findElement(By)`, `elementExists(By)`, `elementExistsAndShown(By)`, `clickIfElementShown(By)` |
| `framework/config/Constants.java`                  | Path constants: `BUILD_FOLDER`, `REPORTS_FOLDER`, `SCREENSHOTS_FOLDER`, `EXCEL_RESULTS_FOLDER`, `TEST_RESOURCES`                                                              |
| `src/test/resources/general.properties`            | Runtime configuration                                                                                                                                                         |
| `src/test/resources/META-INF/services/`            | SPI registration for `ResultExecutionListener` + `AllureTestListener`                                                                                                         |
| `lombok.config`                                    | Lombok project-level config                                                                                                                                                   |
| `AGENTS.md`                                        | Full agent/AI guide — architecture, workflow, key files, commands                                                                                                             |
| `.github/copilot-instructions.md`                  | GitHub Copilot coding instructions                                                                                                                                            |
