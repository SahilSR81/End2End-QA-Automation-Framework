package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.ChangePasswordPage;
import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.MyAccountPage;
import com.ecommerce.utils.FakerUtil;
import com.ecommerce.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChangePasswordPageTest extends BaseTest {

    private String currentUserEmail;
    private String currentUserPassword;

    private void prepareChangePassword() {
        ensureLoggedIn();
        currentUserEmail = com.ecommerce.utils.TestDataHolder.getEmail();
        currentUserPassword = com.ecommerce.utils.TestDataHolder.getPassword();
        
        HomePage homePage = new HomePage(driver);
        // Navigate to Change Password if not there
        if (!driver.getCurrentUrl().contains("changepassword")) {
            homePage.clickMyAccount();
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            myAccountPage.clickChangePasswordLink();
        }
    }

    @Test(priority = 1)
    public void testChangePassword_BlankFields_Failure() {
        prepareChangePassword();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        
        changePasswordPage.changePassword("", "", "");
        boolean hasError = changePasswordPage.isErrorMessageDisplayed() || changePasswordPage.getValidationErrors().length() > 0;
        Assert.assertTrue(hasError, "Error message should be displayed for blank fields.");
        LoggerUtil.info("Verified blank fields error on Change Password.");
    }

    @Test(priority = 2)
    public void testChangePassword_WrongCurrentPassword_Failure() {
        prepareChangePassword();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        
        changePasswordPage.changePassword("wrongcurrentpass", "NewPassword123!", "NewPassword123!");
        boolean hasError = changePasswordPage.isErrorMessageDisplayed() || changePasswordPage.getValidationErrors().length() > 0;
        Assert.assertTrue(hasError, "Error message should be displayed for wrong current password.");
        LoggerUtil.info("Verified wrong current password error.");
    }

    @Test(priority = 3)
    public void testChangePassword_NewPasswordMismatch_Failure() {
        prepareChangePassword();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        
        changePasswordPage.changePassword(currentUserPassword, "NewPassword123!", "DifferentPass321!");
        boolean hasError = changePasswordPage.isErrorMessageDisplayed() || changePasswordPage.getValidationErrors().length() > 0 || changePasswordPage.getResultMessage().contains("do not match");
        Assert.assertTrue(hasError, "Error message should be displayed for password mismatch.");
        LoggerUtil.info("Verified new password mismatch error.");
    }

    @Test(priority = 4)
    public void testChangePassword_ShortNewPassword_Failure() {
        prepareChangePassword();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        
        changePasswordPage.changePassword(currentUserPassword, "123", "123");
        boolean hasError = changePasswordPage.isErrorMessageDisplayed() || changePasswordPage.getValidationErrors().length() > 0;
        Assert.assertTrue(hasError, "Error message should be displayed for short password.");
        LoggerUtil.info("Verified short new password error.");
    }

    @Test(priority = 5)
    public void testChangePassword_Valid_Success() {
        prepareChangePassword();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        
        String newPass = FakerUtil.generatePassword();
        changePasswordPage.changePassword(currentUserPassword, newPass, newPass);
        
        Assert.assertFalse(changePasswordPage.isErrorMessageDisplayed() && changePasswordPage.getValidationErrors().isEmpty(), "No error should be displayed on success.");
        Assert.assertTrue(changePasswordPage.getResultMessage().toLowerCase().contains("changed"), "Success message should be shown.");
        com.ecommerce.utils.TestDataHolder.setPassword(newPass);
        LoggerUtil.info("Verified successful change password. New password saved.");
    }
}
