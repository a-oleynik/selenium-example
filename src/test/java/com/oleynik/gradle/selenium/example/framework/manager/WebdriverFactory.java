package com.oleynik.gradle.selenium.example.framework.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class WebdriverFactory {

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
                yield new ChromeDriver();
            }
            case "ie" -> {
                System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
                yield new InternetExplorerDriver();
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
