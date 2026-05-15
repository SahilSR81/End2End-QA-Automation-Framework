package com.ecommerce.pages;

import com.ecommerce.utils.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By emailInput = By.id("Email");
    private By passwordInput = By.id("Password");
    private By rememberMeCheckbox = By.id("RememberMe");
    private By loginButton = By.cssSelector(".login-button");
    private By forgottenPasswordLink = By.cssSelector("a[href*='passwordrecovery']");
    private By registerButton = By.cssSelector(".register-button");
    private By errorMessage = By.cssSelector(".validation-summary-errors, .message-error, .field-validation-error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillEmail(String email) {
        sendKeys(emailInput, email);
    }

    public void fillPassword(String password) {
        sendKeys(passwordInput, password);
    }

    public void clickRememberMe() {
        if (!driver.findElement(rememberMeCheckbox).isSelected()) {
            click(rememberMeCheckbox);
        }
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void clickForgottenPassword() {
        click(forgottenPasswordLink);
    }

    public void loginWithValidCredentials(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLoginButton();
        // Wait for login to complete and page to redirect
        waitForLoginCompletion();
    }

    /**
     * Waits for the login process to complete.
     * Ensures the page has redirected after successful login.
     */
    private void waitForLoginCompletion() {
        int maxRetries = 5;
        int retries = 0;
        while (retries < maxRetries) {
            try {
                // Wait a bit for the page to start redirecting
                Thread.sleep(1000);
                
                // Check if we're no longer on the login page by checking if login page elements are gone
                if (!isLoginPageDisplayed()) {
                    LoggerUtil.info("Login completed successfully");
                    return;
                }
                retries++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        LoggerUtil.warning("Login completion wait timed out, proceeding anyway");
    }

    public void loginWithInvalidCredentials(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLoginButton();
    }

    public void loginWithBlankEmail(String password) {
        fillPassword(password);
        clickLoginButton();
    }

    public void loginWithBlankPassword(String email) {
        fillEmail(email);
        clickLoginButton();
    }

    public String getErrorMessage() {
        if(isErrorMessageDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        int retries = 0;
        while (retries < 3) {
            try {
                waitForElementVisibility(errorMessage, 10);
                return driver.findElements(errorMessage).size() > 0 && driver.findElement(errorMessage).isDisplayed();
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                retries++;
                try { Thread.sleep(500); } catch (Exception ignored) {}
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(emailInput) && isElementDisplayed(loginButton);
    }
}
