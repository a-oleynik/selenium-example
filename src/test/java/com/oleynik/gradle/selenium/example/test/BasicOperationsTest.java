package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

@Feature("Basic math operations")
public class BasicOperationsTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private final String url = configuration().environmentUrl();

    @BeforeEach()
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    public static Object[][] addNumbers() {
        return new Object[][]{
                {2, 10},
                {99, 7},
                {111, 11},
                {0, 0},
                {1, 1}
        };
    }

    @ParameterizedTest
    @MethodSource("addNumbers")
    @Description("Check addition")
    public void checkCalculatorAdditions(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.add(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkAddition(x, y);
    }

    public static Object[][] subtractNumbers() {
        return new Object[][]{
                {1, 10},
                {99, 7},
                {145, 7},
                {0, 0},
                {1, 1}
        };
    }

    @ParameterizedTest
    @MethodSource("subtractNumbers")
    @Description("Check subtraction")
    public void checkCalculatorSubtractions(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.subtract(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkSubtraction(x, y);
    }

    public static Object[][] multiplyNumbers() {
        return new Object[][]{
                {1, 10},
                {99, 7},
                {145, 7},
                {111, 11},
                {1, 1}
        };
    }

    @ParameterizedTest
    @MethodSource("multiplyNumbers")
    @Description("Check multiplication")
    public void checkCalculatorMultiplications(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.multiply(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkMultiplication(x, y);
    }

    @ParameterizedTest
    @Description("Check division")
    @CsvFileSource(resources = "/Division.csv")
    public void checkCalculatorDivision(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.divide(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkDivision(x, y);
    }
}
