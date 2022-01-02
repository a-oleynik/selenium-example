package com.oleynik.gradle.selenium.example.framework.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:general.properties"})
public interface Configuration extends Config {
    @Config.Key("env.browser")
    String envBrowser();

    @Config.Key("env.url")
    String environmentUrl();

    @Config.Key("default.webdriver.timeout")
    int defaultWebdriverTimeout();
}
