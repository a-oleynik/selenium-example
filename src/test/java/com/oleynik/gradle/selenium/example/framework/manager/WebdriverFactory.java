package com.oleynik.gradle.selenium.example.framework.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class WebdriverFactory {

    public static WebDriver createInstance() {
        WebDriver driver;
        String chosenBrowser = configuration().envBrowser().toLowerCase();
        driver = switch (chosenBrowser) {
            case "firefox" -> new FirefoxDriver();
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-search-engine-choice-screen");
                yield new ChromeDriver(options);
            }
            case "ie" -> new InternetExplorerDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new IllegalStateException("Browser is not supported");
        };

        return driver;


    }
}
