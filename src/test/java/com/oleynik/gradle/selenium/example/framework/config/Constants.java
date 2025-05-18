package com.oleynik.gradle.selenium.example.framework.config;

import java.io.File;

public class Constants {
    public static final String BUILD_FOLDER = "build" + File.separator;
    public static final String REPORTS_FOLDER = BUILD_FOLDER + "reports" + File.separator;
    public static final String SCREENSHOTS_FOLDER = REPORTS_FOLDER + "screenshots" + File.separator;
    public static final String EXCEL_RESULTS_FOLDER = BUILD_FOLDER + "excel-results" + File.separator;
    public static final String TEST_RESOURCES = "src/test/resources/";
}
