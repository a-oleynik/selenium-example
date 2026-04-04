# GitHub Copilot Instructions — selenium-example

## Stack
Java 21 · Gradle · Selenium WebDriver 4 · TestNG 7 · Allure · AssertJ · Owner (config) · Lombok · Apache POI · Log4j/SLF4J

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

## Parameterized Tests
- Inline data: `@DataProvider(name = "x")` returning `Object[][]` in the same class; wire with `@Test(dataProvider = "x")`.
- File-driven data: read `Division.csv` manually via `BufferedReader` in the `@DataProvider` method using `Constants.TEST_RESOURCES` as the base path.

## Listeners — Do Not Touch
Three TestNG listeners are already wired via `@Listeners` on `BaseTest` — never add them again in subclasses:
- `TestExecutionMethodListener` — collects per-test results for Excel reporting
- `ScreenshotListener` — captures screenshot + page source on failure
- `ResultExecutionListener` — suite start/finish hooks (Allure environment + Excel trigger)

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
Allure HTML → `build/reports/allure-report/allureReport/index.html`
TestNG HTML → `build/reports/testng/`
Excel       → `build/reports/executionReport_*.xlsx`
Screenshots → `build/reports/screenshots/`
Both Allure and Excel reports are generated automatically after every `test` run (`finalizedBy` in `build.gradle`).
