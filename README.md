# 🚀 Gradle Selenium WebDriver TestNG Example (Manual Driver Management)

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.44.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.12.0-red.svg)](https://testng.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue.svg)](https://gradle.org/)
[![Allure_Framework](https://img.shields.io/badge/Allure_Framework-2.34.0-purple.svg)](https://allurereport.org/docs/)
[![Allure_CLI](https://img.shields.io/badge/Allure_CLI-2.35.1-yellow.svg)](https://allurereport.org/docs/)

A comprehensive test automation framework demonstrating best practices with Selenium WebDriver, TestNG, and Allure
reporting. This project showcases parameterized testing, parallel execution, automatic retry logic, and advanced
reporting capabilities.

> **Note:** This branch uses TestNG 7 as the testing framework and manually downloaded WebDriver binaries. 
> For automatic WebDriver management using Selenium Manager and TestNG 7, please check out the [`master`](https://github.com/a-oleynik/selenium-example/tree/master) branch.
> For a JUnit 6 implementation see the [`junit6`](https://github.com/a-oleynik/selenium-example/tree/junit6) branch.
>
> **Related projects:**
> - 🧪 [TestNG Workshop](https://github.com/a-oleynik/testng-workshop) — pure TestNG examples (no Selenium) to study the framework in isolation
> - 🔬 [JUnit Workshop](https://github.com/a-oleynik/junit-workshop) — companion JUnit 6 / 5 / 4 examples for side-by-side comparison

---

## 📋 Table of Contents

- [Branches](#-branches)
- [Why this repository?](#-why-this-repository)
- [Who Is This For?](#-who-is-this-for)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
  - [Gradle Plugins](#-gradle-plugins)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [WebDriver Setup](#-webdriver-setup)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Reporting](#-reporting)
- [Advanced Features](#-advanced-features)
- [Learning Path — Beginners](#-learning-path--beginners)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)
- [Additional Resources](#-additional-resources)
- [Useful Links](#-useful-links)
- [Found it useful?](#-found-it-useful)

---

## 🌿 Branches

This repository contains examples for both TestNG and JUnit, with and without Selenium Manager:

| Branch                                                                                                                  | Testing Framework | Description                                                            |
|-------------------------------------------------------------------------------------------------------------------------|-------------------|------------------------------------------------------------------------|
| [`master`](https://github.com/a-oleynik/selenium-example/tree/master)                                                   | **TestNG 7**      | Current branch — full-featured framework with TestNG                   |
| [`junit6`](https://github.com/a-oleynik/selenium-example/tree/junit6)                                                   | **JUnit 6**       | Same framework rebuilt on JUnit 6 — compare patterns side-by-side      |
| [`testng-without-selenium-manager`](https://github.com/a-oleynik/selenium-example/tree/testng-without-selenium-manager) | **TestNG 7**      | Legacy branch — manual WebDriver binary setup without Selenium Manager |
| [`junit6-without-selenium-manager`](https://github.com/a-oleynik/selenium-example/tree/junit6-without-selenium-manager) | **JUnit 6**       | JUnit 6 + manual WebDriver binary setup without Selenium Manager       |

---

## 🤔 Why this repository?

Most Selenium tutorials show you how to click a button and check a title.  
This project goes much further — it demonstrates a **complete, production-ready automation framework** with real architectural decisions you can study, copy, and adapt immediately.

Use this repository if you want to:

- **learn Selenium WebDriver from scratch** with a guided, step-by-step path
- **understand the 3-layer Page Object Model** — Test → Steps → Page — and why collapsing layers causes long-term pain
- **see TestNG and JUnit 6 side-by-side** — same framework, two branches, real differences
- **explore parallel test execution** — class-level and method-level parallelism with ThreadLocal WebDriver
- **study data-driven testing** — inline `@DataProvider`, manual CSV via `BufferedReader`, and reusable OpenCSV-backed providers
- **understand enterprise-grade reporting** — Allure with steps/attachments/screenshots, TestNG HTML, and Excel
- **learn automatic retry, screenshot capture, and page source attachment** without writing infrastructure from scratch
- **use it as a reference** for workshops, tech talks, onboarding sessions, and self-study
- **compare TestNG and JUnit patterns** with the companion [TestNG Workshop](https://github.com/a-oleynik/testng-workshop) and [JUnit Workshop](https://github.com/a-oleynik/junit-workshop)

---

## 👥 Who Is This For?

| Audience                                                     | What you will get                                                                   |
|--------------------------------------------------------------|-------------------------------------------------------------------------------------|
| **QA engineers new to Selenium + TestNG**                    | A complete working framework to clone and run on day one                            |
| **Java developers learning test automation**                 | Real patterns — POM, Steps, ThreadLocal driver, config management                   |
| **Engineers migrating from JUnit to TestNG** (or vice versa) | Side-by-side branches with identical features, different frameworks                 |
| **Senior / lead engineers**                                  | Deep-dives into parallel execution, retry, custom listeners, and Allure integration |
| **Workshop facilitators**                                    | A ready-made project with multiple difficulty levels you can hand to attendees      |

---

## ✨ Features

- 🔧 **3-Layer architecture with Page Object Model** — Test → Steps → Page, strictly enforced
- 🔄 **Parallel test execution** at class level with ThreadLocal WebDriver (thread-safe)
- 🎯 **Parameterized tests** using TestNG `@DataProvider` (inline, manual CSV, reusable OpenCSV)
- 🧪 **TestNG listeners** for screenshots, Excel reporting, and Allure environment setup
- 📊 **Multiple reporting formats**: Allure, TestNG HTML, and Excel
- 🔁 **Automatic test retry** on failure (Gradle test-retry plugin)
- 📸 **Screenshot + page source capture** on test failure, attached to Allure
- 🌐 **Cross-browser support** — Chrome, Firefox, Edge (one `switch` in `WebdriverFactory`)
- 🎨 **Rich Allure annotations** — `@Feature`, `@Step`, `@Attachment`, `@Description`
- 🔍 **AssertJ** for fluent assertions and `SoftAssertions`
- ⚙️ **Configuration management** via Owner library — override any value with a system property
- 🔒 **Lombok** for reducing boilerplate code

---

## 🛠️ Tech Stack

| Technology                         | Version       | Purpose                                                           |
|------------------------------------|---------------|-------------------------------------------------------------------|
| **Java**                           | 21            | Programming language                                              |
| **Gradle**                         | 9.1.0         | Build automation                                                  |
| **Selenium WebDriver**             | 4.44.0        | Browser automation                                                |
| **TestNG**                         | 7.12.0        | Testing framework                                                 |
| **Allure Framework (Allure Java)** | 2.34.0        | Used inside tests (annotations, listeners). Produces raw results. |
| **Allure Report CLI**              | 2.35.1        | Generates interactive HTML report from test results.              |
| **AssertJ**                        | 3.27.7        | Fluent assertions                                                 |
| **Apache POI**                     | 5.5.1         | Excel report generation                                           |
| **OpenCSV**                        | 5.12.0        | CSV data handling                                                 |
| **Owner**                          | 1.0.12        | Configuration management                                          |
| **Lombok**                         | 1.18.46       | Code generation                                                   |
| **Jackson**                        | 2.21.3        | JSON processing                                                   |
| **Log4j/SLF4J**                    | 2.25.3/2.0.17 | Logging                                                           |

### 🔌 Gradle Plugins

| Plugin                         | Version  | Purpose                                                                 |
|--------------------------------|----------|-------------------------------------------------------------------------|
| `io.qameta.allure`             | 4.0.2    | Allure reporting integration for Gradle                                 |
| `org.gradle.test-retry`        | 1.6.4    | Automatically retries failed tests to handle flakiness                  |
| `com.github.ben-manes.versions`| 0.54.0   | Checks for dependency updates via `.\gradlew.bat dependencyUpdates`     |
| `java-library`                 | Built-in | Core Java project support                                               |
| `idea`                         | Built-in | IntelliJ IDEA project file generation and integration                   |

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or higher
    - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
    - Verify: `java -version`

- **Gradle** (optional — Gradle Wrapper is included)
    - Verify: `gradle -version`

- **Git** (for cloning the repository)
    - [Download Git](https://git-scm.com/downloads)
    - Verify: `git --version`

- **Web Browser** (Chrome, Firefox, Edge, Opera, or IE)
  - See [WebDriver Setup](#-webdriver-setup) section for driver downloads

## 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/a-oleynik/selenium-example.git
   cd selenium-example
   ```

2. **Verify Gradle installation**
   ```bash
   .\gradlew.bat --version
   ```

3. **Download dependencies**
   ```bash
   .\gradlew.bat build --refresh-dependencies
   ```

4. **Compile the project**
   ```bash
   .\gradlew.bat clean compileTestJava
   ```

## 🚗 WebDriver Setup

This branch uses manually downloaded WebDriver binaries. For automatic driver management with Selenium Manager, switch to the `main` branch.

### WebDriver Installation

1. **Download the appropriate WebDriver** for your browser from the sources below
2. **Place the WebDriver executable** in the `drivers` folder:
   ```
   {project directory}/drivers/
   ```
3. **Ensure the driver is accessible** by the framework

### WebDriver Sources

- **Chrome WebDriver**
  ```
  https://googlechromelabs.github.io/chrome-for-testing/
  ```

- **Geckodriver (Firefox)**
  ```
  https://github.com/mozilla/geckodriver/releases
  ```

- **Microsoft Edge WebDriver**
  ```
  https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/?form=MA13LH
  ```

- **Opera WebDriver**
  ```
  https://github.com/operasoftware/operachromiumdriver/releases
  ```

- **IE WebDriver**
  ```
  https://www.selenium.dev/downloads/
  ```

- **Selenium Server (Grid)**
  ```
  https://www.selenium.dev/downloads/
  ```

> **Tip:** Make sure to download the driver version that matches your installed browser version.

---

## ⚙️ Configuration

### General Properties (`src/test/resources/general.properties`)

```properties
# Browser configuration
env.browser=Chrome              # Options: Chrome, Firefox, Edge
# Application URL
env.url=http://calculator.com   # Target application URL
# Timeout settings
default.webdriver.timeout=180   # WebDriver timeout in seconds
# Timezone configuration
env.time.zone=Europe/Warsaw     # Timezone for test execution timestamps
```

### Test Execution Configuration

The test execution is configured in `build.gradle`:

```groovy
test {
    useTestNG {
        parallel = 'classes'    // Parallel execution at class level
        threadCount = 3         // Number of parallel threads
        useDefaultListeners = true
        outputDirectory = layout.buildDirectory.file("reports/testng").get().asFile
        // TestNG report output directory
    }

    retry {
        failOnPassedAfterRetry = true
        maxFailures = 100       // Maximum failures before stopping
        maxRetries = 1          // Number of retry attempts
    }
}
```

### Lombok Configuration (`lombok.config`)

Located at the project root, this file controls Lombok's annotation-processor behaviour:

```properties
# Resolve @Jacksonized ambiguity when both Jackson 2 and Jackson 3 are on the classpath
lombok.jacksonized.jacksonVersion += 2
```

> **Do not delete `lombok.config`.** Without it, the compiler emits:
> `warning: Ambiguous: Jackson2 and Jackson3 exist; define which variant(s) you want in 'lombok.config'`
> for every class annotated with `@Jacksonized` (e.g. `TestExecutionResult`).
>
> If you add new Lombok config keys, always add them here — never inline annotation-processor arguments in `build.gradle`.

### Customizing Configuration

Override any value at runtime with a system property:

1. Edit `general.properties` and change `env.browser` value
2. Or set system property:
   ```bash
   .\gradlew.bat test -Denv.browser=Firefox
   ```

## 🚀 Usage

### Run All Tests

Execute the entire test suite:

```bash
.\gradlew.bat clean test
```

### Run Specific Test Class

Execute a single test class:

```bash
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.CalculatorSanityTest
```

### Run Specific Test Method

Execute a specific test method:

```bash
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.CalculatorSanityTest.checkCalculatorOpening
```

### Run Tests in a Package

Execute all tests in a specific package:

```bash
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.*
```

### Run Tests with Groups

Execute all tests with group "Regression":

```bash
.\gradlew.bat clean test -Dgroups="Regression"
```

### Run Tests excluding Groups

Execute all tests excluding those with group "Flaky":

```bash
.\gradlew.bat clean test -Dexclude="Flaky"
```

### Run Tests from a package filtered by Groups

Execute all tests from a package and tags -Dgroups="Regression":

```bash
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.* -Dgroups="Regression"
```

### Run with Custom Parameters

Execute tests with system properties:

```bash
.\gradlew.bat clean test -Denv.browser=Firefox -Denv.url=https://example.com
```

### Generate Reports Only

Generate Allure report from existing test results:

```bash
.\gradlew.bat allureReport
```

Generate Excel report:

```bash
.\gradlew.bat excelReport
```

### Check for Dependency Updates

Check for outdated dependencies:

```bash
.\gradlew.bat dependencyUpdates
```

---

## 📁 Project Structure

```
selenium-example/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/oleynik/gradle/selenium/example/
│       │       ├── framework/          # Framework utilities
│       │       │   ├── config/         # Configuration management (Owner)
│       │       │   ├── dataproviders/  # CsvDataProvider + @CsvSource annotation
│       │       │   ├── listeners/      # TestNG listeners (screenshot, Excel, Allure)
│       │       │   ├── manager/        # WebDriver management (ThreadLocal)
│       │       │   ├── reporting/      # Custom reporting (Excel)
│       │       │   └── utils/          # Utility classes
│       │       ├── pages/              # Page Object classes (PageFactory + @FindBy)
│       │       ├── steps/              # @Step-annotated business actions + assertions
│       │       └── test/               # TestNG test classes (extend BaseTest)
│       │           ├── BasicDivisionTest.java
│       │           ├── BasicOperationsTest.java
│       │           ├── CalculatorSanityTest.java
│       │           └── SoftAssertionTest.java
│       └── resources/
│           ├── general.properties      # Main configuration
│           ├── allure.properties       # Allure configuration
│           └── Division.csv            # Test data files
├── build/
│   ├── reports/                        # Generated reports
│   │   ├── allure-report/              # Allure HTML report
│   │   ├── testng/                     # TestNG HTML report
│   │   └── executionReport_*.xlsx      # Excel execution report
│   └── test-results/                   # Raw test results
├── drivers/                            # WebDriver binaries (manually downloaded) and Selenium Grid config files
├── build.gradle                        # Build configuration
├── lombok.config                       # Lombok annotation-processor settings (do not delete)
├── gradlew.bat                         # Gradle wrapper (Windows)
├── gradlew                             # Gradle wrapper (Linux/Mac)
└── README.md                           # This file
```

---

## 📊 Reporting

Reports are generated **automatically after every `test` run** — no extra step needed.

### Allure Report

**Location:** `build/reports/allure-report/allureReport/index.html`

**Features:**

- 📈 Test execution timeline
- 📊 Test result statistics
- 📸 Screenshots on failure (attached automatically)
- 📄 Page source capture for debugging
- 🏷️ Test categorization by `@Feature` and `@Story`
- 📝 Detailed `@Step` trace for every test
- 📉 Trend analysis across runs

**View Report:**

```bash
# Generate and open report
.\gradlew.bat allureReport
# Then open: build/reports/allure-report/allureReport/index.html
```

### TestNG HTML Report

**Location:** `build/reports/tests/test/index.html`

**Features:**

- ✅ Pass/Fail statistics
- ⏱️ Execution time per test
- 📋 Test class grouping
- ❌ Failure details with stack traces
- 📊 Test results breakdown

### Excel Report

**Location:** `build/reports/consolidatedExecutionReport_ddmmyy_HHmmss.xlsx`

**Features:**

- 📊 Test execution summary
- 📅 Timestamp information
- ✅ Per-test status tracking
- 🔢 Test parameters captured automatically
- ⏱️ Duration metrics

---

## 🎯 Advanced Features

### 3-Layer Architecture

Every feature follows a strict three-layer hierarchy — never skip or collapse layers:

```
test/    → TestNG test class    (@Feature, @Test(description="..."), extends BaseTest)
steps/   → @Step-annotated methods that call page methods and run assertions
pages/   → Page Object class    (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

This separation keeps tests readable, pages reusable, and assertions in one place.

### Parallel Execution

Tests run in parallel at the class level with 3 threads by default. Configure in `build.gradle`.
WebDriver is stored in a `ThreadLocal` — each thread gets its own isolated browser instance.

```groovy
test {
    useTestNG {
        parallel = 'classes'  // Options: methods, tests, classes, instances
        threadCount = 3       // Number of parallel threads
    }
}
```

### Test Retry Mechanism

Failed tests are automatically retried once via the Gradle test-retry plugin:

```groovy
retry {
    failOnPassedAfterRetry = true   // a flaky test that only passes on retry is still a failure
    maxFailures = 100
    maxRetries = 1
}
```

### Data-Driven Testing

Tests support three styles of parameterization.

**Option 1 — Inline `@DataProvider`** (see `BasicOperationsTest`)

```java
@DataProvider(name = "addition")
public static Object[][] additionNumbers() {
    return new Object[][]{{2, 10}, {99, 7}, {111, 11}, {0, 0}, {1, 1}};
}

@Test(dataProvider = "addition", description = "Check addition")
public void checkCalculatorAdditions(int x, int y) {
    // Test implementation
}
```

**Option 2 — Manual CSV via `BufferedReader`** (see `BasicOperationsTest.divideNumbers()`)

Define the `@DataProvider` directly in the test class and read the CSV file line-by-line.
Use `TEST_RESOURCES` from `framework/config/Constants.java` as the base path.

```java
@DataProvider(name = "division")
public static Object[][] divideNumbers() {
    String line;
    ArrayList<Object[]> outData = new ArrayList<>();
    try (BufferedReader csvFile = new BufferedReader(new FileReader(TEST_RESOURCES + "Division.csv"))) {
        while ((line = csvFile.readLine()) != null) {
            String[] data = line.split(",");
            outData.add(new Object[]{Integer.parseInt(data[0].trim()), Integer.parseInt(data[1].trim())});
        }
    } catch (IOException | NumberFormatException e) {
        throw new RuntimeException(e);
    }
    return outData.toArray(new Object[outData.size()][]);
}

@Test(dataProvider = "division", description = "Check division")
public void checkCalculatorDivision(int x, int y) {
    // Test implementation
}
```

**Option 3 — Reusable `CsvDataProvider` + `@CsvSource` annotation (OpenCSV)**  
(see `BasicDivisionTest.checkDivisionsFromCSVByReusableDataProviderVerify()`)

A reusable project-level `@DataProvider` backed by `CsvDataProvider` (uses OpenCSV internally).
Annotate the test with `@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class)` and `@CsvSource(path = ...)`.

```java
@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class,
        description = "Check division using reusable csv data provider")
@CsvSource(path = TEST_RESOURCES + "Division.csv")
public void checkDivisionsFromCSVByReusableDataProviderVerify(int x, int y) {
    // Test implementation
}
```

`TEST_RESOURCES` is defined in `framework/config/Constants.java`:

```java
public static final String TEST_RESOURCES = "src/test/resources/";
```

### Screenshot on Failure

`ScreenshotListener` captures a full-page screenshot and the raw HTML source on every test failure.  
Both are attached to the Allure report automatically.

- Location: `build/reports/screenshots/`
- Format: `timestamp-ClassName-testMethod.png`

---

## 🌱 Learning Path — Beginners

New to Selenium + TestNG? Work through these topics in order; each builds on the previous one.

1. **Run the project** — clone, run `.\gradlew.bat clean test`, open the Allure report.  
   Get comfortable with the feedback loop before reading any code.

2. **Read `BaseTest`** — understand `@BeforeClass` / `@AfterClass`, WebDriver lifecycle, and the three listeners.

3. **Trace one test end-to-end** → `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`.  
   Understand why each layer exists and what responsibility it owns.

4. **Study `WebdriverManager`** — see how `ThreadLocal<WebDriver>` makes parallel execution safe.

5. **Read `WebdriverFactory`** — learn how `switch` on `envBrowser()` selects the browser;  
   add a new browser by adding one `case`.

6. **Study `ConfigurationManager`** — understand Owner-based config and how system properties override the file.

7. **Add a new test** — pick a calculator button, write a test in the 3-layer style:  
   - `@Test` method in a test class that extends `BaseTest`  
   - `@Step` method in a Steps class  
   - `@FindBy` locator + interaction method in the Page class

8. **Try parameterization** — convert your test to use an inline `@DataProvider` with 3 data rows.

9. **Break a test on purpose** — see the screenshot attached to the Allure report automatically.

10. **Explore the `junit6` branch** — switch branches, run the same tests, compare the framework code.  
    Notice what changed and what stayed the same.

---

## 🔧 Troubleshooting

### Kill Chromedriver Processes (Windows)

If Chrome drivers are not closing properly:

```bash
taskkill /F /IM chromedriver.exe /T
```

### Clear Build Cache

If you encounter build issues:

```bash
.\gradlew.bat clean --refresh-dependencies
```

### WebDriver Issues

If you encounter WebDriver compatibility issues:
1. Ensure your WebDriver version matches your browser version
2. Download the latest WebDriver from the [WebDriver Setup](#-webdriver-setup) section
3. Replace the old driver in the `drivers` folder

### View Detailed Logs

Enable verbose logging:

```bash
.\gradlew.bat test --info   # verbose
.\gradlew.bat test --debug  # full debug
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org)
- [Allure Framework](https://allurereport.org/docs/)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [Selenium Grid Setup](https://www.selenium.dev/documentation/grid/)
- [WebDriver Downloads](https://www.selenium.dev/downloads/)
- [Forget WebDriverManager: Use Selenium Manager Instead](https://medium.com/@andrei.oleynik/forget-webdrivermanager-use-selenium-manager-instead-1acf7e1076f3)
- [TestNG XML Is a Legacy Concept: Here's What Modern Test Suites Should Look Like](https://medium.com/@andrei.oleynik/testng-xml-is-a-legacy-concept-heres-what-modern-test-suites-should-look-like-bd5cb380db71)
- [WebDriver getAttribute() is Back: When to Use It vs getDomAttribute() and getDomProperty()](https://medium.com/@andrei.oleynik/adventures-of-getattribute-back-from-the-dead-846c31f5bcab)
- [JUnit 6 Released — Clean-Up, Modernization & Minimal Disruption](https://medium.com/@andrei.oleynik/junit-6-released-clean-up-modernization-minimal-disruption-d3ecf11b64ad)

---

## 🔗 Useful Links

- [**Selenium Grid Downloads**](https://www.selenium.dev/downloads/)
- [**Java Download**](https://www.oracle.com/java/technologies/downloads/)
- [**Gradle Download**](https://gradle.org/install/)
- [**Lombok Download**](https://projectlombok.org/download)
- [**IntelliJ Lombok Plugin**](https://plugins.jetbrains.com/plugin/6317-lombok)

---

## ⭐ Found it useful?

If you found useful examples or information in this repository, please give it a ⭐  
Your support helps the project reach more Java and QA engineers.
