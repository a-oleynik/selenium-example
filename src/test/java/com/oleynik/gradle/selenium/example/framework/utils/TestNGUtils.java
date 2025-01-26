package com.oleynik.gradle.selenium.example.framework.utils;

import com.oleynik.gradle.selenium.example.framework.reporting.ExecutionStatus;

public class TestNGUtils {
    public static ExecutionStatus getExecutionStatus(int testStatus) {
        return switch (testStatus) {
            case 1 -> ExecutionStatus.PASS;
            case 2 -> ExecutionStatus.FAIL;
            case 3 -> ExecutionStatus.SKIP;
            default -> ExecutionStatus.UNKNOWN;
        };
    }
}
