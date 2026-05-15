package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By logo = By.cssSelector(".header-logo a, img[alt='Tricentis Demo Web Shop']");
    private By registerLink = By.cssSelector(".ico-register");
    private By loginLink = By.cssSelector(".ico-login");
    private By cartLink = By.cssSelector(".ico-cart");
    private By searchInput = By.id("small-searchterms");
    private By searchButton = By.cssSelector("input.search-box-button");
    private By logoutLink = By.cssSelector(".ico-logout");
    private By myAccountLink = By.cssSelector(".account");

    
    // Newsletter locators
    private By newsletterEmailInput = By.id("newsletter-email");
    private By newsletterSubscribeButton = By.id("newsletter-subscribe-button");
    private By newsletterResultBlock = By.id("newsletter-result-block");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates back to the home page by clicking the site logo.
     */
    public void navigateToHome() {
        // Navigates to base URL derived from current or simple click on logo
        if(isElementDisplayed(logo)) {
            click(logo);
        }
    }

    /**
     * Executes a product search using the top search bar.
     * @param productName The name of the product to search.
     */
    public void searchProduct(String productName) {
        sendKeys(searchInput, productName);
        click(searchButton);
    }

    /**
     * Navigates to a specific category from the top menu.
     * @param category The exact link text of the category.
     */
    public void selectCategory(String category) {
        click(By.linkText(category));
    }

    /**
     * Clicks on a specific product link by its name.
     * @param productName The name of the product as displayed.
     */
    public void clickOnProductByName(String productName) {
        click(By.linkText(productName));
    }

    /**
     * Clicks the login link in the header.
     */
    public void clickLoginLink() {
        click(loginLink);
    }

    /**
     * Clicks the register link in the header.
     */
    public void clickRegisterLink() {
        click(registerLink);
    }

    /**
     * Navigates to the shopping cart page.
     */
    public void viewCart() {
        click(cartLink);
    }

    /**
     * Logs the current user out if the logout link is visible.
     */
    public void logout() {
        if(isElementDisplayed(logoutLink)) {
            click(logoutLink);
        }
    }
    
    /**
     * Navigates to the My Account page.
     */
    public void clickMyAccount() {
        click(myAccountLink);
    }

    /**
     * Fetches the current page title.
     * @return The title of the page as a String.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Checks if the home page is currently displayed.
     * @return boolean True if the home page elements are visible.
     */
    public boolean isHomePageDisplayed() {
        return isElementDisplayed(logo) || isElementDisplayed(searchInput);
    }
    
    /**
     * Checks if the login link is visible (indicating the user is not logged in).
     * @return boolean True if login link is displayed.
     */
    public boolean isLoginLinkVisible() {
        return driver.findElements(loginLink).size() > 0 && driver.findElement(loginLink).isDisplayed();
    }

    /**
     * Subscribes to the newsletter.
     * @param email The email to subscribe with.
     */
    public void subscribeToNewsletter(String email) {
        sendKeys(newsletterEmailInput, email);
        click(newsletterSubscribeButton);
    }

    /**
     * Gets the newsletter subscription result message.
     * @return The text of the result block.
     */
    public String getNewsletterResultMessage() {
        if (isElementDisplayed(newsletterResultBlock)) {
            return getText(newsletterResultBlock);
        }
        return "";
    }

    /**
     * Waits for the login to complete by verifying logout link visibility or login link invisibility.
     */
    public void waitForLoginCompletion() {
        int maxRetries = 10;
        int retries = 0;
        while (retries < maxRetries) {
            try {
                Thread.sleep(500);
                
                // Check if logout link is visible (user is logged in)
                if (driver.findElements(logoutLink).size() > 0 && driver.findElement(logoutLink).isDisplayed()) {
                    return; // Login successful
                }
                
                // Check if my account link is visible (alternative indicator)
                if (driver.findElements(myAccountLink).size() > 0 && driver.findElement(myAccountLink).isDisplayed()) {
                    return; // Login successful
                }
                
                retries++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                retries++;
            }
        }
    }
}
