package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.framework.dataproviders.CsvDataProvider;
import com.oleynik.gradle.selenium.example.framework.dataproviders.CsvSource;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.config.Constants.TEST_RESOURCES;

@Feature("Basic math operations")
public class BasicDivisionTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeMethod()
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @Test(dataProvider = "csvIntegerDataProvider", dataProviderClass = CsvDataProvider.class,
            description = "Check division using reusable csv data provider")
    @CsvSource(path = TEST_RESOURCES + "Division.csv")
    public void checkDivisionsFromCSVByReusableDataProviderVerify(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.divide(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkDivision(x, y);
    }
}
