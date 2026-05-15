package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the Wish List.
 */
public class WishlistPage extends BasePage {

    private By tableRows = By.cssSelector("table.table-hover tbody tr");
    private By removeBtn = By.cssSelector("a.btn-danger");
    private By addToCartBtn = By.cssSelector("button.btn-primary[data-original-title='Add to Cart']");
    private By successAlert = By.cssSelector(".alert-success");

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Retrieves the count of items present in the wishlist.
     * @return Item count.
     */
    public int getWishlistItemsCount() {
        return driver.findElements(tableRows).size();
    }

    /**
     * Adds the first item in the wishlist to the cart.
     */
    public void addFirstItemToCart() {
        if(isElementDisplayed(addToCartBtn)) {
            driver.findElements(addToCartBtn).get(0).click();
        }
    }

    /**
     * Removes the first item from the wishlist.
     */
    public void removeFirstItem() {
        if(isElementDisplayed(removeBtn)) {
            driver.findElements(removeBtn).get(0).click();
        }
    }

    /**
     * Validates if a success message appears.
     * @return boolean True if success message appears.
     */
    public boolean isSuccessAlertDisplayed() {
        return isElementDisplayed(successAlert);
    }
}
