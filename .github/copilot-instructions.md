# GitHub Copilot Instructions — selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **TestNG** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

## Stack
Java 21 · Gradle · Selenium WebDriver 4 · TestNG 7 · Allure · AssertJ · Owner (config) · Lombok · Apache POI · SLF4J · Logback

## Mandatory 3-Layer Architecture
Every feature must follow this hierarchy — never collapse or skip layers:

```
test/    → TestNG test class    (@Feature, @Test(description="..."), extends BaseTest)
steps/   → @Step-annotated methods that call page methods and run assertions
pages/   → Page Object class    (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

## Test Classes
- Always `extend BaseTest` — never manage WebDriver lifecycle manually in a test class.
- Annotate each class with `@Feature("...")` (Allure) and each test method with `@Test(description = "...")`.
- Use `@Test(groups = {"Regression"})` / `@Test(groups = {"Flaky"})` for group-based filtering.
- Read the target URL via `configuration().environmentUrl()` — never hardcode URLs.
- Instantiate a fresh `Steps` object in `@BeforeMethod(alwaysRun = true)`, not as a class field.
- Always add `alwaysRun = true` to `@BeforeMethod` so setup runs even when groups are filtered.

## Steps Classes
- Every public method must carry `@Step("descriptive action string with {param} placeholders")`.
- Steps own assertions (`assertEquals`, `assertTrue`, AssertJ `assertThat`) — pages must not assert.
- For multi-assertion scenarios use AssertJ `SoftAssertions` and call `softly.assertAll()` at the end.

## Page Classes
- Constructor signature: navigate first, then `PageFactory.initElements(WebdriverManager.getDriver(), this)`.
- Use `@FindBy` fields for stable locators; fall back to `By` constants (static final) for dynamic or conditional locators.
- Cookie/consent pop-ups: dismiss via `WebdriverUtils.clickIfElementShown(By)` — never add hard waits.
- Never call `WebdriverManager.getDriver()` outside page/utils classes.

## WebDriver Access
```java
// CORRECT — always use ThreadLocal accessor
WebDriver driver = WebdriverManager.getDriver();

// WRONG — never inject or pass the driver directly
```

## Configuration
```java
// Read config values via the singleton — never hardcode
configuration().environmentUrl()          // env.url
configuration().envBrowser()              // env.browser
configuration().defaultWebdriverTimeout() // default.webdriver.timeout
```
Override at runtime: `.\gradlew.bat test -Denv.browser=Firefox -Denv.url=https://...`

Config is managed via the **Owner** library reading `src/test/resources/general.properties`:
```properties
env.browser = Chrome       # Chrome | Firefox | Edge
env.url = http://calculator.com
default.webdriver.timeout = 180
env.time.zone = Europe/Warsaw
```

## Lombok
- `lombok.config` lives at the project root — do **not** delete it.
- `lombok.jacksonized.jacksonVersion += 2` is required to suppress the *"Ambiguous: Jackson2 and Jackson3"* warning produced by `@Jacksonized` when both Jackson versions are detectable on the classpath.
- If you add new Lombok config keys, add them to `lombok.config` — never inline annotation-processor arguments in `build.gradle`.

## WebDriver Binaries (No Selenium Manager)
This branch does **not** use Selenium Manager. Drivers are pre-downloaded binaries in `./drivers/`:

| Binary                       | Browser |
|------------------------------|---------|
| `drivers/chromedriver.exe`   | Chrome  |
| `drivers/geckodriver.exe`    | Firefox |
| `drivers/msedgedriver.exe`   | Edge    |
| `drivers/IEDriverServer.exe` | IE      |

Binaries must be manually kept in sync with the installed browser version.

## Adding a New Browser
1. Place the matching driver binary in `./drivers/`.
2. Add a `case` to the switch in `WebdriverFactory.createInstance()` — set `System.setProperty("webdriver.*.driver", ".\\drivers\\*.exe")` before yielding the driver instance. That is the **only** place to change.

```java
case "chrome" -> {
    System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-search-engine-choice-screen");
    yield new ChromeDriver(options);
}
```

