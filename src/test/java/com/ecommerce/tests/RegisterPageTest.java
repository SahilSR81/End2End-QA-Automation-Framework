package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {

    @Test(priority = 1)
    public void testRegister_AllBlank_Failure() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.clickRegisterButton();
        
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error messages should be displayed for all blank fields.");
        LoggerUtil.info("Verified all blank fields error.");
    }

    @Test(priority = 2)
    public void testRegister_OnlyFirstAndLastName_Failure() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.fillAllFields("John", "Doe", "", "", "Male");
        registerPage.clickRegisterButton();
        
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message should be displayed for missing email and password.");
        LoggerUtil.info("Verified partial fields error.");
    }

    @Test(priority = 3)
    public void testRegister_InvalidEmail_Failure() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.fillAllFields("John", "Doe", "invalid-email", "Password123!", "Male");
        registerPage.clickRegisterButton();
        
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid email.");
        LoggerUtil.info("Verified invalid email error.");
    }

    @Test(priority = 4)
    public void testRegister_PasswordLessThan6Chars_Failure() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.fillAllFields("John", "Doe", FakerUtil.generateEmail(), "12345", "Male");
        registerPage.clickRegisterButton();
        
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message should be displayed for short password.");
        LoggerUtil.info("Verified short password error.");
    }

    @Test(priority = 5)
    public void testRegister_PasswordMismatch_Failure() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.registerWithInvalidData_PasswordMismatch();
        
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message should indicate password mismatch.");
        LoggerUtil.info("Verified password mismatch error.");
    }

    @Test(priority = 6)
    public void testRegisterWithValidData_Success() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();
        
        RegisterPage registerPage = new RegisterPage(driver);
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), "Register page should be displayed");

        String email = FakerUtil.generateEmail();
        String password = FakerUtil.generatePassword();

        // Save for cross-test usage
        com.ecommerce.utils.TestDataHolder.setEmail(email);
        com.ecommerce.utils.TestDataHolder.setPassword(password);

        registerPage.fillAllFields(
            FakerUtil.generateFirstName(),
            FakerUtil.generateLastName(),
            email,
            password,
            "Male"
        );
        registerPage.clickRegisterButton();

        // Check if registration was successful
        Assert.assertFalse(registerPage.isErrorMessageDisplayed(), "Error message should not be displayed");
        LoggerUtil.info("Registration successful for: " + email);

        // Click continue button after registration
        registerPage.clickContinueButton();
        LoggerUtil.info("Clicked continue button after registration");

        // Newsletter Test logic
        homePage.subscribeToNewsletter(email);
        LoggerUtil.info("Subscribed to newsletter with email: " + email);
        
        try { Thread.sleep(2000); } catch(Exception e){} 
        String newsletterMsg = homePage.getNewsletterResultMessage();
        LoggerUtil.info("Newsletter result: " + newsletterMsg);

        // Optional Logout right after, but BaseTest ensures login if needed by other tests
        homePage.logout();
        LoggerUtil.info("Clicked Log out link");
        Assert.assertTrue(homePage.isLoginLinkVisible(), "Login link should be visible after logout");
    }
}
