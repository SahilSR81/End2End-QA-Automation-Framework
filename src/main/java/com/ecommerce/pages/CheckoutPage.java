package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.ecommerce.utils.LoggerUtil;

/**
 * Page object for the Checkout Process representing Billing Address, Shipping, and transitions.
 */
public class CheckoutPage extends BasePage {
    // Billing Address Section
    private By billingNewAddressSelect = By.id("billing-address-select");
    private By billingFirstName = By.id("BillingNewAddress_FirstName");
    private By billingLastName = By.id("BillingNewAddress_LastName");
    private By billingEmail = By.id("BillingNewAddress_Email");
    private By billingCompany = By.id("BillingNewAddress_Company");
    private By billingCountryId = By.id("BillingNewAddress_CountryId");
    private By billingStateProvinceId = By.id("BillingNewAddress_StateProvinceId");
    private By billingCity = By.id("BillingNewAddress_City");
    private By billingAddress1 = By.id("BillingNewAddress_Address1");
    private By billingAddress2 = By.id("BillingNewAddress_Address2");
    private By billingZipPostalCode = By.id("BillingNewAddress_ZipPostalCode");
    private By billingPhoneNumber = By.id("BillingNewAddress_PhoneNumber");
    private By billingFaxNumber = By.id("BillingNewAddress_FaxNumber");
    private By billingNextButton = By.cssSelector("#billing-buttons-container .new-address-next-step-button");

    // Shipping Address Section
    // private By shippingAddressSelect = By.id("shipping-address-select");
    private By shippingNextButton = By.cssSelector("#shipping-buttons-container .new-address-next-step-button");
    
    // Shipping Method Section
    private By shippingMethodNextButton = By.cssSelector("#shipping-method-buttons-container .shipping-method-next-step-button");

    // Payment Method Section
    private By paymentMethodNextButton = By.cssSelector("#payment-method-buttons-container .payment-method-next-step-button");

    // Payment Info Section
    private By paymentInfoNextButton = By.cssSelector("#payment-info-buttons-container .payment-info-next-step-button");

    // Confirm Order Section
    private By confirmOrderNextButton = By.cssSelector("button.confirm-order-next-step-button, input.confirm-order-next-step-button, .button-1.confirm-order-next-step-button");

    // Payment Info
    private By ccType = By.id("CreditCardType");
    private By ccName = By.id("CardholderName");
    private By ccNumber = By.id("CardNumber");
    private By ccCode = By.id("CardCode");
    private By poNumber = By.id("PurchaseOrderNumber");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills the billing address form and proceeds.
     */
    public void fillBillingAddress(String fname, String lname, String email, String company, String country, String state, String city, String address1, String address2, String zip, String phone, String fax) {
        try {
            if (isElementDisplayed(billingNewAddressSelect)) {
                selectByVisibleText(billingNewAddressSelect, "New Address");
            }
        } catch(Exception e) { }
        
        if (isElementDisplayed(billingFirstName)) {
            if (fname != null) { sendKeys(billingFirstName, fname); }
            if (lname != null) { sendKeys(billingLastName, lname); }
            if (email != null) { sendKeys(billingEmail, email); }
            if (company != null) sendKeys(billingCompany, company);
            if (country != null) selectByVisibleText(billingCountryId, country);
            if (state != null) selectByVisibleText(billingStateProvinceId, state);
            if (city != null) sendKeys(billingCity, city);
            if (address1 != null) sendKeys(billingAddress1, address1);
            if (address2 != null) sendKeys(billingAddress2, address2);
            if (zip != null) sendKeys(billingZipPostalCode, zip);
            if (phone != null) sendKeys(billingPhoneNumber, phone);
            if (fax != null) sendKeys(billingFaxNumber, fax);
        }
        click(billingNextButton);
    }

