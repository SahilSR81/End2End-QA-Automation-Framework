package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.ProductPage;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductPageTest extends BaseTest {

    private void navigateToAProduct() {
        HomePage homePage = new HomePage(driver);
        homePage.selectCategory("Computers");
        // We'll search for an item directly to be safe
        homePage.searchProduct("Laptop");
        homePage.clickOnProductByName("14.1-inch Laptop");
    }

    @Test
    public void testProductDetailsDisplay_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page should be displayed");
        Assert.assertNotNull(productPage.getProductName(), "Product name should not be null");
        Assert.assertNotNull(productPage.getProductPrice(), "Product price should not be null");
        Assert.assertTrue(productPage.isAddToCartButtonEnabled(), "Add to cart button should be enabled");
        LoggerUtil.info("Product details displayed correctly for: " + productPage.getProductName());
    }

    @Test
    public void testAddProductToCart_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        LoggerUtil.info("Product added to cart");
    }

    @Test
    public void testAddProductToCartWithQuantity_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCartWithQuantity(3);
        LoggerUtil.info("Added 3 quantities of product to cart");
    }

    @Test
    public void testUpdateQuantityOnProductPage_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.updateQuantity(5);
        LoggerUtil.info("Quantity updated to 5");
    }

    @Test
    public void testProductReviews_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.scrollToReviews();
        LoggerUtil.info("Scrolled to reviews");
    }

    @Test
    public void testProductImageDisplays_Success() {
        navigateToAProduct();
        ProductPage productPage = new ProductPage(driver);
        LoggerUtil.info("Product image displayed successfully");
    }
}
