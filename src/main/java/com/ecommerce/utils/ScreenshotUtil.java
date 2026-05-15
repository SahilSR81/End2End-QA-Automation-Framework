package com.ecommerce.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    
    /**
     * Captures a screenshot of the current browser window and formats the
     * file name with the test name and a timestamp.
     *
     * @param driver The WebDriver instance.
     * @param testName The name of the test to be included in the file name.
     * @return String The absolute or relative path to the saved screenshot file, or null on failure.
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            LoggerUtil.error("Driver is null. Cannot take screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String directoryPath = "target/screenshots";
        String screenshotPath = directoryPath + "/" + testName + "_" + timestamp + ".png";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                LoggerUtil.error("Failed to create screenshot directory.");
            }
        }

        try {
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath);
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            LoggerUtil.info("Screenshot taken: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            LoggerUtil.error("Exception while taking screenshot", e);
            return null;
        }
    }
}
