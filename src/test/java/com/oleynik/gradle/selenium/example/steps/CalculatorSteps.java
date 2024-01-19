package com.oleynik.gradle.selenium.example.steps;

import com.oleynik.gradle.selenium.example.pages.CalculatorPage;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorSteps {

    private CalculatorPage calculatorPage;

    @Step("Opening \"{url}\"")
    public void openCalculator(String url) {
        calculatorPage = new CalculatorPage(url);
        calculatorPage.pressConsent();
        calculatorPage.acceptCookie();
    }

    @Step("Getting page title")
    public String getCalculatorTitle() {
        return calculatorPage.getTitle();
    }

    @Step("Check if plus button exists")
    public boolean plusButtonExists() {
        return calculatorPage.plusButtonShown();
    }

    @Step("Check if minus button exists")
    public boolean minusButtonExists() {
        return calculatorPage.minusButtonShown();
    }

    @Step("Check if divide button exists")
    public boolean divideButtonExists() {
        return calculatorPage.divideButtonShown();
    }

    @Step("Check if equal button exists")
    public boolean equalButtonExists() {
        return calculatorPage.equalButtonShown();
    }

    @Step("Check if multiply button exists")
    public boolean multiplyButtonExists() {
        return calculatorPage.multiplyButtonShown();
    }

    @Step("Check if result input button exists")
    public boolean resultInputExists() {
        return calculatorPage.resultInputShown();
    }

    @Step("Adding \"{summand1}\" to \"{summand2}\"")
    public void add(String summand1, String summand2) {
        for (char digit : summand1.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressPlus();
        for (char digit : summand2.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressEqual();
    }

    @Step("Checking result of addition \"{x}\" and \"{y}\"")
    public void checkAddition(int x, int y) {
        assertEquals(getCalculationResult(), Integer.toString(x + y), "Addition result is wrong");
    }

    @Step("Subtracting \"{subtrahend}\" from \"{minuend}\"")
    public void subtract(String minuend, String subtrahend) {
        for (char digit : minuend.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressMinus();
        for (char digit : subtrahend.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressEqual();
    }

    @Step("Checking result of subtraction \"{y}\" from \"{x}\"")
    public void checkSubtraction(int x, int y) {
        assertEquals(getCalculationResult(), Integer.toString(x - y), "Subtraction result is wrong");
    }

    @Step("Multiplying \"{multiplicand}\" and \"{multiplier}\"")
    public void multiply(String multiplicand, String multiplier) {
        for (char digit : multiplicand.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressMultiply();
        for (char digit : multiplier.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressEqual();
    }

    @Step("Checking result of multiplication \"{y}\" and \"{x}\"")
    public void checkMultiplication(int x, int y) {
        assertEquals(getCalculationResult(), Integer.toString(x * y), "Multiplication result is wrong");
    }

    @Step("Dividing \"{dividend}\" on \"{divisor}\"")
    public void divide(String dividend, String divisor) {
        for (char digit : dividend.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressDivide();
        for (char digit : divisor.toCharArray()) {
            calculatorPage.pressNumber(digit);
        }
        calculatorPage.pressEqual();
    }

    @Step("Checking division \"{x}\" on \"{y}\"")
    public void checkDivision(int x, int y) {
        assertEquals(getCalculationResult(), Integer.toString(x / y), "Division result is wrong");
    }

    @Step("Getting the operation result")
    public String getCalculationResult() {
        return calculatorPage.getResultValue().trim();
    }

    @Step("Checking title and operation buttons")
    public void checkCalculatorUI() {
        assertTrue(getCalculatorTitle().contains("calculator.com"), "The title is incorrect");
        assertTrue(plusButtonExists(), "Plus button is missing");
        assertTrue(minusButtonExists(), "Minus button is missing");
        assertTrue(multiplyButtonExists(), "Multiply button is missing");
        assertTrue(divideButtonExists(), "Divide button is missing");
        assertTrue(equalButtonExists(), "Equal button is missing");
        assertTrue(resultInputExists(), "ResultInput is missing");
    }

    @Step("Checking multiple actions")
    public void checkSoftAsserts() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(2).as("The first check failed").isEqualTo(3);
        softly.assertThat(1).as("The second check failed").isEqualTo(3);
        softly.assertThat(0).as("The third check failed").isEqualTo(3);
        softly.assertThat(3).as("The fourth check failed").isEqualTo(3);
        softly.assertAll();
    }
}
