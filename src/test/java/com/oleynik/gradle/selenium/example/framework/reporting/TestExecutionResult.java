package com.oleynik.gradle.selenium.example.framework.reporting;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class TestExecutionResult implements Comparable<TestExecutionResult> {
    private final String testClass;
    private final String testMethod;
    private Object[] testParameters;
    private ZonedDateTime testStartDateTime;
    private ZonedDateTime testEndDateTime;
    private ExecutionStatus executionStatus;
    private Throwable exception;

    public void setTestMethod(Object[] testParameters) {
        this.testParameters = testParameters;
    }

    public void setTestStartDateTime(ZonedDateTime testStartDateTime) {
        this.testStartDateTime = testStartDateTime;
    }

    public void setTestEndDateTime(ZonedDateTime testEndDateTime) {
        this.testEndDateTime = testEndDateTime;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public TestExecutionResult(TestResultBuilder testResult) {
        this.testClass = testResult.testClass;
        this.testMethod = testResult.testMethod;
        this.testParameters = testResult.testParameters;
        this.testStartDateTime = testResult.testStartDateTime;
        this.testEndDateTime = testResult.testEndDateTime;
        this.executionStatus = testResult.executionStatus;
        this.exception = testResult.exception;
    }

    @Override
    public String toString() {
        return "TestExecutionResult{" +
                "testClass='" + testClass + '\'' +
                ", testMethod='" + testMethod + '\'' +
                ", testParameters=" + getPrintableTestParameters() +
                ", testStartDateTime=" + testStartDateTime +
                ", testEndDateTime=" + testEndDateTime +
                ", executionStatus=" + executionStatus +
                ", exception=" + exception +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestExecutionResult that = (TestExecutionResult) o;
        return getTestClass().equals(that.getTestClass())
                && getTestMethod().equals(that.getTestMethod())
                && Arrays.equals(getTestParameters(), that.getTestParameters())
                && Objects.equals(getTestStartDateTime(), that.getTestStartDateTime())
                && Objects.equals(getTestEndDateTime(), that.getTestEndDateTime())
                && executionStatus == that.executionStatus && Objects.equals(getException(), that.getException());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getTestClass(), getTestMethod(), getTestStartDateTime(), getTestEndDateTime(), executionStatus, getException());
        result = 31 * result + Arrays.hashCode(getTestParameters());
        return result;
    }

    public String getTestClass() {
        return testClass;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public Object[] getTestParameters() {
        return testParameters;
    }

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

    public Throwable getException() {
        return exception;
    }

    public ZonedDateTime getTestStartDateTime() {
        return testStartDateTime;
    }

    public ZonedDateTime getTestEndDateTime() {
        return testEndDateTime;
    }

    public int compareTo(@NonNull TestExecutionResult result) {
        return Comparator.comparing(TestExecutionResult::getTestClass)
                .thenComparing(TestExecutionResult::getTestMethod)
                .thenComparing(TestExecutionResult::getPrintableTestParameters)
                .thenComparing(TestExecutionResult::getTestStatus)
                .compare(this, result);
    }

    public static class TestResultBuilder {
        private final String testClass;
        private final String testMethod;
        private Object[] testParameters;
        private ZonedDateTime testStartDateTime;
        private ZonedDateTime testEndDateTime;
        private ExecutionStatus executionStatus;
        private Throwable exception;

        public TestResultBuilder(String testClass, String testMethod) {
            this.testClass = testClass;
            this.testMethod = testMethod;
        }

        public TestExecutionResult.TestResultBuilder testParameters(Object[] testParameters) {
            this.testParameters = testParameters;
            return this;
        }

        public TestExecutionResult.TestResultBuilder testStartDateTime(ZonedDateTime testStartDateTime) {
            this.testStartDateTime = testStartDateTime;
            return this;
        }

        public TestExecutionResult.TestResultBuilder testEndDateTime(ZonedDateTime testEndDateTime) {
            this.testEndDateTime = testEndDateTime;
            return this;
        }

        public TestExecutionResult.TestResultBuilder testStatus(ExecutionStatus executionStatus) {
            this.executionStatus = executionStatus;
            return this;
        }

        public TestExecutionResult.TestResultBuilder exception(Throwable exception) {
            this.exception = exception;
            return this;
        }

        public TestExecutionResult build() {
            return new TestExecutionResult(this);
        }
    }

}
