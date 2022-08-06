package com.oleynik.gradle.selenium.example.framework.dataproviders;

import com.oleynik.gradle.selenium.example.framework.annotations.ParameterizedDataSource;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class MyDataProvider implements ArgumentsProvider, AnnotationConsumer<ParameterizedDataSource> {
    private String path;

    MyDataProvider() {
    }

    @Override
    public void accept(final ParameterizedDataSource source) {
        path = source.path();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(path))
                    .build();
            List<String[]> allData = csvReader.readAll();
            return allData.stream().map(Arguments::arguments);
        } catch (IOException | NumberFormatException | CsvException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
