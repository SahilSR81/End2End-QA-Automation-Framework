package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.ShoppingCartPage;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.ProductDetailPage;
import com.ecommerce.pages.SearchResultPage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutPageTest extends BaseTest {

    private void prepareCheckout() {
        ensureLoggedIn();
        HomePage homePage = new HomePage(driver);
        
        // Add to cart
        homePage.searchProduct("Computing and Internet");
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.clickFirstResult();
        
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.addToCart();
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        
        homePage.viewCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        cartPage.proceedToCheckout();
    }

    @Test(priority = 1)
    public void testCheckout_GuestCheckout_RedirectsToLogin() {
        HomePage homePage = new HomePage(driver);
        
        // Ensure user is logged out
        if (!homePage.isLoginLinkVisible()) {
            homePage.logout();
            LoggerUtil.info("Logged out for guest check.");
        }

        // Add to cart without login
        homePage.searchProduct("Computing and Internet");
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.clickFirstResult();
        
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.addToCart();
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        
        homePage.viewCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        cartPage.proceedToCheckout();
        
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed() || driver.getCurrentUrl().contains("login"), "Should be redirected to Login page for Guest Checkout.");
        LoggerUtil.info("Verified redirect to login page for guest checkout.");
    }

    @Test(priority = 2)
    public void testCheckout_MissingBillingInfo_Failure() {
        prepareCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page should be displayed");

        checkoutPage.fillBillingAddress("", "", "", "", "United States", null, "City", "Addr", null, "12345", "1234", null);
        LoggerUtil.info("Filled incomplete Billing Address step.");
        
        // Verification: If the next step doesn't expand
        Assert.assertTrue(checkoutPage.isBillingAddressFormDisplayed(), "Should stay on billing form due to missing info.");
    }

    @Test(priority = 3)
    public void testCheckout_InvalidAddressFields_Failure() {
        prepareCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        // Put numbers in names
        checkoutPage.fillBillingAddress("123", "456", "invalidemail", "", "United States", null, "", "Addr", null, "ABC", "XYZ", null);
        
        Assert.assertTrue(checkoutPage.isBillingAddressFormDisplayed(), "Should stay on billing form due to invalid address fields.");
    }

    @Test(priority = 4)
    public void testCheckoutFlow_Success_Ground_CreditCard() {
        prepareCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        String fname = FakerUtil.generateFirstName();
        String lname = FakerUtil.generateLastName();
        String address1 = FakerUtil.generateAddress();
        
        checkoutPage.fillBillingAddress(fname, lname, com.ecommerce.utils.TestDataHolder.getEmail(), "Company", "United States", null, FakerUtil.generateCity(), address1, null, "12345", "1234567890", null);
        checkoutPage.clickShippingContinue();

        checkoutPage.selectShippingMethodAndContinue("Ground");
        LoggerUtil.info("Selected Ground shipping.");

        checkoutPage.selectPaymentMethod("Credit Card");
        checkoutPage.clickPaymentMethodContinue();
        checkoutPage.fillPaymentInfoCreditCard("Visa", "Test User", "4111111111111111", "123");
        LoggerUtil.info("Filled and Selected Credit Card payment.");

        checkoutPage.clickPaymentInfoContinue();
        checkoutPage.clickConfirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
    }
    
    @Test(priority = 5)
    public void testCheckoutFlow_Success_2ndDayAir_PurchaseOrder() {
        prepareCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.fillBillingAddress(FakerUtil.generateFirstName(), FakerUtil.generateLastName(), com.ecommerce.utils.TestDataHolder.getEmail(), "Company", "United States", null, FakerUtil.generateCity(), FakerUtil.generateAddress(), null, "12345", "1234567890", null);
        checkoutPage.clickShippingContinue();

        checkoutPage.selectShippingMethodAndContinue("2nd Day Air");
        LoggerUtil.info("Selected 2nd Day Air shipping.");

        checkoutPage.selectPaymentMethod("Purchase Order");
        checkoutPage.clickPaymentMethodContinue();
        checkoutPage.fillPaymentInfoPO("PO-98765");
        LoggerUtil.info("Selected Purchase Order payment.");

        checkoutPage.clickPaymentInfoContinue();
        checkoutPage.clickConfirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
    }
    
    @Test(priority = 6)
    public void testCheckoutFlow_Success_NextDayAir_CheckMoneyOrder() {
        prepareCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.fillBillingAddress(FakerUtil.generateFirstName(), FakerUtil.generateLastName(), com.ecommerce.utils.TestDataHolder.getEmail(), "Company", "United States", null, FakerUtil.generateCity(), FakerUtil.generateAddress(), null, "12345", "1234567890", null);
        checkoutPage.clickShippingContinue();

        checkoutPage.selectShippingMethodAndContinue("Next Day Air");

        checkoutPage.selectPaymentMethod("Check / Money Order");
        checkoutPage.clickPaymentMethodContinue();

        checkoutPage.clickPaymentInfoContinue();
        checkoutPage.clickConfirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
    }
}
