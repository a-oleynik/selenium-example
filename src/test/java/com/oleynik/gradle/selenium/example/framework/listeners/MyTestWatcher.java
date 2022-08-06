package com.oleynik.gradle.selenium.example.framework.listeners;

import com.oleynik.gradle.selenium.example.framework.reporting.ExecutionStatus;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.junit.jupiter.api.extension.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.ENVIRONMENT_ZONE_ID;

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
        ZonedDateTime testStartDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(startTime), ENVIRONMENT_ZONE_ID);
        ZonedDateTime testEndDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ENVIRONMENT_ZONE_ID);
        String testClass = context.getTestClass().get().getSimpleName();
        String testMethod = context.getTestMethod().get().getName();
        TestExecutionResult testExecutionResult = TestExecutionResult.builder()
                .testClass(testClass)
                .testMethod(testMethod)
                .testParameters((List<Object>)context.getStore(NAMESPACE).get(METHOD_PARAMETERS))
                .testStartDateTime(testStartDateTime)
                .testEndDateTime(testEndDateTime)
                .exception(context.getExecutionException().orElse(null))
                .executionStatus(ExecutionStatus.SKIP)
                .build();
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> optional) {
        ZonedDateTime testStartDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(startTime), ENVIRONMENT_ZONE_ID);
        ZonedDateTime testEndDateTime = testStartDateTime;
        String testClass = context.getTestClass().get().getSimpleName();
        String testMethod = context.getTestMethod().get().getName();
        TestExecutionResult testExecutionResult = TestExecutionResult.builder()
                .testClass(testClass)
                .testMethod(testMethod)
                .testParameters((List<Object>)context.getStore(NAMESPACE).get(METHOD_PARAMETERS))
                .testStartDateTime(testStartDateTime)
                .testEndDateTime(testEndDateTime)
                .exception(context.getExecutionException().orElse(null))
                .executionStatus(ExecutionStatus.DISABLED)
                .build();
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        ;
        ZonedDateTime testStartDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(startTime), ENVIRONMENT_ZONE_ID);
        ZonedDateTime testEndDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ENVIRONMENT_ZONE_ID);
        String testClass = context.getTestClass().get().getSimpleName();
        String testMethod = context.getTestMethod().get().getName();
        TestExecutionResult testExecutionResult = TestExecutionResult.builder()
                .testClass(testClass)
                .testMethod(testMethod)
                .testParameters((List<Object>)context.getStore(NAMESPACE).get(METHOD_PARAMETERS))
                .testStartDateTime(testStartDateTime)
                .testEndDateTime(testEndDateTime)
                .exception(context.getExecutionException().orElse(null))
                .executionStatus(ExecutionStatus.FAIL)
                .build();
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        context.getStore(NAMESPACE);
        ZonedDateTime testStartDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(startTime), ENVIRONMENT_ZONE_ID);
        ZonedDateTime testEndDateTime = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ENVIRONMENT_ZONE_ID);
        String testClass = context.getTestClass().get().getSimpleName();
        String testMethod = context.getTestMethod().get().getName();
        TestExecutionResult testExecutionResult = TestExecutionResult.builder()
                .testClass(testClass)
                .testMethod(testMethod)
                .testParameters((List<Object>)context.getStore(NAMESPACE).get(METHOD_PARAMETERS))
                .testStartDateTime(testStartDateTime)
                .testEndDateTime(testEndDateTime)
                .exception(context.getExecutionException().orElse(null))
                .executionStatus(ExecutionStatus.PASS)
                .build();
        TestExecutionResultCollector.addTestExecutionResult(testExecutionResult);
    }
}
