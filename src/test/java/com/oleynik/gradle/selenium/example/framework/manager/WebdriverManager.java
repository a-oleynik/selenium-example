package com.oleynik.gradle.selenium.example.framework.manager;

import org.openqa.selenium.WebDriver;

public class WebdriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
}
