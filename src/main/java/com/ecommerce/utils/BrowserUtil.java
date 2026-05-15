package com.ecommerce.utils;

import com.ecommerce.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class BrowserUtil {

    /**
     * Launches the specified browser and configures initial setup like wait timeouts
     * and window maximization. Reads headless configuration from system properties
     * or config.properties file.
     *
     * @param browserName Name of the browser to launch (chrome, firefox, edge)
     * @return WebDriver instance for the launched browser
     */
    public static WebDriver launchBrowser(String browserName) {
        WebDriver driver = null;
        try {
            LoggerUtil.info("Launching browser: " + browserName);
            
            // Determine headless mode from System properties (maven args) or config.properties
            String sysHeadless = System.getProperty("headless");
            boolean isHeadless = sysHeadless != null ? Boolean.parseBoolean(sysHeadless) 
                                                     : Boolean.parseBoolean(ConfigReader.getProperty("headless"));

            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                if(isHeadless) options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            } else if (browserName.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if(isHeadless) options.addArguments("--headless");
                driver = new FirefoxDriver(options);
            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if(isHeadless) options.addArguments("--headless=new");
                driver = new EdgeDriver(options);
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            LoggerUtil.info("Browser " + browserName + " launched successfully in " + (isHeadless ? "headless" : "headed") + " mode.");
        } catch (Exception e) {
            LoggerUtil.error("Error launching browser: " + browserName, e);
            throw e;
        }
        return driver;
    }
}