## Parameterized Tests
- Inline data: `@DataProvider(name = "x")` returning `Object[][]` in the same class; wire with `@Test(dataProvider = "x")`.
- Manual CSV: define a `@DataProvider` in the test class and read the CSV via `BufferedReader` using `Constants.TEST_RESOURCES` as the base path (see `BasicOperationsTest.divideNumbers()`).
- Reusable CSV: annotate the test with `@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class)` and `@CsvSource(path = TEST_RESOURCES + "YourData.csv")` — `CsvDataProvider` (OpenCSV) resolves the path from `@CsvSource` (see `BasicDivisionTest`).

## Parallel Execution & Retry
- TestNG `parallel = 'classes'`, `threadCount = 3` (configured in `build.gradle` `useTestNG {}` block).
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation.
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true` (a test that only passes on retry is marked as a failure).

## Listeners — Do Not Touch
Three TestNG listeners are already wired via `@Listeners` on `BaseTest` — never add them again in subclasses:

| Class                         | Interface                | Purpose                                                                                                   |
|-------------------------------|--------------------------|-----------------------------------------------------------------------------------------------------------|
| `TestExecutionMethodListener` | `IInvokedMethodListener` | Collects per-test result (timing, status, params) into `TestExecutionResultCollector` for Excel reporting |
| `ScreenshotListener`          | `IInvokedMethodListener` | Captures screenshot + page source on failure, attaches to Allure via `@Attachment`                        |
| `ResultExecutionListener`     | `IExecutionListener`     | Writes Allure `environment.properties` on suite start; triggers Excel generation on suite finish          |

`AllureTestListener` is registered via Java SPI in `src/test/resources/META-INF/services/` — do not add it with `@Listeners`.

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

## Reporting

| Report             | Location                                                              |
|--------------------|-----------------------------------------------------------------------|
| Allure HTML        | `build/reports/allure-report/allureReport/index.html`                 |
| TestNG HTML        | `build/reports/testng/`                                               |
| Excel              | `build/reports/consolidatedExecutionReport_ddmmyy_HHmmss.xlsx`        |
| Screenshots        | `build/reports/screenshots/`                                          |
| Allure raw results | `build/allure-results/`                                               |
| Excel raw results  | `build/excel-results/testResult_*.json`                               |
| Log file           | `build/logs/test.log` (overwritten each run; mirrors console output)  |

Both Allure and Excel reports are generated automatically after every `test` run (`finalizedBy` in `build.gradle`).

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
| `framework/utils/WebdriverUtils.java`                  | Driver lifecycle + explicit wait helpers: `createNewDriver()`, `quitDriver()`, `findElement(By)` (FluentWait, `presenceOfElementLocated`), `findElement(By, Function, Integer)` (custom condition + timeout), `elementExists(By)`, `elementExistsAndShown(By)`, `clickIfElementShown(By)` |
| `framework/config/Constants.java`                      | Path constants: `BUILD_FOLDER`, `LOGS_FOLDER`, `REPORTS_FOLDER`, `SCREENSHOTS_FOLDER`, `EXCEL_RESULTS_FOLDER`, `TEST_RESOURCES` — use these whenever referencing file system paths                                                                                                        |
| `src/test/resources/general.properties`                | Runtime configuration                                                                                                                                                                                                                                                                     |
| `src/test/resources/logback-test.xml`                  | Logback config — console appender + `FileAppender` writing to `build/logs/test.log`; framework package at `DEBUG`, root at `INFO`                                                                                                                                                         |
| `src/test/resources/META-INF/services/`                | SPI registration for `AllureTestListener` only                                                                                                                                                                                                                                            |
| `lombok.config`                                        | Lombok project-level config; sets `lombok.jacksonized.jacksonVersion += 2` to resolve the Jackson2/Jackson3 ambiguity warning on `@Jacksonized`                                                                                                                                           |
| `AGENTS.md`                                            | Full agent/AI guide — architecture, workflow, key files, commands                                                                                                                                                                                                                         |
| `.junie/guidelines.md`                                 | JetBrains Junie AI coding guidelines                                                                                                                                                                                                                                                      |
