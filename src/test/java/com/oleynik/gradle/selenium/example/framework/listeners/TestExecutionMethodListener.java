package com.oleynik.gradle.selenium.example.framework.listeners;

import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.time.Instant;
import java.time.ZonedDateTime;

import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.ENVIRONMENT_ZONE_ID;
import static com.oleynik.gradle.selenium.example.framework.utils.TestNGUtils.getExecutionStatus;

public class TestExecutionMethodListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            String testClass = testResult.getInstance().getClass().getSimpleName();
            String testMethod = testResult.getMethod().getMethodName();
            ZonedDateTime testStartDateTime = ZonedDateTime
                    .ofInstant(Instant.ofEpochMilli(testResult.getStartMillis()), ENVIRONMENT_ZONE_ID);
            ZonedDateTime testEndDateTime = ZonedDateTime
                    .ofInstant(Instant.ofEpochMilli(testResult.getEndMillis()), ENVIRONMENT_ZONE_ID);
            TestExecutionResult testExecutionResult = new TestExecutionResult
                    .TestResultBuilder(testClass, testMethod)
                    .testParameters(testResult.getParameters())
                    .testStartDateTime(testStartDateTime)
                    .testEndDateTime(testEndDateTime)
                    .exception(testResult.getThrowable())
                    .testStatus(getExecutionStatus(testResult.getStatus()))
                    .build();
            TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
        }
    }
}
