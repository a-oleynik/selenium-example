package com.oleynik.gradle.selenium.example.framework.utils;

import com.oleynik.gradle.selenium.example.framework.reporting.ExecutionStatus;
import org.testng.ITestResult;

public class TestNGUtils {
    private TestNGUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static ExecutionStatus getExecutionStatus(int testStatus) {
        return switch (testStatus) {
            case ITestResult.SUCCESS -> ExecutionStatus.PASS;
            case ITestResult.FAILURE -> ExecutionStatus.FAIL;
            case ITestResult.SKIP    -> ExecutionStatus.SKIP;
            default -> ExecutionStatus.UNKNOWN;
        };
    }
}
