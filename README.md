# Gradle Selenium Webdriver example

### Usage

* To start test execution

```
.\gradlew.bat clean test
```

* To start execution of a concrete test

```
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.CalculatorSanityTest.checkCalculatorOpening
```
* To execute tests in the package com.oleynik.gradle.selenium.example.test

```
.\gradlew.bat clean test --tests com.oleynik.gradle.selenium.example.test.* 
```
* Compile the project

```
.\gradlew clean compileTestJava
```

### Reporting

* Generated Allure report can be found in
```
{project directory}/build/reports/allure-report/allureReport/index.html
```

* Generated JUnit report can be found in
```
{project directory}/build/reports/tests/test/index.html
```
* Generated excel report can be found in
```
{project directory}/build/reports/testExecutionReport_ddmmyy_hhmmss.xlsx
```
### Webdriver

* Webdrivers are managed by Selenium Manager

* To kill Chromedriver processes on Windows execute
```
taskkill /F /IM chromedriver.exe /T
```

### Webdriver sources

* Selenium Server (Grid)
```
https://www.selenium.dev/downloads/
```
