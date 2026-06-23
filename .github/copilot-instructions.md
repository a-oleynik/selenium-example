# GitHub Copilot Instructions — selenium-example

## Project Overview

Gradle + Java 21 Selenium WebDriver test automation framework using **TestNG** and **Allure** reporting.
The target application is an online calculator at `http://calculator.com`.
Tests run in parallel across Chrome/Firefox/Edge with automatic retry, screenshot capture, and dual reporting (Allure + Excel).

## Stack
Java 21 · Gradle · Selenium WebDriver 4 · JUnit 6 (Jupiter) · Allure · AssertJ · Owner (config) · Lombok · Apache POI · Log4j/SLF4J

## Mandatory 3-Layer Architecture
Every feature must follow this hierarchy — never collapse or skip layers:

```
test/    → JUnit 6 test class   (@Feature, @Description, extends BaseTest)
steps/   → @Step-annotated methods that call page methods and run assertions
pages/   → Page Object class    (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

## Test Classes
- Always `extend BaseTest` — never manage WebDriver lifecycle manually in a test class.
- Annotate each class with `@Feature("...")` (Allure) and each test method with `@Description("...")`.
- Use `@Tag("Regression")` / `@Tag("Flaky")` to enable Gradle tag filtering.
- Read the target URL via `configuration().environmentUrl()` — never hardcode URLs.
- Instantiate a fresh `Steps` object in `@BeforeEach`, not as a class field.

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
env.browser = Chrome       # Chrome | Firefox | Edge | IE
env.url = http://calculator.com
default.webdriver.timeout = 180
env.time.zone = Europe/Warsaw
```

## Lombok
- `lombok.config` lives at the project root — do **not** delete it.
- `lombok.jacksonized.jacksonVersion += 2` is required to suppress the *"Ambiguous: Jackson2 and Jackson3"* warning produced by `@Jacksonized` when both Jackson versions are detectable on the classpath.
- If you add new Lombok config keys, add them to `lombok.config` — never inline annotation-processor arguments in `build.gradle`.

## Selenium Manager (Automatic Drivers)
This branch uses **Selenium Manager** — no manual driver binaries are needed.
Do NOT add `System.setProperty("webdriver.*.driver", ...)` calls anywhere.

## Adding a New Browser
Add a `case` to the switch in `WebdriverFactory.createInstance()` — that is the only place to change.

```java
case "chrome" -> {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-search-engine-choice-screen");
    yield new ChromeDriver(options);
}
```

## Parameterized Tests
- Inline data: `@MethodSource` pointing to a `static Object[][]` method in the same class.
- Classpath CSV: `@CsvFileSource(resources = "/YourData.csv")` — place the CSV in `src/test/resources/`.
- Filesystem CSV: `@ParameterizedDataSource(path = TEST_RESOURCES + "YourData.csv")` — custom annotation backed by `CSVDataProvider` (OpenCSV); path relative to project root via `Constants.TEST_RESOURCES`.

## Parallel Execution & Retry
- TestNG `parallel = 'classes'`, `threadCount = 3` (configured in `build.gradle` `useTestNG {}` block).
- `TestExecutionResultCollector` uses a `ConcurrentLinkedQueue` — thread-safe result aggregation.
- Gradle test-retry plugin: `maxRetries = 1`, `failOnPassedAfterRetry = true` (a test that only passes on retry is marked as a failure).

## Listeners — Do Not Touch
`ResultExecutionListener` and `AllureTestListener` are auto-registered via Java SPI in
`src/test/resources/META-INF/services/`. Do not add them with `@ExtendWith`.
`MyTestWatcher` is already wired on `BaseTest` via `@ExtendWith(MyTestWatcher.class)` — screenshots and page-source capture on failure happen automatically.

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

## Reporting
Allure HTML → `build/reports/allure-report/allureReport/index.html`
JUnit HTML  → `build/reports/tests/test/index.html`
Excel       → `build/reports/executionReport_*.xlsx`
Screenshots → `build/reports/screenshots/`
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
| `framework/config/Constants.java`                      | Path constants: `BUILD_FOLDER`, `REPORTS_FOLDER`, `SCREENSHOTS_FOLDER`, `EXCEL_RESULTS_FOLDER`, `TEST_RESOURCES` — use these whenever referencing file system paths                                                                                                                       |
| `src/test/resources/general.properties`                | Runtime configuration                                                                                                                                                                                                                                                                     |
| `src/test/resources/META-INF/services/`                | SPI registration for `AllureTestListener` only                                                                                                                                                                                                                                            |
| `lombok.config`                                        | Lombok project-level config; sets `lombok.jacksonized.jacksonVersion += 2` to resolve the Jackson2/Jackson3 ambiguity warning on `@Jacksonized`                                                                                                                                           |
| `AGENTS.md`                                            | Full agent/AI guide — architecture, workflow, key files, commands                                                                                                                                                                                                                         |
| `.junie/guidelines.md`                                 | JetBrains Junie AI coding guidelines                                                                                                                                                                                                                                                      |
