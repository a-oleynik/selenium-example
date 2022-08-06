package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

@Feature("Calculator sanity")
public class CalculatorSanityTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeEach
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @Test
    @Description("Check calculator open")
    public void checkCalculatorOpening() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }

    @Test
    @Description("Check calculator open 2")
    public void checkCalculatorOpening2() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }
}
