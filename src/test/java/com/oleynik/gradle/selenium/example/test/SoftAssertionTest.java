package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

@Feature("Soft sssertions")
public class SoftAssertionTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeEach
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @Test
    @Description("Test with soft assertions")
    public void checkSoftAssertions() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkSoftAsserts();
    }
}
