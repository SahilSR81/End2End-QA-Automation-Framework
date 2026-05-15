package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the Address Book page where user manages addresses.
 */
public class AddressPage extends BasePage {
    
    private By addNewAddressButton = By.cssSelector(".add-address-button");
    private By firstNameInput = By.id("Address_FirstName");
    private By lastNameInput = By.id("Address_LastName");
    private By emailInput = By.id("Address_Email");
    private By companyInput = By.id("Address_Company");
    private By countrySelect = By.id("Address_CountryId");
    private By stateProvinceSelect = By.id("Address_StateProvinceId");
    private By cityInput = By.id("Address_City");
    private By address1Input = By.id("Address_Address1");
    private By address2Input = By.id("Address_Address2");
    private By zipPostalCodeInput = By.id("Address_ZipPostalCode");
    private By phoneNumberInput = By.id("Address_PhoneNumber");
    private By faxNumberInput = By.id("Address_FaxNumber");
    private By saveButton = By.cssSelector(".save-address-button");
    
    // address-list div that contains "No addresses"
    private By addressListBlock = By.cssSelector(".address-list");
    private By validationErrors = By.cssSelector(".field-validation-error");

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(validationErrors).size() > 0;
    }

    public String getErrorMessages() {
        StringBuilder errors = new StringBuilder();
        for (var el : driver.findElements(validationErrors)) {
            errors.append(el.getText()).append(" ");
        }
        return errors.toString();
    }

    /**
     * Checks if the "No addresses" text is present.
     */
    public boolean isNoAddressesMessageDisplayed() {
        if(isElementDisplayed(addressListBlock)) {
            String text = getText(addressListBlock);
            return text.contains("No addresses");
        }
        return false;
    }

    /**
     * Clicks the add new address button.
     */
    public void clickAddNewAddress() {
        click(addNewAddressButton);
    }

    /**
     * Fills the address form with the provided details.
     */
    public void fillAddressDetails(String fName, String lName, String email, String company, String countryName, String stateName, String city, String address1, String address2, String zip, String phone, String fax) {
        if (fName != null) sendKeys(firstNameInput, fName);
        if (lName != null) sendKeys(lastNameInput, lName);
        if (email != null) sendKeys(emailInput, email);
        if (company != null) sendKeys(companyInput, company);
        
        if (countryName != null) selectByVisibleText(countrySelect, countryName);
        if (stateName != null) selectByVisibleText(stateProvinceSelect, stateName);
        
        if (city != null) sendKeys(cityInput, city);
        if (address1 != null) sendKeys(address1Input, address1);
        if (address2 != null) sendKeys(address2Input, address2);
        if (zip != null) sendKeys(zipPostalCodeInput, zip);
        if (phone != null) sendKeys(phoneNumberInput, phone);
        if (fax != null) sendKeys(faxNumberInput, fax);
    }

    /**
     * Submits the address form by clicking Save.
     */
    public void clickSaveButton() {
        click(saveButton);
    }
}
