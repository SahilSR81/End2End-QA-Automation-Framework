package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.ProductPage;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    private void addProductAndGoToCart() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("Laptop");
        homePage.clickOnProductByName("14.1-inch Laptop");
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        try { Thread.sleep(1500); } catch (InterruptedException e) {} // Wait for ajax
        homePage.viewCart();
    }

    @Test
    public void testViewCart_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
        Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should have items");
    }

    @Test
    public void testRemoveProductFromCart_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        String productName = "14.1-inch Laptop";
        if (cartPage.isItemPresentInCart(productName)) {
            cartPage.removeItemFromCart(productName);
            LoggerUtil.info("Removed item from cart");
        }
    }

    @Test
    public void testUpdateQuantityInCart_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        String productName = "14.1-inch Laptop";
        if (cartPage.isItemPresentInCart(productName)) {
            cartPage.updateItemQuantity(productName, 3);
            LoggerUtil.info("Updated item quantity in cart");
        }
    }

    @Test
    public void testProceedToCheckout_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();
        LoggerUtil.info("Proceeded to checkout");
    }

    @Test
    public void testContinueShopping_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.continueShopping();
        LoggerUtil.info("Clicked continue shopping");
    }

    @Test
    public void testEmptyCart_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.emptyCart();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
        LoggerUtil.info("Emptied the cart");
    }

    @Test
    public void testCartTotalsCalculation_Success() {
        addProductAndGoToCart();
        CartPage cartPage = new CartPage(driver);
        double total = cartPage.getTotalAmount();
        Assert.assertTrue(total > 0, "Cart total should be greater than 0");
        LoggerUtil.info("Cart total verified: " + total);
    }
}
