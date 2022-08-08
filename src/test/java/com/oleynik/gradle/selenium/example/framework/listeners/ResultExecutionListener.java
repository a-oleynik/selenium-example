package com.oleynik.gradle.selenium.example.framework.listeners;

import com.google.common.collect.ImmutableMap;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.testng.IExecutionListener;

import java.util.List;
import java.util.Map;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.config.Constants.REPORTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.reporting.AllureEnvironmentWriter.writeAllureEnvironment;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.convertExecutionTestResults;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.createExcelFromData;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForExcelReport;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;

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
        List<Map<String, String>> testResults = convertExecutionTestResults(TestExecutionResultCollector.getAllResults());
        String excelReport = REPORTS_FOLDER + "testExecutionReport_" + getDateTimeForExcelReport() + ".xlsx";
        createDirectoryIfNotExist(REPORTS_FOLDER);
        createExcelFromData(excelReport, "Report", testResults);
    }
}
