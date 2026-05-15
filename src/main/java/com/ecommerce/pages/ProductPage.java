package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    private By productName = By.cssSelector(".product-name h1");
    private By productPrice = By.cssSelector(".product-price span");
    private By productDescription = By.cssSelector(".full-description, .short-description");
    private By addToCartButton = By.cssSelector("input[id^='add-to-cart-button']");
    private By addToWishlistButton = By.cssSelector("input.add-to-wishlist-button, input[id^='add-to-wishlist-button']");
    private By quantityInput = By.cssSelector("input[id^='addtocart_'][id$='_EnteredQuantity']");
    private By stockStatus = By.cssSelector(".stock .value");
    private By reviewsLink = By.cssSelector(".product-review-links");
    private By errorMessage = By.cssSelector(".message-error");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart() {
        click(addToCartButton);
    }

    public void addProductToCartWithQuantity(int quantity) {
        updateQuantity(quantity);
        click(addToCartButton);
    }

    public void addProductToWishlist() {
        click(addToWishlistButton);
    }

    public void updateQuantity(int quantity) {
        if(driver.findElements(quantityInput).size() > 0) {
            sendKeys(quantityInput, String.valueOf(quantity));
        }
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public String getProductDescription() {
        return getText(productDescription);
    }

    public String getStockStatus() {
        return getText(stockStatus);
    }

    public double getProductRating() {
        // Implement rating extraction logic if rating element is present
        return 0.0; 
    }

    public void leaveReview(String review, int rating) {
        scrollToReviews();
        click(reviewsLink);
        // Additional implementation needed depending on review page elements
    }

    public void scrollToReviews() {
        scrollToElement(reviewsLink);
    }

    public void clickRelatedProduct(String relatedProductName) {
        click(By.linkText(relatedProductName));
    }

    public boolean isAddToCartButtonEnabled() {
        return driver.findElements(addToCartButton).size() > 0 && driver.findElement(addToCartButton).isEnabled();
    }

    public String getErrorMessage() {
        if (driver.findElements(errorMessage).size() > 0) {
            return getText(errorMessage);
        }
        return "";
    }
    
    public boolean isProductPageDisplayed() {
        return isElementDisplayed(productName) && isElementDisplayed(addToCartButton);
    }
}
