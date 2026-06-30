package com.oleynik.gradle.selenium.example.framework.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class WebdriverFactory {
    private WebdriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static WebDriver createInstance() {
        WebDriver driver;
        String chosenBrowser = configuration().envBrowser().toLowerCase();
        driver = switch (chosenBrowser) {
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
                yield new FirefoxDriver();
            }
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-search-engine-choice-screen");
                yield new ChromeDriver(options);
            }
            case "edge" -> {
                System.setProperty("webdriver.edge.driver", ".\\drivers\\msedgedriver.exe");
                yield new EdgeDriver();
            }
            default -> throw new IllegalStateException("Browser is not supported");
        };

        return driver;


    }
}
