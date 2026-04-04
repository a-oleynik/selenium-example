# 🚀 Gradle Selenium WebDriver JUnit 6 Example

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.41.0-green.svg)](https://www.selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit-6.1.0_M1-green.svg)](https://junit.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue.svg)](https://gradle.org/)
[![Allure_Framework](https://img.shields.io/badge/Allure_Framework-2.33.0-purple.svg)](https://allurereport.org/docs/)
[![Allure_CLI](https://img.shields.io/badge/Allure_CLI-2.35.1-yellow.svg)](https://allurereport.org/docs/)

A comprehensive test automation framework demonstrating best practices with Selenium WebDriver, JUnit 6, and Allure
reporting. This project showcases parameterized testing, parallel execution, automatic retry logic, and advanced
reporting capabilities.

> **Note:** This branch uses JUnit 6 as the testing framework. For a TestNG-based implementation, please check out the
`master` branch.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Reporting](#-reporting)
- [Advanced Features](#-advanced-features)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)
- [Additional Resources](#-additional-resources)
- [Useful Links](#-useful-links)

## ✨ Features

- 🔧 **Page Object Model (POM)** / **Page Object design pattern**
- 🔄 **Parallel test execution** with configurable thread count
- 🎯 **Parameterized tests** using JUnit 6 @ParameterizedTest
- 🧪 **JUnit 6 extensions** for custom test lifecycle management
- 📊 **Multiple reporting formats**: Allure and Excel
- 🔁 **Automatic test retry** on failure
- 📸 **Screenshot capture** on test failure
- 📄 **Page source capture** for debugging
- 🌐 **Cross-browser support** (Chrome, Firefox, Edge)
- 🎨 **Allure annotations** for rich test documentation
- 🔍 **AssertJ** for fluent assertions
- 📦 **Automatic WebDriver management** via Selenium Manager
- ⚙️ **Configuration management** using Owner library
- 🔒 **Lombok** for reducing boilerplate code

## 🛠️ Tech Stack

| Technology                         | Version       | Purpose                                                           |
|------------------------------------|---------------|-------------------------------------------------------------------|
| **Java**                           | 21            | Programming language                                              |
| **Gradle**                         | 9.1.0         | Build automation                                                  |
| **Selenium WebDriver**             | 4.41.0        | Browser automation                                                |
| **JUnit 6**                        | 6.1.0-M1      | Testing framework                                                 |
| **Allure Framework (Allure Java)** | 2.33.0        | Used inside tests (annotations, listeners). Produces raw results. |
| **Allure Report CLI**              | 2.35.1        | Generates interactive HTML report from test results.              |
| **AssertJ**                        | 3.27.6        | Fluent assertions                                                 |
| **Apache POI**                     | 5.5.0         | Excel report generation                                           |
| **OpenCSV**                        | 5.12.0        | CSV data handling                                                 |
| **Owner**                          | 1.0.12        | Configuration management                                          |
| **Lombok**                         | 1.18.44       | Code generation                                                   |
| **Jackson**                        | 2.21.2        | JSON processing                                                   |
| **Log4j/SLF4J**                    | 2.25.3/2.0.17 | Logging                                                           |

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or higher
    - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
    - Verify: `java -version`

- **Gradle** (optional, wrapper included)
    - Verify: `gradle -version`

- **Git** (for cloning the repository)
    - [Download Git](https://git-scm.com/downloads)
    - Verify: `git --version`

- **Web Browser** (Chrome, Firefox, or Edge)
    - Drivers are managed automatically by Selenium Manager

## 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/selenium-example.git
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
        maxFailures = 100
        // Maximum failures before stopping
        maxRetries = 1                                                                  // Number of retry attempts
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

To run tests with different browsers:

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
│       │       ├── pages/              # Page Object classes
│       │       ├── steps/              # Test step definitions
│       │       └── test/               # Test classes
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
│   │   ├── allure-report/
│   │   ├── tests/
│   │   └── *.xlsx
│   └── test-results/                   # Test execution results
├── drivers/                            # WebDriver binaries (managed automatically)
├── build.gradle                        # Build configuration
├── lombok.config                       # Lombok annotation-processor settings (do not delete)
├── gradlew.bat                         # Gradle wrapper (Windows)
├── gradlew                             # Gradle wrapper (Linux/Mac)
└── README.md                           # This file
```

## 📊 Reporting

### Allure Report

**Location:** `build/reports/allure-report/allureReport/index.html`

**Features:**

- 📈 Test execution timeline
- 📊 Test result statistics
- 📸 Screenshots on failure
- 📄 Page source capture
- 🏷️ Test categorization
- 📝 Detailed test steps
- 📉 Trend analysis

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
- ⏱️ Execution time
- 📋 Test class grouping
- ❌ Failure details
- 📊 Test results breakdown

### Excel Report

**Location:** `build/reports/testExecutionReport_ddmmyy_hhmmss.xlsx`

**Features:**

- 📊 Test execution summary
- 📅 Timestamp information
- ✅ Status tracking
- 🔢 Test parameters
- ⏱️ Duration metrics

## 🎯 Advanced Features

### Parallel Execution

Tests run in parallel using JUnit 6's parallel execution capabilities. Configure in `build.gradle`:

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

Failed tests are automatically retried once:

```groovy
retry {
    failOnPassedAfterRetry = true
    maxFailures = 100
    maxRetries = 1
}
```

### Data-Driven Testing

Tests support parameterization via JUnit 6's `@ParameterizedTest` + `@MethodSource`.
Define a `static Object[][]` method in the same class and reference it by name.
See `BasicOperationsTest` — `addNumbers()`, `subtractNumbers()`, `multiplyNumbers()`.

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


### CSV Data Support

**Option 1 — JUnit built-in `@CsvFileSource` (classpath resource)**

Place the CSV in `src/test/resources/` and reference it by classpath path.
No header row skipping is needed unless the file contains one.
See `BasicOperationsTest.checkCalculatorDivision()`.

```java
@ParameterizedTest
@Description("Check division")
@CsvFileSource(resources = "/Division.csv")
public void checkCalculatorDivision(int x, int y) {
    // Test implementation
}
```

**Option 2 — Custom `@ParameterizedDataSource` annotation (filesystem path, OpenCSV)**

A reusable project-level annotation backed by `CSVDataProvider` (`ArgumentsProvider` + `AnnotationConsumer`).
Pass the file path relative to the project root using the `TEST_RESOURCES` constant.
See `BasicDivisionTest.checkDivisionsFromCSVByReusableDataProviderVerify()`.

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

Screenshots are automatically captured on test failure and attached to Allure reports:

- Location: `build/screenshots/`
- Format: `timestamp-ClassName-testMethod.png`

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
.\gradlew.bat test --info
```

Or debug mode:

```bash
.\gradlew.bat test --debug
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [JUnit 6 User Guide](https://docs.junit.org/current/user-guide/)
- [Allure Framework](https://allurereport.org/docs/)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [Selenium Manager](https://www.selenium.dev/documentation/selenium_manager/)
- [Selenium Grid Setup](https://www.selenium.dev/documentation/grid/)
- [Forget WebDriverManager: Use Selenium Manager Instead](https://medium.com/@andrei.oleynik/forget-webdrivermanager-use-selenium-manager-instead-1acf7e1076f3)
- [TestNG XML Is a Legacy Concept: Here's What Modern Test Suites Should Look Like](https://medium.com/@andrei.oleynik/testng-xml-is-a-legacy-concept-heres-what-modern-test-suites-should-look-like-bd5cb380db61)
- [WebDriver getAttribute() is Back: When to Use It vs getDomAttribute() and getDomProperty()](https://medium.com/@andrei.oleynik/adventures-of-getattribute-back-from-the-dead-846c31f5bcab)

## 🔗 Useful Links

- **Selenium Grid Downloads:** https://www.selenium.dev/downloads/
- **Java Download:** https://www.oracle.com/java/technologies/downloads/
- **Gradle Download:** https://gradle.org/install/
