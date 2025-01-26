package com.oleynik.gradle.selenium.example.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GeneralUtils {
    public static void saveBytesAsFile(String path, byte[] fileBytes) {
        try (OutputStream out = new FileOutputStream(path)) {
            out.write(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
