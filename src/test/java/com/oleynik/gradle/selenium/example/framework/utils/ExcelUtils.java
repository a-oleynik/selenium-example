package com.oleynik.gradle.selenium.example.framework.utils;

import com.oleynik.gradle.selenium.example.framework.reporting.TestExecutionResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.*;

import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.*;

public class ExcelUtils {
    public static List<Map<String, String>> convertExecutionTestResults(List<TestExecutionResult> results) {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> resultMap;
        for (TestExecutionResult result : results) {
            resultMap = new LinkedHashMap<>() {{
                put("TestClass", result.getTestClass());
                put("TestMethod", result.getTestMethod());
                put("Parameters", result.getPrintableTestParameters());
                put("TestStatus", result.getTestStatus().toString());
                put("TestStart", zonedDateTimeToString(result.getTestStartDateTime(), STANDARD_DATE_TIME));
                put("TestEnd", zonedDateTimeToString(result.getTestEndDateTime(), STANDARD_DATE_TIME));
                put("Duration", getPrintableDuration(result.getTestStartDateTime(), result.getTestEndDateTime()));
                put("Exception", null != result.getException() ? result.getException().getClass().getCanonicalName() : "");
                put("ExceptionMessage", null != result.getException() ? result.getException().getMessage() : "");
            }};
            data.add(resultMap);
        }
        return data;
    }

    public static void createExcelFromData(String file, String sheetName, List<Map<String, String>> data) {
        XSSFWorkbook workbook = createWorkbook(sheetName, data);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static XSSFWorkbook createWorkbook(String sheetName, List<Map<String, String>> data) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        int rowCount = 0;
        Row row;
        row = sheet.createRow(++rowCount);
        int columnCount = 0;
        for (String value : data.getFirst().keySet()) {
            Cell cell = row.createCell(++columnCount);
            cell.setCellValue(value);
            cell.setCellStyle(headerStyle);
        }
        for (Map<String, String> dataRow : data) {
            row = sheet.createRow(++rowCount);
            columnCount = 0;
            for (String value : dataRow.values()) {
                Cell cell = row.createCell(++columnCount);
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
        }

        if (sheet.getPhysicalNumberOfRows() > 0) {
            row = sheet.getRow(sheet.getFirstRowNum());
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                sheet.autoSizeColumn(columnIndex);
            }
        }
        return workbook;
    }
}
