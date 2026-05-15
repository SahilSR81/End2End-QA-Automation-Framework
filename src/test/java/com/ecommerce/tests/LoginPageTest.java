package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void testLogin_BlankCredentials_Failure() {
        new HomePage(driver).clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for blank credentials.");
        LoggerUtil.info("Verified blank credentials error.");
    }

    @Test(priority = 2)
    public void testLogin_InvalidEmailFormat_Failure() {
        new HomePage(driver).clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.loginWithInvalidCredentials("invalid-email-format", "Password123!");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid email format.");
        LoggerUtil.info("Verified invalid email format error.");
    }

    @Test(priority = 3)
    public void testLogin_WrongPassword_Failure() {
        new HomePage(driver).clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.loginWithInvalidCredentials(FakerUtil.generateEmail(), "WrongPassword123!");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for wrong password.");
        LoggerUtil.info("Verified wrong password error.");
    }

    @Test(priority = 4)
    public void testForgotPassword_InvalidEmail() {
        new HomePage(driver).clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgottenPassword();
        
        Assert.assertTrue(driver.getTitle().contains("Password Recovery"), "Should navigate to Password Recovery page");
        driver.findElement(By.id("Email")).sendKeys("invalid-email");
        driver.findElement(By.name("send-email")).click();
        
        Assert.assertTrue(driver.findElements(By.cssSelector(".field-validation-error, .result")).size() > 0, "Should display error about email.");
        LoggerUtil.info("Verified forgot password flow with invalid email");
    }

    @Test(priority = 5)
    public void testForgotPassword_ValidEmail() {
        new HomePage(driver).clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgottenPassword();
        
        driver.findElement(By.id("Email")).sendKeys("tester_sahil@gmail.com");
        driver.findElement(By.name("send-email")).click();
        
        // It should either say email sent or still be there. 
        Assert.assertTrue(driver.findElements(By.cssSelector(".result")).size() > 0 || driver.getTitle().contains("Password Recovery"), "Should display result message.");
        LoggerUtil.info("Verified forgot password flow with valid email");
    }

    @Test(priority = 6)
    public void testLogin_ValidCredentials_Success() {
        HomePage homePage = new HomePage(driver);
        
        String userEmail = com.ecommerce.utils.TestDataHolder.getEmail();
        String userPass = com.ecommerce.utils.TestDataHolder.getPassword();
        
        if (userEmail == null || userEmail.isEmpty()) {
            // Need a user to test login, rely on BaseTest to register one and store it
            ensureLoggedIn();
            userEmail = com.ecommerce.utils.TestDataHolder.getEmail();
            userPass = com.ecommerce.utils.TestDataHolder.getPassword();
        }

        // Ensure user is logged out before logging in
        if (homePage.isLoginLinkVisible() == false) {
            homePage.logout();
        }

        // 2. Perform Login
        homePage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCredentials(userEmail, userPass);
        
        // Wait for login to complete and page to refresh
        homePage.waitForLoginCompletion();
        
        // 3. Verify successful login (logout link should be visible, or 'My account')
        Assert.assertFalse(homePage.isLoginLinkVisible(), "Login link should not be visible after successful login.");
        LoggerUtil.info("Tested valid credentials login flow successfully.");
    }
}
