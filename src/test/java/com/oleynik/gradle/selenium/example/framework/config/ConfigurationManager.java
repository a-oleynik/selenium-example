package com.oleynik.gradle.selenium.example.framework.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {
    private ConfigurationManager() {
    }

    public static Configuration configuration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
