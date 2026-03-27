package com.oleynik.gradle.selenium.example.framework.listeners;

import com.google.common.collect.ImmutableMap;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.jspecify.annotations.NonNull;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;


import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.reporting.AllureEnvironmentWriter.writeAllureEnvironment;
import static com.oleynik.gradle.selenium.example.framework.utils.ReportUtils.generateConsolidatedExcelReport;
import static com.oleynik.gradle.selenium.example.framework.utils.ReportUtils.saveTestExecutionResults;

public class ResultExecutionListener implements TestExecutionListener {
    @Override
    public void testPlanExecutionStarted(@NonNull TestPlan testPlan) {
        writeAllureEnvironment(
                ImmutableMap.<String, String>builder()
                        .put("Browser", configuration().envBrowser())
                        .put("URL", configuration().environmentUrl())
                        .build());
    }

    @Override
    public void testPlanExecutionFinished(@NonNull TestPlan testPlan) {
        saveTestExecutionResults(TestExecutionResultCollector.getAllResults());
        generateConsolidatedExcelReport("executionReport");
    }
}
