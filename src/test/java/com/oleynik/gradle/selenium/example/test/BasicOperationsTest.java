package com.oleynik.gradle.selenium.example.test;

import com.oleynik.gradle.selenium.example.framework.BaseTest;
import com.oleynik.gradle.selenium.example.steps.CalculatorSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class BasicOperationsTest extends BaseTest {
    private CalculatorSteps calculatorSteps;
    private String url = configuration().environmentUrl();

    @BeforeMethod()
    public void setUp() {
        calculatorSteps = new CalculatorSteps();
    }

    @DataProvider(name = "addition")
    public static Object[][] additionNumbers() {
        return new Object[][]{{2, 10}, {99, 7}, {111, 11}, {0, 0}, {1, 1}};
    }

    @Test(dataProvider = "addition", description = "Check addition")
    public void checkCalculatorAdditions(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.add(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkAddition(x, y);
    }

    @DataProvider(name = "subtraction")
    public static Object[][] subtractNumbers() {
        return new Object[][]{{1, 10}, {99, 7}, {145, 7}, {0, 0}, {1, 1}};
    }

    @Test(dataProvider = "subtraction", description = "Check subtraction")
    public void checkCalculatorSubtractions(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.subtract(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkSubtraction(x, y);
    }

    @DataProvider(name = "multiplication")
    public static Object[][] multiplyNumbers() {
        return new Object[][]{{1, 10}, {99, 7}, {145, 7}, {111, 11}, {1, 1}};
    }

    @Test(dataProvider = "multiplication", description = "Check multiplication")
    public void checkCalculatorMultiplications(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.multiply(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkMultiplication(x, y);
    }

    @DataProvider(name = "division")
    public static Object[][] divideNumbers() {
        String line;
        ArrayList<Object[]> outData = new ArrayList<>();
        try {
            BufferedReader csvFile = new BufferedReader(new FileReader("src/test/resources/Division.csv"));
            while ((line = csvFile.readLine()) != null) {
                String[] data = line.split(",");
                outData.add(new Object[]{Integer.parseInt(data[0].trim()), Integer.parseInt(data[1].trim())});
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return outData.toArray(new Object[outData.size()][]);
    }

    @Test(dataProvider = "division", description = "Check division")
    public void checkCalculatorDivision(int x, int y) {
        calculatorSteps.openCalculator(url);
        calculatorSteps.divide(Integer.toString(x), Integer.toString(y));
        calculatorSteps.checkDivision(x, y);
    }
}
