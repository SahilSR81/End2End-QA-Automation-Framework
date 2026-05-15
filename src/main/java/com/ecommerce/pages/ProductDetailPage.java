package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Detailed Page Object for reviewing a single product explicitly.
 */
public class ProductDetailPage extends BasePage {

    private By productName = By.cssSelector("div.col-sm-4 h1");
    private By addToCartBtn = By.cssSelector("input[id^='add-to-cart-button'], button[id^='add-to-cart-button'], .add-to-cart-button");
    private By qtyInput = By.id("input-quantity");
    private By wishlistBtn = By.xpath("//button[@data-original-title='Add to Wish List']");
    private By successAlert = By.cssSelector(".alert-success");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Retrieves the product name shown on the detail page.
     * @return Product name string.
     */
    public String getProductTitle() {
        return getText(productName);
    }

    /**
     * Sets the quantity for the product.
     * @param quantity The string format quantity.
     */
    public void setQuantity(String quantity) {
        driver.findElement(qtyInput).clear();
        sendKeys(qtyInput, quantity);
    }

    /**
     * Adds the current product to Cart.
     */
    public void addToCart() {
        click(addToCartBtn);
    }

    /**
     * Adds the current product to Wishlist.
     */
    public void addToWishlist() {
        click(wishlistBtn);
    }

    /**
     * Checks if a success alert is shown after adding to cart or wishlist.
     * @return true if visible.
     */
    public boolean isSuccessAlertDisplayed() {
        return isElementDisplayed(successAlert);
    }
}
