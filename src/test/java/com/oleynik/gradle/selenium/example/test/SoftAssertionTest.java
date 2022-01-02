package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class SoftAssertionTest extends BaseTest {
    private String url = configuration().environmentUrl();

    @Test(enabled = false, description = "Test with soft assertions")
    public void checkSoftAssertions() {
        SoftAssert softAssert = new SoftAssert();
        CalculatorSteps calculatorSteps = new CalculatorSteps();
        calculatorSteps.openCalculator(url);
        calculatorSteps.checkSoftAsserts(softAssert);
        softAssert.assertAll();
    }
}
