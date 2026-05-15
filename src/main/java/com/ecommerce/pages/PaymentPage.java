package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Map;
import com.ecommerce.utils.LoggerUtil;

public class PaymentPage extends BasePage {
    private By codRadio = By.id("paymentmethod_0");
    private By checkRadio = By.id("paymentmethod_1");
    private By creditCardRadio = By.id("paymentmethod_2");
    private By purchaseOrderRadio = By.id("paymentmethod_3");
    private By paymentMethodNextButton = By.cssSelector(".payment-method-next-step-button");

    // private By creditCardType = By.id("CreditCardType");
    private By cardholderName = By.id("CardholderName");
    private By cardNumber = By.id("CardNumber");
    private By expireMonth = By.id("ExpireMonth");
    private By expireYear = By.id("ExpireYear");
    private By cardCode = By.id("CardCode");
    private By paymentInfoNextButton = By.cssSelector(".payment-info-next-step-button");

    private By confirmOrderNextButton = By.cssSelector("button.confirm-order-next-step-button, input.confirm-order-next-step-button, .button-1.confirm-order-next-step-button");
    private By orderTotalLabel = By.xpath("//span[contains(text(), 'Total:')]/following-sibling::span/strong | //td[@class='cart-total-right']//strong | //span[@class='product-price order-total']//strong");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void selectPaymentMethod(String method) {
        try {
            waitForElementVisibility(By.cssSelector(".method-list"), 10);
            if ("CreditCard".equalsIgnoreCase(method)) {
                click(creditCardRadio);
            } else if ("BankTransfer".equalsIgnoreCase(method) || "Check".equalsIgnoreCase(method)) {
                click(checkRadio);
            } else if ("CashOnDelivery".equalsIgnoreCase(method) || "COD".equalsIgnoreCase(method)) {
                click(codRadio);
            } else if ("PurchaseOrder".equalsIgnoreCase(method)) {
                click(purchaseOrderRadio);
            }
            click(paymentMethodNextButton);
        } catch (Exception e) {
            LoggerUtil.warning("Payment method selection failed or skipped.");
        }
    }

    public void fillCreditCardDetails(String cardNumberStr, String expiryDate, String cvv, String cardholderNameStr) {
        try {
            waitForElementVisibility(cardholderName, 10);
            sendKeys(cardholderName, cardholderNameStr);
            sendKeys(cardNumber, cardNumberStr);
            
            if(expiryDate != null && expiryDate.contains("/")) {
                String[] parts = expiryDate.split("/");
                // Month is displayed as 01, 02.. 12
                selectByVisibleText(expireMonth, parts[0]);
                selectByVisibleText(expireYear, "20" + parts[1]);
            }
            sendKeys(cardCode, cvv);
            click(paymentInfoNextButton);
        } catch(Exception e) {
            LoggerUtil.warning("Credit card details form not visible.");
        }
    }

    public void fillCreditCardDetails(Map<String, String> cardData) {
        fillCreditCardDetails(cardData.get("cardNumber"), cardData.get("expiryDate"), cardData.get("cvv"), cardData.get("cardholderName"));
    }

    public void fillPaymentInfoPO(String po) {
        try {
            waitForElementVisibility(By.id("PurchaseOrderNumber"), 10);
            sendKeys(By.id("PurchaseOrderNumber"), po);
        } catch(Exception e) {
            LoggerUtil.warning("PO details form not visible.");
        }
        click(paymentInfoNextButton);
    }

    public void confirmOrder() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        if(isConfirmOrderButtonEnabled()){
            click(confirmOrderNextButton);
        }
    }

    public double getOrderTotal() {
        try {
            waitForElementVisibility(orderTotalLabel, 10);
            String totalText = getText(orderTotalLabel);
            return Double.parseDouble(totalText.replaceAll("[^\\d.]", ""));
        } catch(Exception e) {
            LoggerUtil.warning("Order total not found on payment page.");
            return 0.0;
        }
    }

    public double getSubTotal() { return 0.0; }
    public double getShippingCost() { return 0.0; }
    public double getTaxAmount() { return 0.0; }
    public String getOrderSummary() { return ""; }

    public boolean isPaymentPageDisplayed() {
        try {
            waitForElementVisibility(paymentMethodNextButton, 10);
            return true;
        } catch (Exception e) {
            return isElementDisplayed(paymentInfoNextButton) || isElementDisplayed(confirmOrderNextButton);
        }
    }

    public String getSelectedPaymentMethod() { return ""; }

    public void proceedWithSelectedPaymentMethod() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        if (driver.findElements(paymentInfoNextButton).size() > 0 && driver.findElement(paymentInfoNextButton).isDisplayed()){
            click(paymentInfoNextButton);
        }
    }

    public boolean isConfirmOrderButtonEnabled() {
        return driver.findElements(confirmOrderNextButton).size() > 0 && driver.findElement(confirmOrderNextButton).isDisplayed();
    }
}
