package com.oleynik.gradle.selenium.example.framework;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTestMethods {

    /**
     * Check if Webdriver is available for test execution.
     * If Webdriver is not available, new driver instance is created.
     */
    public static void instantiateDriver() {
        WebDriver driver = WebdriverManager.getDriver();
        try {
            if (driver == null) {
                System.out.println("Webdriver is not set. Creating new driver.");
                WebdriverUtils.createNewDriver();
                setMaximisedBrowserWindow();
                return;
            }
            driver.getWindowHandles();
            driver.getTitle();
        } catch (Exception exception) {
            System.out.println("Browser is not detected. Error message: \"" + exception.getMessage() + "\". Restarting browser");
            WebdriverUtils.createNewDriver();
            setMaximisedBrowserWindow();
        }
    }

    /**
     * Set browser window maximised.
     * If Webdriver is not available, new driver instance is created.
     */
    public static void setMaximisedBrowserWindow() {
        WebDriver driver = WebdriverManager.getDriver();
        //Maximize the browser window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }
}
