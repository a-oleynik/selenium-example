package com.oleynik.gradle.selenium.example.framework.listeners;

import com.google.common.collect.ImmutableMap;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.testng.IExecutionListener;


import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.reporting.AllureEnvironmentWriter.writeAllureEnvironment;
import static com.oleynik.gradle.selenium.example.framework.utils.ReportUtils.generateConsolidatedExcelReport;
import static com.oleynik.gradle.selenium.example.framework.utils.ReportUtils.saveTestExecutionResults;

public class ResultExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        writeAllureEnvironment(
                ImmutableMap.<String, String>builder()
                        .put("Browser", configuration().envBrowser())
                        .put("URL", configuration().environmentUrl())
                        .build());
    }

    @Override
    public void onExecutionFinish() {
        saveTestExecutionResults(TestExecutionResultCollector.getAllResults());
        generateConsolidatedExcelReport("executionReport");
    }
}
