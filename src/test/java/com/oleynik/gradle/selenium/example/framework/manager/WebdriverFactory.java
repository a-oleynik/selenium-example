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
        switch (chosenBrowser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", ".\\drivers\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalStateException("Browser is not supported");
        }

        return driver;


    }
}
