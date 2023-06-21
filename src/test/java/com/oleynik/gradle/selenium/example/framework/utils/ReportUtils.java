package com.oleynik.gradle.selenium.example.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.oleynik.gradle.selenium.example.framework.config.Constants.EXCEL_RESULTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.config.Constants.REPORTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForExcelReport;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForTestResultFile;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.convertExecutionTestResults;
import static com.oleynik.gradle.selenium.example.framework.utils.ExcelUtils.createExcelFromData;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;
import static com.oleynik.gradle.selenium.example.framework.utils.JsonUtils.getObjectMapper;

public class ReportUtils {
    public static void saveTestExecutionResult(TestExecutionResult result) {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            createDirectoryIfNotExist(EXCEL_RESULTS_FOLDER);
            objectMapper.writeValue(new File(EXCEL_RESULTS_FOLDER + "testResult_" + getDateTimeForTestResultFile() + ".json"), result);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static List<TestExecutionResult> readTestExecutionResults() {
        try (Stream<Path> paths = Files.walk(Paths.get(EXCEL_RESULTS_FOLDER))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(i -> readTestExecutionResult(i.toString()))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static TestExecutionResult readTestExecutionResult(String filePath) {
        try {
            String fileContent = FileUtils.readFileToString(new File(filePath),
                    StandardCharsets.UTF_8);
            return getObjectMapper().readValue(fileContent, TestExecutionResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTestExecutionResults(List<TestExecutionResult> results) {
        results.forEach(ReportUtils::saveTestExecutionResult);
    }

    public static void generateConsolidatedExcelReport(String testReportName) {
        List<Map<String, String>> testResults = convertExecutionTestResults(readTestExecutionResults());
        String excelReport = REPORTS_FOLDER + testReportName + "_" + getDateTimeForExcelReport() + ".xlsx";
        createDirectoryIfNotExist(REPORTS_FOLDER);
        createExcelFromData(excelReport, "Report", testResults);
    }

    public static void main(String[] args) {
        generateConsolidatedExcelReport("consolidatedExecutionReport");
    }
}
