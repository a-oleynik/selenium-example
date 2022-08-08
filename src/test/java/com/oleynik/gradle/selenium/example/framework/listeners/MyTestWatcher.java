package com.oleynik.gradle.selenium.example.framework.listeners;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import com.oleynik.gradle.selenium.example.framework.reporting.ExecutionStatus;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.oleynik.gradle.selenium.example.framework.config.Constants.SCREENSHOTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.*;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.saveBytesAsFile;
import static java.lang.String.format;

public class MyTestWatcher implements BeforeEachCallback, TestWatcher {
    private long startTime;
    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create("com", "oleynik", "gradle", "selenium", "example", "framework",
                    "listeners", "MyInvocationInterceptor");
    public static final String METHOD_PARAMETERS = "method.parameters";

    @Override
    public void beforeEach(ExtensionContext context) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable throwable) {
        TestExecutionResult testExecutionResult = generateTestExecutionResult(context, ExecutionStatus.SKIP);
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> optional) {
        TestExecutionResult testExecutionResult = generateTestExecutionResult(context, ExecutionStatus.DISABLED);
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        if (context.getExecutionException().isPresent()) {
            WebDriver driver = WebdriverManager.getDriver();
            if (null != driver) {
                String testMethod = context.getRequiredTestMethod().getName();
                byte[] screenshotBytes = makeScreenshotOnFailure(testMethod, driver);
                saveScreenshot(context, screenshotBytes);
                String pageSource = makePageSource(testMethod, driver);
                savePageSource(context, pageSource);
            }
        }
        TestExecutionResult testExecutionResult = generateTestExecutionResult(context, ExecutionStatus.FAIL);
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        TestExecutionResult testExecutionResult = generateTestExecutionResult(context, ExecutionStatus.PASS);
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    private TestExecutionResult generateTestExecutionResult(ExtensionContext context, ExecutionStatus executionStatus) {
        ZonedDateTime testStartDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(startTime), ENVIRONMENT_ZONE_ID);
        ZonedDateTime testEndDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ENVIRONMENT_ZONE_ID);
        String testClass = context.getRequiredTestClass().getSimpleName();
        String testMethod = context.getRequiredTestMethod().getName();
        @SuppressWarnings("unchecked")
        TestExecutionResult testExecutionResult = TestExecutionResult.builder()
                .testClass(testClass)
                .testMethod(testMethod)
                .testParameters((List<Object>) context.getStore(NAMESPACE).get(METHOD_PARAMETERS))
                .testStartDateTime(testStartDateTime)
                .testEndDateTime(testEndDateTime)
                .exception(context.getExecutionException().orElse(null))
                .executionStatus(executionStatus)
                .build();
        return testExecutionResult;
    }

    @Attachment(value = "{testName} - screenshot", type = "image/png")
    private byte[] makeScreenshotOnFailure(String testName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{testName} - page source", type = "text/plain", fileExtension = ".html")
    private String makePageSource(String testName, WebDriver driver) {
        return driver.getPageSource();
    }

    private void saveScreenshot(ExtensionContext context, byte[] screenshotBytes) {
        String testClass = context.getRequiredTestClass().getSimpleName();
        String testMethod = context.getRequiredTestMethod().getName();
        String screenshotName = format("%s-%s-%s.png", getDateTimeForScreenshotName(), testClass,
                testMethod);
        String screenshotPath = SCREENSHOTS_FOLDER + screenshotName;
        createDirectoryIfNotExist(SCREENSHOTS_FOLDER);
        saveBytesAsFile(screenshotPath, screenshotBytes);
        System.out.println(getDateTimeForAllureConsoleLog() + ": screenshot saved in " + screenshotPath);
    }

    private void savePageSource(ExtensionContext context, String pageSource) {
        String testClass = context.getRequiredTestClass().getSimpleName();
        String testMethod = context.getRequiredTestMethod().getName();
        String pageSourceName = format("%s-%s-%s.html", getDateTimeForScreenshotName(), testClass,
                testMethod);
        String pageSourcePath = SCREENSHOTS_FOLDER + pageSourceName;
        createDirectoryIfNotExist(SCREENSHOTS_FOLDER);
        saveBytesAsFile(pageSourcePath, pageSource.getBytes());
        System.out.println(getDateTimeForAllureConsoleLog() + ": page source saved in " + pageSourcePath);
    }
}
