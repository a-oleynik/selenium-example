package com.oleynik.gradle.selenium.example.framework.reporting;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class ThrowableJacksonModule extends SimpleModule {
    public ThrowableJacksonModule() {
        addSerializer(Throwable.class, new ThrowableSerializer());
    }
}
