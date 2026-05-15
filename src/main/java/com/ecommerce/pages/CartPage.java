package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.ArrayList;

public class CartPage extends BasePage {
    private By cartTable = By.cssSelector(".cart");
    private By removeCheckboxes = By.name("removefromcart");
    private By updateCartButton = By.name("updatecart");
    private By continueShoppingButton = By.name("continueshopping");
    private By checkoutButton = By.id("checkout");
    private By termsOfServiceCheckbox = By.id("termsofservice");
    // private By subTotalLabel = By.cssSelector(".order-summary-content .cart-total .product-price"); // Will adjust based on exact DOM structure
    private By emptyCartMessage = By.cssSelector(".order-summary-content");
    private By discountCodeInput = By.name("discountcouponcode");
    private By applyDiscountButton = By.name("applydiscountcouponcode");
    
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        if (isElementDisplayed(cartTable)) {
            return driver.findElements(By.cssSelector(".cart tbody tr")).size();
        }
        return 0;
    }

    public void removeItemFromCart(String productName) {
        // Find row by product name, then select its remove checkbox
        List<WebElement> rows = driver.findElements(By.cssSelector(".cart tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                row.findElement(By.name("removefromcart")).click();
                break;
            }
        }
        click(updateCartButton);
    }

    public void updateItemQuantity(String productName, int newQuantity) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".cart tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                WebElement qtyInput = row.findElement(By.cssSelector(".qty-input"));
                qtyInput.clear();
                qtyInput.sendKeys(String.valueOf(newQuantity));
                break;
            }
        }
        click(updateCartButton);
    }

    public void proceedToCheckout() {
        if(isElementDisplayed(termsOfServiceCheckbox) && !driver.findElement(termsOfServiceCheckbox).isSelected()) {
            click(termsOfServiceCheckbox);
        }
        click(checkoutButton);
    }

    public void continueShopping() {
        click(continueShoppingButton);
    }

    public double getSubTotal() {
        // Approximation, depends on exact layout
        try {
            String text = driver.findElement(By.cssSelector(".cart-total-right .product-price")).getText();
            return Double.parseDouble(text.replaceAll("[^\\d.]", ""));
        } catch(Exception e) {
            return 0.0;
        }
    }

    public double getShippingCharges() { return 0.0; } // Implement as needed
    public double getTaxAmount() { return 0.0; } // Implement as needed
    
    public double getTotalAmount() {
        return getSubTotal(); // Usually total is in a specific cell
    }

    public String getItemPrice(String productName) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".cart tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                return row.findElement(By.cssSelector(".product-unit-price")).getText();
            }
        }
        return "";
    }

    public boolean isItemPresentInCart(String productName) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".cart tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }

    public void emptyCart() {
        List<WebElement> checkboxes = driver.findElements(removeCheckboxes);
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        if(checkboxes.size() > 0) {
            click(updateCartButton);
        }
    }

    public boolean isCartEmpty() {
        if (driver.findElements(emptyCartMessage).size() > 0) {
            return getText(emptyCartMessage).contains("empty");
        }
        return getCartItemCount() == 0;
    }

    public List<String> getAllCartItems() {
        List<String> items = new ArrayList<>();
        if (isElementDisplayed(cartTable)) {
            List<WebElement> productNames = driver.findElements(By.cssSelector(".cart tbody tr .product-name"));
            for (WebElement name : productNames) {
                items.add(name.getText());
            }
        }
        return items;
    }

    public void applyPromoCode(String code) {
        sendKeys(discountCodeInput, code);
        click(applyDiscountButton);
    }
    
    public boolean isCartPageDisplayed() {
        return driver.getTitle().contains("Cart") || isElementDisplayed(By.cssSelector(".page-title h1"));
    }
}
