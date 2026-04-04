# 🚀 Gradle Selenium WebDriver TestNG Example

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.41.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.12.0-red.svg)](https://testng.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue.svg)](https://gradle.org/)
[![Allure_Framework](https://img.shields.io/badge/Allure_Framework-2.33.0-purple.svg)](https://allurereport.org/docs/)
[![Allure_CLI](https://img.shields.io/badge/Allure_CLI-2.35.1-yellow.svg)](https://allurereport.org/docs/)

A comprehensive test automation framework demonstrating best practices with Selenium WebDriver, TestNG, and Allure
reporting. This project showcases parameterized testing, parallel execution, automatic retry logic, and advanced
reporting capabilities.

> **Note:** This branch uses TestNG 7 as the testing framework. For a JUnit-based implementation, please check out the
`junit6` branch.

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
- 🎯 **Parameterized tests** using TestNG DataProviders
- 🧪 **TestNG listeners** for custom test lifecycle management
- 📊 **Multiple reporting formats**: Allure, TestNG HTML, and Excel
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
| **TestNG**                         | 7.12.0        | Testing framework                                                 |
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
    useTestNG {
        parallel = 'classes'
        // Parallel execution at class level
        threadCount = 3                                                                 // Number of parallel threads
        useDefaultListeners = true
        outputDirectory = layout.buildDirectory.file("reports/testng").get().asFile
        // TestNG report output directory
    }

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

## 📁 Project Structure

```
selenium-example/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/oleynik/gradle/selenium/example/
│       │       ├── framework/          # Framework utilities
│       │       │   ├── config/         # Configuration management
│       │       │   ├── listeners/      # TestNG listeners
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

### TestNG HTML Report

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

Tests run in parallel at the class level with 3 threads by default. Configure in `build.gradle`:

```groovy
test {
    useTestNG {
        parallel = 'classes'  // Options: methods, tests, classes, instances
        threadCount = 3       // Number of parallel threads
    }
}
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

Tests support parameterization via TestNG `@DataProvider`:

```java
@DataProvider(name = "additionData")
public static Object[][] additionData() {
    return new Object[][]{
            {2, 10},
            {99, 7},
            {111, 11}
    };
}

@Test(dataProvider = "additionData", description = "Check addition")
public void checkCalculatorAdditions(int x, int y) {
    // Test implementation
}
```

### CSV Data Support

**Option 1 — Manual `BufferedReader` inside an inline `@DataProvider`**

Define the `@DataProvider` directly in the test class and read the CSV file line-by-line using `BufferedReader`.
Use the `TEST_RESOURCES` constant from `framework/config/Constants.java` for the file path.
See `BasicOperationsTest.divideNumbers()`.

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

**Option 2 — Reusable `CsvDataProvider` + `@CsvSource` annotation (OpenCSV)**

A reusable project-level `@DataProvider` backed by `CsvDataProvider` (uses OpenCSV internally).
Annotate the test method with both `@Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class)` and `@CsvSource(path = ...)`.
See `BasicDivisionTest.checkDivisionsFromCSVByReusableDataProviderVerify()`.

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
- [TestNG Documentation](https://testng.org)
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
