package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.OrderConfirmationPage;
import com.ecommerce.pages.ProductDetailPage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.pages.SearchResultPage;
import com.ecommerce.pages.ShoppingCartPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderConfirmationPageTest extends BaseTest {

    private void createOrder() {
        ensureLoggedIn();
        HomePage homePage = new HomePage(driver);
        
        homePage.searchProduct("Computing and Internet");
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.clickFirstResult();
        
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.addToCart();
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        
        homePage.viewCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        String fname = FakerUtil.generateFirstName();
        String lname = FakerUtil.generateLastName();
        String email = com.ecommerce.utils.TestDataHolder.getEmail();
        String city = FakerUtil.generateCity();
        String address1 = FakerUtil.generateAddress();
        String zip = "12345";
        String phone = "1234567890";

        checkoutPage.fillBillingAddress(fname, lname, email, null, "United States", null, city, address1, null, zip, phone, null);
        checkoutPage.clickShippingContinue();
        checkoutPage.selectShippingMethodAndContinue("Ground");
        checkoutPage.selectPaymentMethod("CashOnDelivery");
        checkoutPage.clickPaymentMethodContinue();
        checkoutPage.clickPaymentInfoContinue();
        checkoutPage.clickConfirmOrder();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    public void testOrderConfirmationAndContinueFlow() {
        createOrder();
        
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed(), "Order confirmation page should be displayed.");
        
        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertFalse(orderNumber.isEmpty(), "Order number should be generated.");
        LoggerUtil.info("Order Confirmed with order number: " + orderNumber);
        
        String message = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(message.contains("successfully processed"), "Success message should contain 'successfully processed'");
        LoggerUtil.info("Confirmation msg: " + message);

        confirmationPage.clickContinueButton();
        LoggerUtil.info("Clicked Continue button on order confirmation page.");
        
        // Assert we return to Home page (or any indication of leaving the checkout)
        HomePage homePage = new HomePage(driver);
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout"), "Should navigate to home page");
        LoggerUtil.info("Successfully returned back from confirmation page.");
    }
}
