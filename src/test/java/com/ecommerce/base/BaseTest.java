package com.ecommerce.base;

import com.ecommerce.config.ConfigReader;
import com.ecommerce.utils.BrowserUtil;
import com.ecommerce.utils.LoggerUtil;
import com.ecommerce.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ecommerce.pages.HomePage;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.RegisterPage;
import com.ecommerce.utils.TestDataHolder;
import com.ecommerce.utils.FakerUtil;

public class BaseTest {
    protected WebDriver driver;
    protected String baseURL;

    protected boolean isUserLoggedIn() {
        HomePage homePage = new HomePage(driver);
        String pageTitle = driver.getTitle();
        return !pageTitle.contains("Login") && (homePage.isLoginLinkVisible() == false || pageTitle.contains("My account"));
    }

    protected void loginWithRegisteredUser() {
        HomePage homePage = new HomePage(driver);
        if (homePage.isLoginLinkVisible()) {
            homePage.clickLoginLink();
        }
        
        String email = TestDataHolder.getEmail();
        String password = TestDataHolder.getPassword();

        // If credentials don't exist yet (e.g. running standalone post-login test), create a user
        if (email == null || email.isEmpty()) {
            homePage.clickRegisterLink();
            RegisterPage registerPage = new RegisterPage(driver);
            email = FakerUtil.generateEmail();
            password = FakerUtil.generatePassword();
            TestDataHolder.setEmail(email);
            TestDataHolder.setPassword(password);
            
            registerPage.fillAllFields(FakerUtil.generateFirstName(), FakerUtil.generateLastName(), email, password, "Male");
            registerPage.clickRegisterButton();
            registerPage.clickContinueButton();
            
            // Re-check just in case registration doesn't auto-login
            if (homePage.isLoginLinkVisible()) {
                homePage.clickLoginLink();
                LoginPage loginPage = new LoginPage(driver);
                loginPage.loginWithValidCredentials(email, password);
            }
        } else {
            // Found existing shared user, let's login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginWithValidCredentials(email, password);
        }
    }

    protected void ensureLoggedIn() {
        if (!isUserLoggedIn()) {
            loginWithRegisteredUser();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        LoggerUtil.info("Initializing Test Suite...");
    }

    /**
     * Initializes the WebDriver before the test class execution.
     * Selects the browser based on TestNG parameter or properties file.
     *
     * @param browserParam Optional browser parameter from TestNG xml.
     */
    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setUpClass(@Optional("") String browserParam) {
        String browser = (browserParam != null && !browserParam.isEmpty()) ? browserParam : ConfigReader.getBrowser();
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }
        baseURL = ConfigReader.getBaseURL();
        LoggerUtil.info("Starting test class with browser: " + browser);
        driver = BrowserUtil.launchBrowser(browser);
    }

    /**
     * Prepares the driver before each test method execution.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver.get(baseURL);
        LoggerUtil.info("Navigated to URL: " + baseURL);
    }

    /**
     * Cleans up after each test execution.
     * Captures screenshot if the test fails. Clears cookies for the next test.
     *
     * @param result ITestResult containing the status and details of the executed test.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            LoggerUtil.error("Test failed: " + result.getName());
            ScreenshotUtil.takeScreenshot(driver, result.getName() + "_FAILURE");
        } else if (ITestResult.SUCCESS == result.getStatus()) {
            LoggerUtil.info("Test passed: " + result.getName());
        } else {
            LoggerUtil.info("Test skipped: " + result.getName());
        }

        if (driver != null) {
            driver.manage().deleteAllCookies();
            LoggerUtil.info("Cleared browser cookies after test: " + result.getName());
        }
    }

    /**
     * Quits the WebDriver after the test class execution.
     */
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (driver != null) {
            LoggerUtil.info("Closing browser.");
            driver.quit();
        }
    }
}
