package com.oleynik.gradle.selenium.example.framework.listeners;

import com.google.common.collect.ImmutableMap;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.List;
import java.util.Map;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.config.Constants.REPORTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.reporting.AllureEnvironmentWriter.writeAllureEnvironment;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.convertExecutionTestResults;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.createExcelFromData;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForExcelReport;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;

public class ResultExecutionListener implements TestExecutionListener {
    public void testPlanExecutionStarted(TestPlan testPlan) {
        writeAllureEnvironment(
                ImmutableMap.<String, String>builder()
                        .put("Browser", configuration().envBrowser())
                        .put("URL", configuration().environmentUrl())
                        .build());
    }

    public void testPlanExecutionFinished(TestPlan testPlan) {
        List<Map<String, String>> testResults = convertExecutionTestResults(TestExecutionResultCollector.getAllResults());
        String excelReport = REPORTS_FOLDER + "testExecutionReport_" + getDateTimeForExcelReport() + ".xlsx";
        createDirectoryIfNotExist(REPORTS_FOLDER);
        createExcelFromData(excelReport, "Report", testResults);
    }

    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
    }

    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
    }

    public void executionStarted(TestIdentifier testIdentifier) {
    }

    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
    }

    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
    }
}
