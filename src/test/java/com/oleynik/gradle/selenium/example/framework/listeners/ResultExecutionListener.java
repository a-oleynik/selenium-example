package com.oleynik.gradle.selenium.example.framework.listeners;

import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResultCollector;
import org.testng.IExecutionListener;

import java.util.List;
import java.util.Map;

import static com.oleynik.gradle.selenium.example.framework.config.Constants.REPORTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.convertExecutionTestResults;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.createExcelFromData;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.getDateTimeForExcelReport;

public class ResultExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionFinish() {
        List<Map<String, String>> testResults = convertExecutionTestResults(TestExecutionResultCollector.getAllResults());
        String excelReport = REPORTS_FOLDER + "testExecutionReport_" + getDateTimeForExcelReport() + ".xlsx";
        createExcelFromData(excelReport, "Report", testResults);
    }
}
