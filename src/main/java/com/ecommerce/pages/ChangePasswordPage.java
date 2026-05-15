package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends BasePage {

    private By oldPasswordInput = By.id("OldPassword");
    private By newPasswordInput = By.id("NewPassword");
    private By confirmNewPasswordInput = By.id("ConfirmNewPassword");
    private By changePasswordBtn = By.cssSelector(".change-password-button");
    private By resultMessage = By.cssSelector(".result");
    private By validationErrors = By.cssSelector(".field-validation-error, .message-error");

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    public void changePassword(String oldPass, String newPass, String confirmPass) {
        sendKeys(oldPasswordInput, oldPass);
        sendKeys(newPasswordInput, newPass);
        sendKeys(confirmNewPasswordInput, confirmPass);
        click(changePasswordBtn);
    }

    public String getResultMessage() {
        if(isElementDisplayed(resultMessage)) {
            return getText(resultMessage);
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(validationErrors).size() > 0 && driver.findElement(validationErrors).isDisplayed();
    }

    public String getValidationErrors() {
        StringBuilder errors = new StringBuilder();
        for (var el : driver.findElements(validationErrors)) {
            errors.append(el.getText()).append(" ");
        }
        return errors.toString();
    }
}
