package com.oleynik.gradle.selenium.example.framework.reporting;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ThrowableSerializer extends JsonSerializer<Throwable> {
    @Override
    public void serialize(Throwable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getMessage());
    }
}
