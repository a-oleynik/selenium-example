package com.oleynik.gradle.selenium.example.framework.utils;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import com.oleynik.gradle.selenium.example.framework.manager.WebdriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class WebdriverUtils {

    public static WebDriver createNewDriver() {
        WebDriver webDriver = WebdriverFactory.createInstance();
        WebdriverManager.setWebDriver(webDriver);
        return webDriver;
    }

    public static void quitDriver() {
        if (WebdriverManager.getDriver() != null) {
            try {
                WebdriverManager.getDriver().quit();
            } catch (Exception exception) {
                System.out.println("Warning: driver.quit() command invoked exception " + exception.getClass().getName());
                exception.printStackTrace();
            } finally {
                WebdriverManager.removeDriver();
            }
        }
    }

    public static WebElement findElement(By locator) {
        return findElement(locator, ExpectedConditions::presenceOfElementLocated, configuration().defaultWebdriverTimeout());
    }

    public static WebElement findElement(By locator,
                                         final Function<By, ExpectedCondition<WebElement>> condition,
                                         Integer timeout) {
        return new FluentWait<>(WebdriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(WebDriverException.class)
                .until(condition.apply(locator));
    }

    public static boolean elementExists(By locator) {
        List<WebElement> elements = WebdriverManager.getDriver().findElements(locator);
        return !elements.isEmpty();
    }

    public static boolean elementExistsAndShown(By by) {
        return elementExists(by) && WebdriverUtils.findElement(by).isDisplayed();
    }

    public static void clickIfElementShown(By by){
        if (elementExistsAndShown(by)) {
            WebdriverUtils.findElement(by).click();
        }
    }
}
