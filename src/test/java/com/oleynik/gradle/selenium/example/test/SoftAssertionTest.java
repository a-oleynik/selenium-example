package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

@Feature("Soft assertions")
public class SoftAssertionTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @Test(enabled = false, description = "Test with soft assertions")
    public void checkSoftAssertions() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkSoftAsserts();
    }
}
