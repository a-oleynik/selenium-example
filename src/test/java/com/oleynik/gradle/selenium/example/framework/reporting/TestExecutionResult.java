package com.oleynik.gradle.selenium.example.framework.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestExecutionResult implements Comparable<TestExecutionResult> {
    private final String testClass;
    private final String testMethod;
    private Object[] testParameters;
    private ZonedDateTime testStartDateTime;
    private ZonedDateTime testEndDateTime;
    private ExecutionStatus executionStatus;
    private Throwable exception;

    public String getPrintableTestParameters() {
        if (testParameters.length > 0) {
            return Arrays.toString(testParameters);
        } else {
            return "";
        }
    }

    public ExecutionStatus getTestStatus() {
        return executionStatus;
    }

    public int compareTo(@NonNull TestExecutionResult result) {
        return Comparator.comparing(TestExecutionResult::getTestClass)
                .thenComparing(TestExecutionResult::getTestMethod)
                .thenComparing(TestExecutionResult::getPrintableTestParameters)
                .thenComparing(TestExecutionResult::getTestStatus)
                .compare(this, result);
    }
}
