package com.ecommerce.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/com/ecommerce/config/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("config.properties file not found at the specified path: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseURL() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }
}
