package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.framework.annotations.ParameterizedDataSource;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;
import static com.oleynik.gradle.selenium.example.framework.config.Constants.TEST_RESOURCES;

@Feature("Basic math operations")
public class BasicDivisionTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeEach()
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @ParameterizedTest
    @Description("Check division using reusable csv data provider")
    @ParameterizedDataSource(path = TEST_RESOURCES + "Division.csv")
    public void checkDivisionsFromCSVByReusableDataProviderVerify(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.divide(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkDivision(x, y);
    }
}
