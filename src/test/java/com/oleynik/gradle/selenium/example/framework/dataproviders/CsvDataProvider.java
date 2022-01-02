package com.oleynik.gradle.selenium.example.framework.dataproviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CsvDataProvider {
    @DataProvider
    public static Object[][] csvIntegerDataProvider(Method m) {
        try {
            String dataSource = getCsvSourcePathFromMethod(m);
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(dataSource))
                    .build();
            List<String[]> allData = csvReader.readAll();
            return allData
                    .stream()
                    .map(e -> Arrays.stream(e)
                            .map(num -> (Object) Integer.parseInt(num))
                            .toArray())
                    .toArray(Object[][]::new);
        } catch (IOException | NumberFormatException | CsvException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String getCsvSourcePathFromMethod(Method method) {
        if (method.isAnnotationPresent(CsvSource.class)) {
            CsvSource annotation = method.getAnnotation(CsvSource.class);
            return annotation.path();
        } else {
            throw new RuntimeException("@CsvSource annotation is not found");
        }
    }
}
