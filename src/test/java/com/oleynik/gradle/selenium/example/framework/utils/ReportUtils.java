package com.oleynik.gradle.selenium.example.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;

import java.io.File;
import java.io.IOException;

import static com.oleynik.gradle.selenium.example.framework.config.Constants.EXCEL_RESULTS_FOLDER;
import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForTestResultFile;
import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;

public class ReportUtils {
    public static void saveTestExecutionResult(TestExecutionResult result){
            try {
                ObjectMapper objectMapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule());
                createDirectoryIfNotExist(EXCEL_RESULTS_FOLDER);
                objectMapper.writeValue(new File(EXCEL_RESULTS_FOLDER + "testResult_" + getDateTimeForTestResultFile() + ".json"), result);
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
    }
}
