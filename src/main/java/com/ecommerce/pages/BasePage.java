package com.ecommerce.pages;

import com.ecommerce.utils.LoggerUtil;
import com.ecommerce.utils.ScreenshotUtil;
import com.ecommerce.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Initializes the driver and WebDriverWait.
     *
     * @param driver WebDriver instance to interact with the browser.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Waits for an element to be clickable and performs a click.
     *
     * @param locator By locator of the element to click.
     */
    public void click(By locator) {
        int retries = 0;
        while (retries < 3) {
            try {
                WaitUtil.waitForElementClickability(driver, locator, 15);
                WebElement element = driver.findElement(locator);
                try {
                    element.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                    try { Thread.sleep(500); } catch (Exception ignored) {}
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }
                LoggerUtil.info("Clicked element: " + locator);
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException se) {
                LoggerUtil.warning("Stale element when clicking, retrying: " + locator);
                retries++;
                try { Thread.sleep(500); } catch (Exception ignored) {}
            } catch (Exception e) {
                LoggerUtil.error("Failed to click element: " + locator, e);
                throw e;
            }
        }
        throw new org.openqa.selenium.StaleElementReferenceException("Element is still stale after 3 retries: " + locator);
    }

    /**
     * Clears existing text and sends new text to an element.
     *
     * @param locator By locator of the target element.
     * @param text The string to be typed into the element.
     */
    public void sendKeys(By locator, String text) {
        int retries = 0;
        while (retries < 3) {
            try {
                WaitUtil.waitForElementVisibility(driver, locator, 15);
                WebElement element = driver.findElement(locator);
                try {
                    WaitUtil.waitForElementClickability(driver, locator, 5);
                } catch(Exception ignored) {}
                try {
                    element.clear();
                } catch (org.openqa.selenium.InvalidElementStateException e) {
                    LoggerUtil.warning("Element could not be cleared via standard clear(). Trying JS.");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", element);
                }
                element.sendKeys(text);
                LoggerUtil.info("Entered text '" + text + "' into element: " + locator);
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException se) {
                LoggerUtil.warning("Stale element when clearing/sending keys, retrying: " + locator);
                retries++;
                try { Thread.sleep(500); } catch (Exception ignored) {}
            } catch (Exception e) {
                LoggerUtil.error("Failed to enter text into element: " + locator, e);
                throw e;
            }
        }
        throw new org.openqa.selenium.StaleElementReferenceException("Element is still stale after 3 retries: " + locator);
    }

    /**
     * Retrieves visible text from an element.
     *
     * @param locator By locator of the element.
     * @return String The text retrieved from the element.
     */
    public String getText(By locator) {
        try {
            WaitUtil.waitForElementVisibility(driver, locator, 15);
            String text = driver.findElement(locator).getText();
            LoggerUtil.info("Retrieved text '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            LoggerUtil.error("Failed to retrieve text from element: " + locator, e);
            throw e;
        }
    }

    /**
     * Checks if an element is currently displayed on the page.
     *
     * @param locator By locator of the element to check.
     * @return boolean True if element is displayed, otherwise false.
     */
    public boolean isElementDisplayed(By locator) {
        try {
            boolean isDisplayed = driver.findElement(locator).isDisplayed();
            LoggerUtil.info("Element " + locator + " is displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            LoggerUtil.info("Element " + locator + " is not displayed or not present.");
            return false;
        }
    }

    public void waitForElementVisibility(By locator, int timeout) {
        WaitUtil.waitForElementVisibility(driver, locator, timeout);
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            LoggerUtil.info("Scrolled to element: " + locator);
        } catch (Exception e) {
            LoggerUtil.error("Failed to scroll to element: " + locator, e);
            throw e;
        }
    }

    /**
     * Selects an option from a dropdown using visible text.
     */
    public void selectByVisibleText(By locator, String text) {
        try {
            WaitUtil.waitForElementVisibility(driver, locator, 15);
            WebElement element = driver.findElement(locator);
            org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
            select.selectByVisibleText(text);
            LoggerUtil.info("Selected text '" + text + "' from dropdown: " + locator);
        } catch (Exception e) {
            LoggerUtil.error("Failed to select text '" + text + "' from dropdown: " + locator, e);
            throw e;
        }
    }

    public void takeScreenshot(String testName) {
        ScreenshotUtil.takeScreenshot(driver, testName);
    }
}
