# 🚀 Gradle Selenium WebDriver JUnit 6 Example

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.38.0-green.svg)](https://www.selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit-6.0.1-green.svg)](https://junit.org/)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Allure_Framework](https://img.shields.io/badge/Allure_Framework-2.31.0-purple.svg)](https://allurereport.org/docs/)
[![Allure_CLI](https://img.shields.io/badge/Allure_CLI-2.35.1-yellow.svg)](https://allurereport.org/docs/)

A comprehensive test automation framework demonstrating best practices with Selenium WebDriver, JUnit 6, and Allure
reporting. This project showcases parameterized testing, parallel execution, automatic retry logic, and advanced
reporting capabilities.

> **Note:** This branch uses JUnit 6 as the testing framework. For a TestNG-based implementation, please check out the
`master` branch.

> **Note:** This branch uses manually downloaded WebDriver binaries. For automatic WebDriver management using Selenium Manager, please check out the `junit6` branch.


## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [WebDriver Setup](#-webdriver-setup)
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
- ⚙️ **Configuration management** using Owner library
- 🔒 **Lombok** for reducing boilerplate code

## 🛠️ Tech Stack

| Technology                         | Version       | Purpose                                                           |
|------------------------------------|---------------|-------------------------------------------------------------------|
| **Java**                           | 21            | Programming language                                              |
| **Gradle**                         | 8.x           | Build automation                                                  |
| **Selenium WebDriver**             | 4.38.0        | Browser automation                                                |
| **JUnit 6**                        | 6.0.1         | Testing framework                                                 |
| **Allure Framework (Allure Java)** | 2.31.0        | Used inside tests (annotations, listeners). Produces raw results. |
| **Allure Report CLI**              | 2.35.1        | Generates interactive HTML report from test results.              |
| **AssertJ**                        | 3.27.6        | Fluent assertions                                                 |
| **Apache POI**                     | 5.5.0         | Excel report generation                                           |
| **OpenCSV**                        | 5.12.0        | CSV data handling                                                 |
| **Owner**                          | 1.0.12        | Configuration management                                          |
| **Lombok**                         | 1.18.42       | Code generation                                                   |
| **Jackson**                        | 2.20.1        | JSON processing                                                   |
| **Log4j/SLF4J**                    | 2.24.3/2.0.17 | Logging                                                           |

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
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")                  // Enable parallel execution
    systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")       // Dynamic thread allocation
    systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")            // Auto-detect extensions
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
├── drivers/                            # WebDriver binaries (manually downloaded)
├── build.gradle                        # Build configuration
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

**Location:** `build/excel-results/testExecutionReport_ddmmyy_hhmmss.xlsx`

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
    maxParallelForks = 3  // Number of parallel test processes
}
```

You can also configure parallel execution in `junit-platform.properties`:

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.config.strategy=dynamic
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

Tests support parameterization via JUnit 6's `@ParameterizedTest`:

```java

@ParameterizedTest
@CsvSource({
        "2, 3, 5",
        "10, 5, 15",
        "-1, 1, 0"
})
@DisplayName("Test addition with multiple inputs")
public void testAddition(int a, int b, int expected) {
    // Test implementation
}
```

Or using `@MethodSource`:

```java

@ParameterizedTest
@MethodSource("additionData")
public void testAddition(int a, int b, int expected) {
    // Test implementation
}

static Stream<Arguments> additionData() {
    return Stream.of(
            Arguments.of(2, 3, 5),
            Arguments.of(10, 5, 15),
            Arguments.of(-1, 1, 0)
    );
}
```

### CSV Data Support

Load test data from CSV files using JUnit 6's `@CsvFileSource`:

```java

@ParameterizedTest
@CsvFileSource(resources = "/Division.csv", numLinesToSkip = 1)
public void testDivision(int dividend, int divisor, int expected) {
    // Test implementation
}
```

Or use a custom data provider:

```java

@ParameterizedTest
@MethodSource("divisionData")
public void testDivision(int dividend, int divisor, int expected) {
    // Test implementation
}

static Stream<Arguments> divisionData() throws Exception {
    return CsvDataProvider.getData("src/test/resources/Division.csv");
}
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
- [Allure Framework](https://docs.qameta.io/allure/)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [Selenium Grid Setup](https://www.selenium.dev/documentation/grid/)
- [WebDriver Downloads](https://www.selenium.dev/downloads/)

## 🔗 Useful Links

- **Selenium Grid Downloads:** https://www.selenium.dev/downloads/
- **Java Download:** https://www.oracle.com/java/technologies/downloads/
- **Gradle Download:** https://gradle.org/install/
