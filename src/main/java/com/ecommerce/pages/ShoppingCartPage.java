package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page object for the Shopping Cart, handling coupons, gift cards, and shipping estimates.
 */
public class ShoppingCartPage extends BasePage {

    // Cart Items
    private By cartItemRows = By.cssSelector("table.cart tbody tr.cart-item-row");
    private By removeCheckbox = By.name("removefromcart");
    // private By itemQuantityInput = By.cssSelector(".qty-input");
    private By updateCartBtn = By.name("updatecart");
    private By continueShoppingBtn = By.name("continueshopping");
    
    // Discount Code
    private By couponInput = By.name("discountcouponcode");
    private By applyCouponBtn = By.name("applydiscountcouponcode");
    
    // Gift Cards
    private By voucherInput = By.name("giftcardcouponcode");
    private By applyVoucherBtn = By.name("applygiftcardcouponcode");
    
    // Estimate shipping
    private By countrySelect = By.id("CountryId");
    private By stateProvinceSelect = By.id("StateProvinceId");
    private By zipPostalCodeInput = By.id("ZipPostalCode");
    private By estimateShippingBtn = By.name("estimateshipping");
    
    // Checkout
    private By termsOfServiceCheckbox = By.id("termsofservice");
    private By checkoutBtn = By.id("checkout");
    private By orderSummaryContent = By.cssSelector(".order-summary-content");
    private By messageElement = By.cssSelector(".message, .message-error");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmptyCartMessageDisplayed() {
        if(isElementDisplayed(orderSummaryContent)) {
            return getText(orderSummaryContent).contains("Your Shopping Cart is empty!");
        }
        return false;
    }

    public String getMessage() {
        if(isElementDisplayed(messageElement)) {
            return getText(messageElement);
        }
        return "";
    }

    /**
     * Applies a discount coupon code.
     * @param code The coupon code string.
     */
    public void applyCoupon(String code) {
        sendKeys(couponInput, code);
        click(applyCouponBtn);
    }

    /**
     * Applies a gift certificate or voucher.
     * @param code The voucher code.
     */
    public void applyGiftCard(String code) {
        sendKeys(voucherInput, code);
        click(applyVoucherBtn);
    }

    /**
     * Estimates shipping and taxes for a given location.
     */
    public void estimateShipping(String countryName, String stateName, String zip) {
        if (countryName != null) selectByVisibleText(countrySelect, countryName);
        if (stateName != null) selectByVisibleText(stateProvinceSelect, stateName);
        if (zip != null) sendKeys(zipPostalCodeInput, zip);
        click(estimateShippingBtn);
    }

    /**
     * Removes the first item from the cart.
     */
    public void removeFirstItem() {
        List<WebElement> items = driver.findElements(cartItemRows);
        if(items.size() > 0) {
            WebElement firstItem = items.get(0);
            WebElement checkbox = firstItem.findElement(removeCheckbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            click(updateCartBtn);
        }
    }

    /**
     * Accepts terms of service and clicks checkout.
     */
    public void proceedToCheckout() {
        if (isElementDisplayed(termsOfServiceCheckbox)) {
            if (!driver.findElement(termsOfServiceCheckbox).isSelected()) {
                click(termsOfServiceCheckbox);
            }
        }
        click(checkoutBtn);
    }

    /**
     * Checks if the shopping cart is displayed by looking for checkout button.
     * @return boolean True if checkout button is displayed.
     */
    public boolean isShoppingCartPageDisplayed() {
        return isElementDisplayed(checkoutBtn);
    }
}
