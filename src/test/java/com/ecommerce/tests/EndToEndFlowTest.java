package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.*;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EndToEndFlowTest extends BaseTest {
    private String testEmail;
    private String testPassword;
    private String productToSearch;

    @BeforeMethod
    public void setUpTestData() {
        testEmail = FakerUtil.generateEmail();
        testPassword = FakerUtil.generatePassword() + "1!"; 
        productToSearch = "Computing and Internet";
        
        LoggerUtil.info("================================================================================");
        LoggerUtil.info("STARTING END-TO-END E-COMMERCE TEST FLOW");
        LoggerUtil.info("Test Email: " + testEmail);
        LoggerUtil.info("================================================================================");
    }

    @Test(description = "Complete E-Commerce Journey")
    public void testCompleteECommerceJourney_Success() {
        String shippingMethod = "Ground";
        String paymentMethod = "Credit Card";
        // STEP 1: Navigate to Home Page
        LoggerUtil.info("STEP 1: Navigating to Home Page");
        HomePage homePage = new HomePage(driver);
        homePage.takeScreenshot("01_HomePage_Loaded");
        
        // STEP 2: Login
        LoggerUtil.info("STEP 2: Logging in with registered user");
        ensureLoggedIn();
        
        // STEP 3: Search for Product
        LoggerUtil.info("STEP 3: Searching for Product: " + productToSearch);
        homePage.searchProduct(productToSearch);
        homePage.takeScreenshot("03_SearchResults_Displayed");

        // STEP 4: Click on first product
        LoggerUtil.info("STEP 4: Clicking on First Product");
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.clickFirstResult();
        
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.takeScreenshot("04_ProductPage_Opened");

        // STEP 5: Add to cart
        LoggerUtil.info("STEP 5: Adding Product to Cart");
        productDetailPage.addToCart();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        productDetailPage.takeScreenshot("05_ProductAdded_ToCart");

        // STEP 6: Navigate to cart
        LoggerUtil.info("STEP 6: Navigating to Cart Page");
        homePage.viewCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        Assert.assertTrue(cartPage.isShoppingCartPageDisplayed(), "Cart page should be displayed");
        cartPage.takeScreenshot("06_CartPage_Opened");

        // STEP 7: Proceed to Checkout
        LoggerUtil.info("STEP 7: Proceeding to Checkout");
        cartPage.proceedToCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.takeScreenshot("07_CheckoutPage_Opened");

        // STEP 8: Billing address
        LoggerUtil.info("STEP 8: Confirming Billing Address");
        String fname = FakerUtil.generateFirstName();
        String lname = FakerUtil.generateLastName();
        String company = "TestCompany";
        String country = "United States";
        String city = FakerUtil.generateCity();
        String address1 = FakerUtil.generateAddress();
        String zip = "12345";
        String phone = "1234567890";
        
        checkoutPage.fillBillingAddress(fname, lname, com.ecommerce.utils.TestDataHolder.getEmail(), company, country, null, city, address1, null, zip, phone, null);
        checkoutPage.takeScreenshot("08_BillingAddress_Filled");

        // STEP 9: Select shipping
        LoggerUtil.info("STEP 9: Confirming Shipping Address");
        checkoutPage.clickShippingContinue();

        LoggerUtil.info("STEP 10: Selecting Shipping Method: " + shippingMethod);
        checkoutPage.selectShippingMethodAndContinue(shippingMethod);

        // STEP 11: Proceed to payment
        LoggerUtil.info("STEP 11: Selecting Payment Method: " + paymentMethod);
        checkoutPage.selectPaymentMethod(paymentMethod);
        checkoutPage.clickPaymentMethodContinue();

        LoggerUtil.info("STEP 12: Confirming Payment Info");
        checkoutPage.fillPaymentInfoCreditCard("Visa", fname + " " + lname, "4111111111111111", "123");
        checkoutPage.clickPaymentInfoContinue();

        LoggerUtil.info("STEP 13: Confirming Order");
        checkoutPage.clickConfirmOrder();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        // STEP 14: Order Confirmation
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order confirmation page should be displayed");
        String orderNumber = confirmationPage.getOrderNumber();
        LoggerUtil.info("Order Number: " + orderNumber);
        confirmationPage.clickContinueButton();

        // STEP 15: Logout
        LoggerUtil.info("STEP 15: Logging Out");
        homePage.logout();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        Assert.assertTrue(homePage.isLoginLinkVisible(), "Login link should be visible after logout");
        LoggerUtil.info("================================================================================");
        LoggerUtil.info("END-TO-END E-COMMERCE TEST COMPLETED SUCCESSFULLY!");
        LoggerUtil.info("================================================================================");
    }
}
