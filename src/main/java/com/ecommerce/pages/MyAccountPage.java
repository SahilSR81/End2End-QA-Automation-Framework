package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MyAccountPage extends BasePage {
    private By firstNameInput = By.id("FirstName");
    private By lastNameInput = By.id("LastName");
    private By emailInput = By.id("Email");
    private By saveButton = By.cssSelector(".save-customer-info-button");
    private By changePasswordLink = By.cssSelector("a[href*='/customer/changepassword']");
    private By oldPasswordInput = By.id("OldPassword");
    private By newPasswordInput = By.id("NewPassword");
    private By confirmNewPasswordInput = By.id("ConfirmNewPassword");
    private By savePasswordButton = By.cssSelector(".change-password-button");
    private By addressesLink = By.cssSelector("a[href*='/customer/addresses']");
    private By addNewAddressButton = By.cssSelector(".add-address-button");
    private By ordersLink = By.cssSelector("a[href*='/customer/orders']");
    private By rewardPointsLink = By.cssSelector("a[href*='/customer/rewardpoints']");

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddressesLink() {
        click(addressesLink);
    }

    public void clickChangePasswordLink() {
        click(changePasswordLink);
    }

    public void editProfile(String fname, String lname) {
        sendKeys(firstNameInput, fname);
        sendKeys(lastNameInput, lname);
        click(saveButton);
    }

    public void changePassword(String oldPassword, String newPassword) {
        click(changePasswordLink);
        sendKeys(oldPasswordInput, oldPassword);
        sendKeys(newPasswordInput, newPassword);
        sendKeys(confirmNewPasswordInput, newPassword);
        click(savePasswordButton);
    }

    public void addNewAddress(Map<String, String> addressData) {
        click(addressesLink);
        if(driver.findElements(addNewAddressButton).size() > 0) {
            click(addNewAddressButton);
        }
        if(addressData.containsKey("firstName")) sendKeys(By.id("Address_FirstName"), addressData.get("firstName"));
        if(addressData.containsKey("lastName")) sendKeys(By.id("Address_LastName"), addressData.get("lastName"));
        if(addressData.containsKey("email")) sendKeys(By.id("Address_Email"), addressData.get("email"));
        if(addressData.containsKey("country")) sendKeys(By.id("Address_CountryId"), addressData.get("country"));
        if(addressData.containsKey("city")) sendKeys(By.id("Address_City"), addressData.get("city"));
        if(addressData.containsKey("address")) sendKeys(By.id("Address_Address1"), addressData.get("address"));
        if(addressData.containsKey("zipCode")) sendKeys(By.id("Address_ZipPostalCode"), addressData.get("zipCode"));
        if(addressData.containsKey("phoneNumber")) sendKeys(By.id("Address_PhoneNumber"), addressData.get("phoneNumber"));
        click(By.cssSelector(".save-address-button"));
    }

    public void editExistingAddress(String addressName, Map<String, String> newData) { }

    public void deleteAddress(String addressName) { }

    public List<String> getAllAddresses() {
        return new ArrayList<>();
    }

    public String getFirstName() {
        return driver.findElement(firstNameInput).getAttribute("value");
    }

    public String getLastName() {
        return driver.findElement(lastNameInput).getAttribute("value");
    }

    public String getEmailAddress() {
        return driver.findElement(emailInput).getAttribute("value");
    }

    public void viewOrderHistory() {
        click(ordersLink);
    }

    public void downloadProducts() { }

    public int getRewardPoints() {
        click(rewardPointsLink);
        return 0;
    }

    public void logout() {
        click(By.cssSelector(".ico-logout"));
    }

    public boolean isMyAccountPageDisplayed() {
        return isElementDisplayed(firstNameInput) || driver.getTitle().contains("My account");
    }

    public boolean isAddressPresent(String addressName) {
        return false;
    }
}
