package com.oleynik.gradle.selenium.example.framework;

import com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils.quitDriver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUpBrowser() {
        driver = WebdriverUtils.createNewDriver();
        BaseTestMethods.setMaximisedBrowserWindow();
    }

    @BeforeMethod(alwaysRun = true)
    public void checkWebDriver() {
        BaseTestMethods.instantiateDriver();
    }

    @AfterClass(alwaysRun = true)
    public void dropDriver() {
        quitDriver();
    }
}
