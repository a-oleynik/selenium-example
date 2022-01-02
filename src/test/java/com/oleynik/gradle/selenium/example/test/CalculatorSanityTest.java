package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class CalculatorSanityTest extends BaseTest {
    CalculatorSteps calculatorSteps;
    String url = configuration().environmentUrl();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @Test(description = "Check calculator open")
    public void checkCalculatorOpening() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }

    @Test(description = "Check calculator open 2")
    public void checkCalculatorOpening2() {
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }
}
