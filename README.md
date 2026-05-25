# 🚀 Gradle Selenium WebDriver JUnit 6 Example (Manual Driver Management)

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.44.0-green.svg)](https://www.selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit-6.1.0RC1-green.svg)](https://junit.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue.svg)](https://gradle.org/)
[![Allure_Framework](https://img.shields.io/badge/Allure_Framework-2.34.0-purple.svg)](https://allurereport.org/docs/)
[![Allure_CLI](https://img.shields.io/badge/Allure_CLI-2.35.1-yellow.svg)](https://allurereport.org/docs/)

A comprehensive test automation framework demonstrating best practices with Selenium WebDriver, JUnit 6, and Allure
reporting. This project showcases parameterized testing, parallel execution, automatic retry logic, and advanced
reporting capabilities.

> **Note:** This branch uses JUnit 6 as the testing framework and manually downloaded WebDriver binaries. For a TestNG-based implementation, please check out the
[`master`](https://github.com/a-oleynik/selenium-example/tree/master) branch.  
> For a JUnit 6 implementation with Selenium Manager see the [`junit6`](https://github.com/a-oleynik/selenium-example/tree/junit6) branch.
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
- **study data-driven testing** — inline `@MethodSource`, `@CsvFileSource` for classpath CSV, and reusable OpenCSV-backed `@ParameterizedDataSource`
- **understand enterprise-grade reporting** — Allure with steps/attachments/screenshots, JUnit HTML, and Excel
- **learn automatic retry, screenshot capture, and page source attachment** without writing infrastructure from scratch
- **use it as a reference** for workshops, tech talks, onboarding sessions, and self-study
- **compare TestNG and JUnit patterns** with the companion [TestNG Workshop](https://github.com/a-oleynik/testng-workshop) and [JUnit Workshop](https://github.com/a-oleynik/junit-workshop)

---

## 👥 Who Is This For?

| Audience                                                     | What you will get                                                                   |
|--------------------------------------------------------------|-------------------------------------------------------------------------------------|
| **QA engineers new to Selenium + JUnit 6**                   | A complete working framework to clone and run on day one                            |
| **Java developers learning test automation**                 | Real patterns — POM, Steps, ThreadLocal driver, config management                   |
| **Engineers migrating from JUnit to TestNG** (or vice versa) | Side-by-side branches with identical features, different frameworks                 |
| **Senior / lead engineers**                                  | Deep-dives into parallel execution, retry, custom listeners, and Allure integration |
| **Workshop facilitators**                                    | A ready-made project with multiple difficulty levels you can hand to attendees      |

---

## ✨ Features

- 🔧 **3-Layer architecture with Page Object Model** — Test → Steps → Page, strictly enforced
- 🔄 **Parallel test execution** at class level with ThreadLocal WebDriver (thread-safe)
- 🎯 **Parameterized tests** using JUnit 6 @ParameterizedTest
- 🧪 **JUnit 6 extensions** for custom test lifecycle management
- 📊 **Multiple reporting formats**: Allure and Excel
- 🔁 **Automatic test retry** on failure (Gradle test-retry plugin)
- 📸 **Screenshot + page source capture** on test failure, attached to Allure
- 🌐 **Cross-browser support** — Chrome, Firefox, Edge (one `switch` in `WebdriverFactory`)
- 🎨 **Rich Allure annotations** — `@Feature`, `@Step`, `@Attachment`, `@Description`
- 🔍 **AssertJ** for fluent assertions and `SoftAssertions`
- 📦 **Automatic WebDriver management** via Selenium Manager — zero binary setup
- ⚙️ **Configuration management** via Owner library — override any value with a system property
- 🔒 **Lombok** for reducing boilerplate code

---

## 🛠️ Tech Stack

| Technology                         | Version       | Purpose                                                           |
|------------------------------------|---------------|-------------------------------------------------------------------|
| **Java**                           | 21            | Programming language                                              |
| **Gradle**                         | 9.1.0         | Build automation                                                  |
| **Selenium WebDriver**             | 4.44.0        | Browser automation                                                |
| **JUnit 6**                        | 6.1.0-RC1     | Testing framework                                                 |
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

| Plugin                          | Version  | Purpose                                                             |
|---------------------------------|----------|---------------------------------------------------------------------|
| `io.qameta.allure`              | 4.0.2    | Allure reporting integration for Gradle                             |
| `org.gradle.test-retry`         | 1.6.5    | Automatically retries failed tests to handle flakiness              |
| `com.github.ben-manes.versions` | 0.54.0   | Checks for dependency updates via `.\gradlew.bat dependencyUpdates` |
| `java-library`                  | Built-in | Core Java project support                                           |
| `idea`                          | Built-in | IntelliJ IDEA project file generation and integration               |

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

- **Web Browser** (Chrome, Firefox, or Edge)
    - Drivers are managed automatically by Selenium Manager — no manual setup required

---

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

---

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
    useJUnitPlatform()                                                                  // Enable JUnit 6
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")                          // Enable parallel execution
    systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")             // Dynamic thread allocation
    systemProperty("junit.jupiter.execution.parallel.config.executor-service", "WORKER_THREAD_POOL") // Use regular thread pool
    systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")                  // Auto-detect extensions
    maxParallelForks = 3
    // Number of parallel test processes

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

### Run Tests with Tags

Execute all tests with tag "Regression":

```bash
.\gradlew.bat clean test -DincludeTags="Regression"
```

### Run Tests excluding Tags

Execute all tests excluding those with tag "Flaky":

```bash
.\gradlew.bat clean test -DexcludeTags="Flaky"
```

### Run Tests from a package filtered by Tags

Execute all tests from a package and tags -DincludeTags="Regression":

```bash
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.* -DincludeTags="Regression"
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
│       │       │   ├── config/         # Configuration management
│       │       │   ├── dataproviders/  # CSVDataProvider (OpenCSV ArgumentsProvider) + @ParameterizedDataSource annotation
│       │       │   ├── listeners/      # JUnit 6 extensions
│       │       │   ├── manager/        # WebDriver management
│       │       │   ├── reporting/      # Custom reporting
│       │       │   └── utils/          # Utility classes
│       │       ├── pages/              # Page Object classes (PageFactory + @FindBy)
│       │       ├── steps/              # @Step-annotated business actions + assertions
│       │       └── test/               # JUnit 6 test classes (extend BaseTest)
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
│   │   ├── tests/
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

### JUnit HTML Report

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
test/    → JUnit test class    (@Feature, @Test, extends BaseTest)
steps/   → @Step-annotated methods that call page methods and run assertions
pages/   → Page Object class    (PageFactory + @FindBy, interacts with the DOM only)
```

Example chain: `CalculatorSanityTest` → `CalculatorSteps` → `CalculatorPage`

This separation keeps tests readable, pages reusable, and assertions in one place.

### Parallel Execution

Tests run in parallel using JUnit 6's parallel execution capabilities. Configure in `build.gradle`.
WebDriver is stored in a `ThreadLocal` — each thread gets its own isolated browser instance.

```groovy
test {
    useJUnitPlatform()
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
    systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")
    systemProperty("junit.jupiter.execution.parallel.config.executor-service", "WORKER_THREAD_POOL")
    maxParallelForks = 3  // Number of parallel test processes
}
```

You can also configure parallel execution in `junit-platform.properties`:

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.config.strategy=dynamic
junit.jupiter.execution.parallel.config.executor-service=WORKER_THREAD_POOL
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

**Option 1 — Inline `@MethodSource`** (see `BasicOperationsTest`)

Define a `static Object[][]` method in the same class and reference it by name with `@MethodSource`.

```java
public static Object[][] addNumbers() {
    return new Object[][]{
            {2, 10},
            {99, 7},
            {111, 11},
            {0, 0},
            {1, 1}
    };
}

@ParameterizedTest
@MethodSource("addNumbers")
@Description("Check addition")
public void checkCalculatorAdditions(int x, int y) {
    // Test implementation
}
```

**Option 2 — JUnit built-in `@CsvFileSource` (classpath resource)**  
(see `BasicOperationsTest.checkCalculatorDivision()`)

Place the CSV in `src/test/resources/` and reference it by classpath path.

```java
@ParameterizedTest
@Description("Check division")
@CsvFileSource(resources = "/Division.csv")
public void checkCalculatorDivision(int x, int y) {
    // Test implementation
}
```

**Option 3 — Custom `@ParameterizedDataSource` annotation (filesystem path, OpenCSV)**  
(see `BasicDivisionTest.checkDivisionsFromCSVByReusableDataProviderVerify()`)

A reusable project-level annotation backed by `CSVDataProvider` (`ArgumentsProvider` + `AnnotationConsumer`).
Pass the file path relative to the project root using the `TEST_RESOURCES` constant.

```java
@ParameterizedTest
@Description("Check division using reusable csv data provider")
@ParameterizedDataSource(path = TEST_RESOURCES + "Division.csv")
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

New to Selenium + JUnit 6? Work through these topics in order; each builds on the previous one.

1. **Run the project** — clone, run `.\gradlew.bat clean test`, open the Allure report.  
   Get comfortable with the feedback loop before reading any code.

2. **Read `BaseTest`** — understand `@BeforeAll`/`@AfterAll` (WebDriver lifecycle), `@BeforeEach` (driver self-heal), `@TestInstance(PER_CLASS)` (one driver per class), and `@ExtendWith(MyTestWatcher.class)` (screenshot + page source on failure).

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

8. **Try parameterization** — convert your test to use `@ParameterizedTest` + `@MethodSource` with 3 data rows.

9. **Break a test on purpose** — see the screenshot attached to the Allure report automatically.

10. **Explore the `master` branch** — switch branches, run the same tests, compare the TestNG framework code.  
    Notice what changed (listeners vs extensions, `@DataProvider` vs `@MethodSource`) and what stayed the same.

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

### Update WebDriver

WebDrivers are managed automatically by Selenium Manager. To force update:

```bash
.\gradlew.bat clean test --refresh-dependencies
```

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
- [JUnit 6 User Guide](https://docs.junit.org/current/user-guide/)
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
