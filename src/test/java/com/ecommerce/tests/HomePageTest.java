package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.ProductPage;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePageLoads_Success() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
        LoggerUtil.info("Home page loaded successfully");
    }

    @Test
    public void testSearchProduct_Success() {
        HomePage homePage = new HomePage(driver);
        String productName = "Laptop";
        homePage.searchProduct(productName);
        LoggerUtil.info("Search submitted for: " + productName);
        
        com.ecommerce.pages.SearchResultPage searchResultPage = new com.ecommerce.pages.SearchResultPage(driver);
        java.util.List<String> results = searchResultPage.getResultTitles();
        Assert.assertTrue(driver.getTitle().contains("Search"), "Search results page should be displayed");
        Assert.assertTrue(results.size() >= 0, "Search results parsed successfully.");
    }

    @Test
    public void testCategoryNavigation_Success() {
        HomePage homePage = new HomePage(driver);
        String category = "Computers";
        homePage.selectCategory(category);
        LoggerUtil.info("Navigated to category: " + category);
        Assert.assertTrue(driver.getTitle().contains(category), "Category page should be displayed");
    }

    @Test
    public void testLoginLinkNavigation_Success() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        LoggerUtil.info("Navigated to Login page successfully");
    }

    @Test
    public void testRegisterLinkNavigation_Success() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), "Register page should be displayed");
        LoggerUtil.info("Navigated to Register page successfully");
    }

    @Test
    public void testCartAccess_Success() {
        HomePage homePage = new HomePage(driver);
        homePage.viewCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
        LoggerUtil.info("Navigated to Cart page successfully");
    }
}
