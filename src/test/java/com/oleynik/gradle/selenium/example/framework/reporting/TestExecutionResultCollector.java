package com.oleynik.gradle.selenium.example.framework.reporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestExecutionResultCollector {
    private static Queue<TestExecutionResult> allResults = new ConcurrentLinkedQueue<>();

    private TestExecutionResultCollector() {
    }

    private static TestExecutionResultCollector instance = new TestExecutionResultCollector();

    public static TestExecutionResultCollector getInstance() {
        return instance;
    }

    public static void addTestExecutionResult(TestExecutionResult testExecutionResult) {
        allResults.add(testExecutionResult);
    }

    public static List<TestExecutionResult> getAllResults() {
        List<TestExecutionResult> results = new ArrayList<>(allResults);
        Collections.sort(results);
        return results;
    }
}
