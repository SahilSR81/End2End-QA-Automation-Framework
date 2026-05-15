package com.ecommerce.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {
    
    /**
     * Waits until the specified element is visible on the web page.
     *
     * @param driver The WebDriver instance.
     * @param locator The By locator to find the element.
     * @param timeoutInSeconds Maximum time to wait in seconds.
     */
    public static void waitForElementVisibility(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            LoggerUtil.error("Timeout waiting for element visibility: " + locator, e);
            throw new TimeoutException("Element not visible after " + timeoutInSeconds + " seconds: " + locator);
        }
    }

    /**
     * Waits until the specified element is clickable on the web page.
     *
     * @param driver The WebDriver instance.
     * @param locator The By locator to find the element.
     * @param timeoutInSeconds Maximum time to wait in seconds.
     */
    public static void waitForElementClickability(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            LoggerUtil.error("Timeout waiting for element clickability: " + locator, e);
            throw new TimeoutException("Element not clickable after " + timeoutInSeconds + " seconds: " + locator);
        }
    }

    /**
     * Waits until the specified element is present in the DOM of the web page.
     *
     * @param driver The WebDriver instance.
     * @param locator The By locator to find the element.
     * @param timeoutInSeconds Maximum time to wait in seconds.
     */
    public static void waitForElementPresence(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            LoggerUtil.error("Timeout waiting for element presence: " + locator, e);
            throw new TimeoutException("Element not present after " + timeoutInSeconds + " seconds: " + locator);
        }
    }
}
