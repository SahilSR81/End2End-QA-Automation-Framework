package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.PaymentPage;
import com.ecommerce.pages.ProductPage;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PaymentPageTest extends BaseTest {

    private void preparePayment() {
        ensureLoggedIn();
        HomePage homePage = new HomePage(driver);
        
        homePage.searchProduct("Laptop");
        homePage.clickOnProductByName("14.1-inch Laptop");
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        
        homePage.viewCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillBillingAddress("Test", "User", "test@test.com", "Company", "United States", null, "City", "123 Main St", null, "12345", "555-1234", null);
        checkoutPage.clickShippingContinue();
        checkoutPage.selectShippingMethodAndContinue("Ground");
    }



    @Test
    public void testPaymentPageDisplay_Success() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        Assert.assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment page should be displayed");
    }

    @Test
    public void testSelectPaymentMethod_CreditCard_Success() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("CreditCard");
        LoggerUtil.info("Credit Card payment method selected");
    }

    @Test
    public void testSelectPaymentMethod_CashOnDelivery_Success() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("CashOnDelivery");
        LoggerUtil.info("Cash on Delivery method selected");
    }



    @Test
    public void testConfirmOrder_Success_COD() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("CashOnDelivery");
        paymentPage.proceedWithSelectedPaymentMethod(); // Submit payment info
        paymentPage.confirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
        LoggerUtil.info("Order confirmed via COD");
    }

    @Test
    public void testConfirmOrder_Success_CheckMoneyOrder() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("Check");
        paymentPage.proceedWithSelectedPaymentMethod(); // Submit payment info
        paymentPage.confirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
        LoggerUtil.info("Order confirmed via Check/Money Order");
    }

    @Test
    public void testConfirmOrder_Success_CreditCard() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("CreditCard");
        paymentPage.fillCreditCardDetails("4111111111111111", "12/28", "123", "User Name");
        paymentPage.confirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
        LoggerUtil.info("Order confirmed via Credit Card");
    }

    @Test
    public void testConfirmOrder_Success_PurchaseOrder() {
        preparePayment();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.selectPaymentMethod("PurchaseOrder");
        paymentPage.fillPaymentInfoPO("PO-12345");
        paymentPage.confirmOrder();
        try { Thread.sleep(2000); } catch(Exception e){}
        com.ecommerce.pages.OrderConfirmationPage confirmationPage = new com.ecommerce.pages.OrderConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationPageDisplayed() || driver.getPageSource().contains("successfully processed"), "Order should be completed.");
        LoggerUtil.info("Order confirmed via Purchase Order");
    }
}
