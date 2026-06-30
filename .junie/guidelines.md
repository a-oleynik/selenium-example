# JetBrains Junie Guidelines — selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **TestNG** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

---

## Architecture: 3-Layer Test Pattern

All tests follow a strict three-layer hierarchy — never skip or collapse layers:

```
test/          → TestNG test classes (extend BaseTest, use @Feature, @Test(description="..."))
  └── steps/   → Business-level steps (annotated with @Step for Allure tracing)
        └── pages/ → Selenium Page Object classes (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

- **`BaseTest`** (`framework/BaseTest.java`): One WebDriver instance per test class via `@BeforeClass(alwaysRun = true)` / `@AfterClass(alwaysRun = true)`.
  `@BeforeMethod(alwaysRun = true)` calls `BaseTestMethods.instantiateDriver()` to self-heal a crashed driver before each test.
  Listeners are wired via `@Listeners` directly on this class.
- **`WebdriverManager`** (`framework/manager/WebdriverManager.java`): `ThreadLocal<WebDriver>` — essential for parallel safety.
  Always access the driver via `WebdriverManager.getDriver()`, never pass it directly.
- **`WebdriverFactory`** (`framework/manager/WebdriverFactory.java`): Switch on `configuration().envBrowser()` (case-insensitive).
  Relies on **Selenium Manager** for automatic driver binary resolution — no manual driver files needed. Add new browsers here.
- **`BaseTestMethods`** (`framework/BaseTestMethods.java`): Sets browser to maximised and applies a **2-second implicit wait** (`IMPLICIT_WAIT_SECONDS = 2`) after every driver creation. Do not add additional implicit waits elsewhere.

---

## Coding Standards

### Strict 3-Layer Hierarchy
- `pages/` — DOM only, no business logic, no assertions
- `steps/` — Reusable business actions, uses `@Step`, owns all assertions
- `test/` — TestNG classes, uses `@Feature` and `@Test(description = "...")`

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

- **Inline**: `@DataProvider(name = "x")` returning `Object[][]` in the same class; reference with `@Test(dataProvider = "x")`.
- **Manual CSV**: `@DataProvider` reads CSV line-by-line via `BufferedReader` using `Constants.TEST_RESOURCES` as the base path (see `BasicOperationsTest.divideNumbers()`).
- **Reusable CSV**: `@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class)` + `@CsvSource(path = TEST_RESOURCES + "Division.csv")` — `CsvDataProvider` uses OpenCSV (see `BasicDivisionTest`).

---

## Validation & Assertions

- Use `org.assertj.core.api.Assertions` for expressive assertions in step methods.
- For soft assertions, use TestNG `org.testng.asserts.SoftAssert` or AssertJ `SoftAssertions`; always call `assertAll()` at the end.
- **Assertions belong only in the steps layer** — never in page classes.

---

## Parallel Execution & Retry

- TestNG `parallel = 'classes'`, `threadCount = 3` (configured in `build.gradle`).
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation.
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true`.

---

## Listeners — Do Not Touch

Three TestNG listeners are already wired via `@Listeners` on `BaseTest` — never add them again in subclasses:

| Class                         | Interface                | Purpose                                                                                                   |
|-------------------------------|--------------------------|-----------------------------------------------------------------------------------------------------------|
| `TestExecutionMethodListener` | `IInvokedMethodListener` | Collects per-test result (timing, status, params) into `TestExecutionResultCollector` for Excel reporting |
| `ScreenshotListener`          | `IInvokedMethodListener` | Captures screenshot + page source on failure, attaches to Allure via `@Attachment`                        |
| `ResultExecutionListener`     | `IExecutionListener`     | Writes Allure `environment.properties` on suite start; triggers Excel generation on suite finish          |

`AllureTestListener` is registered via Java SPI in `src/test/resources/META-INF/services/` — do not add it with `@Listeners`.

---

## Adding a New Browser

Add a `case` to the switch in `WebdriverFactory.createInstance()` — that is the only place to change:

