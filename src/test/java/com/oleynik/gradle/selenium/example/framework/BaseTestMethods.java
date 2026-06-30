package com.oleynik.gradle.selenium.example.framework;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@Slf4j
public class BaseTestMethods {
    private static final int IMPLICIT_WAIT_SECONDS = 2;

    /**
     * Check if Webdriver is available for test execution.
     * If Webdriver is not available, new driver instance is created.
     */
    public static void instantiateDriver() {
        WebDriver driver = WebdriverManager.getDriver();
        try {
            if (driver == null) {
                log.info("Webdriver is not set. Creating new driver.");
                WebdriverUtils.createNewDriver();
                setMaximisedBrowserWindow();
                return;
            }
            driver.getWindowHandles();
            driver.getTitle();
        } catch (Exception exception) {
            log.warn("Browser is not detected. Error message: \"{}\". Restarting browser", exception.getMessage());
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
    }
}
