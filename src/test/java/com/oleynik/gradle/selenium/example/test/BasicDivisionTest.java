package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.framework.dataproviders.CsvDataProvider;
import com.oleynik.gradle.selenium.example.framework.dataproviders.CsvSource;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import org.testng.annotations.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class BasicDivisionTest extends BaseTest {
    private String url = configuration().environmentUrl();

    @Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class,
            description="Check division using reusable csv data provider")
    @CsvSource(path="src/test/resources/Division.csv")
    public void checkDivisionsFromCSVByReusableDataProviderVerify(int x, int y) {
        CalculatorSteps calculatorSteps = new CalculatorSteps();
        calculatorSteps.openCalculator(url);
        calculatorSteps.divide(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkDivision(x, y);
    }
}