    /**
     * Selects a billing address from the drop-down and proceeds to next step.
     */
    public void selectExistingBillingAddressAndContinue(String addressPreview) {
        if (isElementDisplayed(billingNewAddressSelect)) {
            selectByVisibleText(billingNewAddressSelect, addressPreview);
        }
        clickBillingContinue();
    }

    public void clickBillingContinue() {
        click(billingNextButton);
    }

    public void clickShippingContinue() {
        try {
            waitForElementVisibility(By.id("shipping-address-select"), 10);
            waitForElementVisibility(shippingNextButton, 10);
            click(shippingNextButton);
        } catch(Exception e) {
            LoggerUtil.info("Shipping address step skipped or automatically approved.");
        }
    }

    public void selectShippingMethodAndContinue(String method) {
        try {
            waitForElementVisibility(By.cssSelector(".method-list"), 10);
            if(method.contains("Ground")) {
                click(By.id("shippingoption_0"));
            } else if (method.contains("Next Day")) {
                click(By.id("shippingoption_1"));
            } else if (method.contains("2nd Day")) {
                click(By.id("shippingoption_2"));
            }
        } catch (Exception e) {
            LoggerUtil.warning("Shipping method selection skipped or not visible.");
        }
        clickShippingMethodContinue();
    }

    public void clickShippingMethodContinue() {
        try {
            waitForElementVisibility(shippingMethodNextButton, 10);
            click(shippingMethodNextButton);
        } catch(Exception e) {
            LoggerUtil.warning("Shipping method Next button issue.");
        }
    }

    public void selectPaymentMethod(String method) {
        try {
            waitForElementVisibility(By.cssSelector(".method-list"), 10);
            if(method.contains("CashOnDelivery")) {
                click(By.id("paymentmethod_0"));
            } else if (method.contains("Check / Money Order")) {
                click(By.id("paymentmethod_1"));
            } else if (method.contains("Credit Card")) {
                click(By.id("paymentmethod_2"));
            } else if (method.contains("Purchase Order")) {
                click(By.id("paymentmethod_3"));
            }
        } catch (Exception e) {
            LoggerUtil.warning("Payment method selection skipped or not visible.");
        }
    }

    public void clickPaymentMethodContinue() {
        try {
            waitForElementVisibility(paymentMethodNextButton, 10);
            click(paymentMethodNextButton);
        } catch(Exception e) {
            LoggerUtil.warning("Payment method Next button issue.");
        }
    }

    private By expireMonth = By.id("ExpireMonth");
    private By expireYear = By.id("ExpireYear");

    public void fillPaymentInfoCreditCard(String type, String name, String number, String cvv) {
        try {
            waitForElementVisibility(ccName, 10);
            selectByVisibleText(ccType, type);
            sendKeys(ccName, name);
            sendKeys(ccNumber, number);
            try {
                selectByVisibleText(expireMonth, "12");
                selectByVisibleText(expireYear, "2028");
            } catch (Exception ignores) {}
            sendKeys(ccCode, cvv);
        } catch(Exception e) {}
    }

    public void fillPaymentInfoPO(String po) {
        try {
            waitForElementVisibility(poNumber, 10);
            sendKeys(poNumber, po);
        } catch(Exception e) {}
    }

    public void clickPaymentInfoContinue() {
        try {
            waitForElementVisibility(paymentInfoNextButton, 10);
            click(paymentInfoNextButton);
        } catch(Exception e) {
            LoggerUtil.warning("Payment info Next button issue.");
        }
    }

    public void clickConfirmOrder() {
        try {
            waitForElementVisibility(confirmOrderNextButton, 10);
            click(confirmOrderNextButton);
        } catch(Exception e) {
            LoggerUtil.warning("Confirm order Next button issue.");
        }
    }

    public boolean isCheckoutPageDisplayed() {
        return driver.getTitle().contains("Checkout") || isElementDisplayed(billingNextButton);
    }
    
    public boolean isBillingAddressFormDisplayed() {
        return isElementDisplayed(billingNextButton);
    }
}
