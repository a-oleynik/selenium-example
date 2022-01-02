package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import org.testng.annotations.Test;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class CalculatorSanityTest extends BaseTest {
    String url = configuration().environmentUrl();

    @Test(description = "Check calculator open")
    public void checkCalculatorOpening() {
        CalculatorSteps calculatorSteps = new CalculatorSteps();
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }

    @Test(description = "Check calculator open 2")
    public void checkCalculatorOpening2() {
        CalculatorSteps calculatorSteps = new CalculatorSteps();
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkCalculatorUI();
    }
}