```java
case "chrome" -> {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-search-engine-choice-screen");
    yield new ChromeDriver(options);
}
```

Selenium Manager resolves the driver binary automatically — no `System.setProperty(...)` calls.

---

## Lombok

- `lombok.config` lives at the project root — do **not** delete it.
- `lombok.jacksonized.jacksonVersion += 2` is required to suppress the *"Ambiguous: Jackson2 and Jackson3"* warning.
- Add new Lombok config keys to `lombok.config` — never inline annotation-processor arguments in `build.gradle`.

---

## Gradle Commands (Windows)

```bash
.\gradlew.bat clean test                                    # full run + Allure + Excel reports
.\gradlew.bat clean test --tests *.CalculatorSanityTest     # single class
.\gradlew.bat clean test -Dgroups="Regression"              # group filter
.\gradlew.bat clean test -Dexclude="Flaky"                  # group exclusion
.\gradlew.bat allureReport                                  # regenerate HTML report only
.\gradlew.bat excelReport                                   # regenerate Excel report only
taskkill /F /IM chromedriver.exe /T                        # kill stale drivers
```

Always include `clean` in test commands (`.\gradlew.bat clean test`) to ensure reports are fresh.

---

## Reporting Outputs

| Report             | Location                                                              |
|--------------------|-----------------------------------------------------------------------|
| Allure HTML        | `build/reports/allure-report/allureReport/index.html`                 |
| TestNG HTML        | `build/reports/testng/`                                               |
| Excel              | `build/reports/consolidatedExecutionReport_ddmmyy_HHmmss.xlsx`        |
| Screenshots        | `build/reports/screenshots/`                                          |
| Allure raw results | `build/allure-results/`                                               |
| Excel raw results  | `build/excel-results/testResult_*.json`                               |

---

## Key Files Reference

| File                                                   | Role                                                                                                                                                                                                                                                                                      |
|--------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `build.gradle`                                         | TestNG parallel config, retry, group filtering logic, report task wiring                                                                                                                                                                                                                  |
| `framework/BaseTest.java`                              | Required superclass; wires all three TestNG listeners via `@Listeners`                                                                                                                                                                                                                    |
| `framework/config/Configuration.java`                  | All config keys (Owner `@Config.Key`)                                                                                                                                                                                                                                                     |
| `framework/manager/WebdriverManager.java`              | ThreadLocal driver store                                                                                                                                                                                                                                                                  |
| `framework/listeners/ScreenshotListener.java`          | Failure screenshot + Allure attachment                                                                                                                                                                                                                                                    |
| `framework/listeners/ResultExecutionListener.java`     | Suite-level reporting hook (`IExecutionListener`)                                                                                                                                                                                                                                         |
| `framework/listeners/TestExecutionMethodListener.java` | Per-test result collector (`IInvokedMethodListener`)                                                                                                                                                                                                                                      |
| `framework/utils/WebdriverUtils.java`                  | Driver lifecycle + explicit wait helpers: `createNewDriver()`, `quitDriver()`, `findElement(By)`, `elementExists(By)`, `elementExistsAndShown(By)`, `clickIfElementShown(By)`                                                                                                             |
| `framework/config/Constants.java`                      | Path constants: `BUILD_FOLDER`, `REPORTS_FOLDER`, `SCREENSHOTS_FOLDER`, `EXCEL_RESULTS_FOLDER`, `TEST_RESOURCES`                                                                                                                                                                          |
| `src/test/resources/general.properties`                | Runtime configuration                                                                                                                                                                                                                                                                     |
| `src/test/resources/META-INF/services/`                | SPI registration for `AllureTestListener` only                                                                                                                                                                                                                                            |
| `lombok.config`                                        | Lombok project-level config                                                                                                                                                                                                                                                               |
| `AGENTS.md`                                            | Full agent/AI guide — architecture, workflow, key files, commands                                                                                                                                                                                                                         |
| `.github/copilot-instructions.md`                      | GitHub Copilot coding instructions                                                                                                                                                                                                                                                        |

