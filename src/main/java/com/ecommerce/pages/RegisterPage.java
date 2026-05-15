package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Map;

public class RegisterPage extends BasePage {
    private By genderMale = By.id("gender-male");
    private By genderFemale = By.id("gender-female");
    private By firstName = By.id("FirstName");
    private By lastName = By.id("LastName");
    private By email = By.id("Email");
    private By password = By.id("Password");
    private By confirmPassword = By.id("ConfirmPassword");
    private By registerButton = By.id("register-button");
    private By continueButton = By.cssSelector(".register-continue-button");
    private By errorMessage = By.cssSelector(".field-validation-error, .message-error, .validation-summary-errors");

    // Removed missing fields like company, address, newsletter etc.
    
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void fillFirstName(String name) { sendKeys(firstName, name); }
    public void fillLastName(String name) { sendKeys(lastName, name); }
    public void fillEmail(String emailStr) { sendKeys(email, emailStr); }
    public void fillPassword(String pass) { sendKeys(password, pass); }
    public void fillConfirmPassword(String pass) { sendKeys(confirmPassword, pass); }

    public void selectGender(String genderStr) {
        if (genderStr != null && genderStr.equalsIgnoreCase("Female")) {
            click(genderFemale);
        } else {
            click(genderMale);
        }
    }

    public void clickRegisterButton() { click(registerButton); }

    public void clickContinueButton() {
        if(isElementDisplayed(continueButton)) {
            click(continueButton);
        }
    }

    public void fillMinimumRequiredFields(String fname, String lname, String emailStr, String pass) {
        fillFirstName(fname);
        fillLastName(lname);
        fillEmail(emailStr);
        fillPassword(pass);
        fillConfirmPassword(pass);
    }

    public void fillAllFields(String fname, String lname, String emailStr, String pass, String genderStr) {
        fillMinimumRequiredFields(fname, lname, emailStr, pass);
        selectGender(genderStr);
    }

    public void registerWithValidData(Map<String, String> data) {
        fillAllFields(
            data.get("firstName"), data.get("lastName"), data.get("email"), 
            data.get("password"), data.get("gender")
        );
        clickRegisterButton();
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(errorMessage).size() > 0 && driver.findElement(errorMessage).isDisplayed();
    }

    public void registerWithInvalidData_BlankFields() {
        clickRegisterButton();
    }

    public void registerWithInvalidData_PasswordMismatch() {
        fillMinimumRequiredFields("Test", "User", "test@test.com", "Password123");
        fillConfirmPassword("Password456");
        clickRegisterButton();
    }

    public void registerWithInvalidData_WeakPassword() {
        fillMinimumRequiredFields("Test", "User", "test@test.com", "123");
        clickRegisterButton();
    }
    
    public boolean isRegisterPageDisplayed() {
        return isElementDisplayed(firstName);
    }
}
