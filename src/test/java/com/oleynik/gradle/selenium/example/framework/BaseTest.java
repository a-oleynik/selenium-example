package com.oleynik.gradle.selenium.example.framework;

import com.oleynik.gradle.selenium.example.framework.listeners.MyTestWatcher;
import com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils.quitDriver;

@ExtendWith({MyTestWatcher.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    @BeforeAll
    public void setUpBrowser() {
        WebdriverUtils.createNewDriver();
        BaseTestMethods.setMaximisedBrowserWindow();
    }

    @BeforeEach
    public void checkWebDriver() {
        BaseTestMethods.setMaximisedBrowserWindow();
        BaseTestMethods.instantiateDriver();
    }

    @AfterAll
    public void dropDriver() {
        quitDriver();
    }
}
