package com.oleynik.gradle.selenium.example.framework.utils;

import com.oleynik.gradle.selenium.example.framework.reporting.ExecutionStatus;

public class TestNGUtils {
    public static ExecutionStatus getExecutionStatus(int testStatus) {
        switch (testStatus) {
            case 1:
                return ExecutionStatus.PASS;
            case 2:
                return ExecutionStatus.FAIL;
            case 3:
                return ExecutionStatus.SKIP;
            default:
                return ExecutionStatus.UNKNOWN;
        }
    }
}
