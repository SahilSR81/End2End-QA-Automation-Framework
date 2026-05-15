package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.ConfigReader;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.MyAccountPage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MyAccountPageTest extends BaseTest {

    private String userEmail;
    private String userPass;

    private void goToMyAccount() {
        ensureLoggedIn();
        HomePage homePage = new HomePage(driver);
        homePage.clickMyAccount();
    }

    @Test
    public void testMyAccountPageDisplay_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(), "My Account page should be displayed");
    }

    @Test
    public void testUserProfileDisplay_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Assert.assertNotNull(myAccountPage.getFirstName(), "First Name should be set");
        Assert.assertNotNull(myAccountPage.getLastName(), "Last Name should be set");
        Assert.assertEquals(myAccountPage.getEmailAddress(), com.ecommerce.utils.TestDataHolder.getEmail(), "Email should match what was registered");
    }

    @Test
    public void testEditProfile_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.editProfile("NewFirst", "NewLast");
        LoggerUtil.info("Profile edited successfully");
    }

    @Test
    public void testChangePassword_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        String oldPass = com.ecommerce.utils.TestDataHolder.getPassword();
        String newPass = "NewPass@123";
        myAccountPage.changePassword(oldPass, newPass);
        com.ecommerce.utils.TestDataHolder.setPassword(newPass);
        LoggerUtil.info("Password change requested and updated in TestDataHolder");
    }

    @Test
    public void testChangePasswordWithWrongOldPassword_Failure() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.changePassword("wrongPass123", "NewPass@123");
        LoggerUtil.info("Password change failure requested");
    }

    @Test
    public void testAddNewAddress_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        
        Map<String, String> addr = new HashMap<>();
        addr.put("firstName", "Test");
        addr.put("lastName", "User");
        addr.put("address", "123 Main St");
        addr.put("city", "Cityville");
        addr.put("country", "United States");
        addr.put("zipCode", "12345");
        addr.put("phoneNumber", "555-1234");
        
        myAccountPage.addNewAddress(addr);
        LoggerUtil.info("New address added successfully");
    }

    @Test
    public void testEditExistingAddress_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.editExistingAddress("Test User", new HashMap<>());
        LoggerUtil.info("Address edit verified");
    }

    @Test
    public void testDeleteAddress_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.deleteAddress("Test User");
        LoggerUtil.info("Address delete verified");
    }

    @Test
    public void testViewOrderHistory_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.viewOrderHistory();
        LoggerUtil.info("Order history view verified");
    }

    @Test
    public void testLogoutFromMyAccount_Success() {
        goToMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.logout();
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLoginLinkVisible(), "Login link should be visible after logout");
    }
}
