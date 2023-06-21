package com.oleynik.gradle.selenium.example.framework.reporting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;



@Jacksonized
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestExecutionResult implements Comparable<TestExecutionResult> {
    @JsonProperty
    private final String testClass;
    @JsonProperty
    private final String testMethod;
    @JsonProperty
    private List<Object> testParameters;
    @JsonProperty
    private ZonedDateTime testStartDateTime;
    @JsonProperty
    private ZonedDateTime testEndDateTime;
    @JsonProperty
    private ExecutionStatus executionStatus;
    @JsonProperty
    private Throwable exception;

    public String getPrintableTestParameters() {
        if (null != testParameters && testParameters.size() > 0) {
            return testParameters.toString();
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
