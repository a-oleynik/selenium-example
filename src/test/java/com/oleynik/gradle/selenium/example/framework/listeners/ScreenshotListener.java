package com.oleynik.gradle.selenium.example.framework.listeners;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import static com.oleynik.gradle.selenium.example.framework.config.Constants.SCREENSHOTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForAllureConsoleLog;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForScreenshotName;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.saveBytesAsFile;
import static java.lang.String.format;

public class ScreenshotListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        boolean isTestSkipped = (testResult.getStatus() == 3);
        if (!testResult.isSuccess() && (!isTestSkipped)) {
            try {
                WebDriver driver = WebdriverManager.getDriver();
                if (null != driver) {
                    String testMethod = method.getTestMethod().getMethodName();
                    byte[] screenshotBytes = makeScreenshotOnFailure(testMethod, driver);
                    saveScreenshot(method, screenshotBytes);
                    String pageSource = makePageSource(testMethod, driver);
                    savePageSource(method, pageSource);
                }
            } catch (Exception exception) {
                Allure.step(format("%s exception thrown. Exception message:\n%s", exception.getClass().getSimpleName(),
                        exception.getMessage()), Status.FAILED);
            }
        }
    }

    @Attachment(value = "{testName} - screenshot", type = "image/png")
    private byte[] makeScreenshotOnFailure(String testName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{testName} - page source", type = "text/plain", fileExtension = ".html")
    private String makePageSource(String testName, WebDriver driver) {
        return driver.getPageSource();
    }

    private void saveScreenshot(IInvokedMethod method, byte[] screenshotBytes) {
        String testClass = method.getTestResult()
                .getTestClass()
                .getRealClass()
                .getSimpleName();
        String testMethod = method.getTestMethod().getMethodName();
        String screenshotName = format("%s-%s-%s.png", getDateTimeForScreenshotName(), testClass,
                testMethod);
        String screenshotPath = SCREENSHOTS_FOLDER + screenshotName;
        createDirectoryIfNotExist(SCREENSHOTS_FOLDER);
        saveBytesAsFile(screenshotPath, screenshotBytes);
        System.out.println(getDateTimeForAllureConsoleLog() + ": screenshot saved in " + screenshotPath);
    }

    private void savePageSource(IInvokedMethod method, String pageSource) {
        String testClass = method.getTestResult()
                .getTestClass()
                .getRealClass()
                .getSimpleName();
        String testMethod = method.getTestMethod().getMethodName();
        String pageSourceName = format("%s-%s-%s.html", getDateTimeForScreenshotName(), testClass,
                testMethod);
        String pageSourcePath = SCREENSHOTS_FOLDER + pageSourceName;
        createDirectoryIfNotExist(SCREENSHOTS_FOLDER);
        saveBytesAsFile(pageSourcePath, pageSource.getBytes());
        System.out.println(getDateTimeForAllureConsoleLog() + ": page source saved in " + pageSourcePath);
    }
}
