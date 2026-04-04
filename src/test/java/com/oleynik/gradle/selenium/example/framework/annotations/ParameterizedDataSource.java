package com.oleynik.gradle.selenium.example.framework.annotations;

import com.oleynik.gradle.selenium.example.framework.dataproviders.CSVDataProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(CSVDataProvider.class)
public @interface ParameterizedDataSource {
    String path();
}
