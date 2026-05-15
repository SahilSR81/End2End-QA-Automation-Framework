package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object representing the user's downloadable products.
 */
public class DownloadableProductsPage extends BasePage {

    private By emptyStateMessage = By.xpath("//p[contains(text(), 'You have not made any previous downloadable orders!')]");
    private By downloadBtn = By.cssSelector("a[data-original-title='Download']");
    private By continueBtn = By.xpath("//a[contains(text(), 'Continue')]");

    public DownloadableProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks if there are any downloadable products listed.
     * @return true if empty state message is shown.
     */
    public boolean hasNoDownloads() {
        return isElementDisplayed(emptyStateMessage);
    }

    /**
     * Downloads the first available file, simulating a click on the download button.
     */
    public void downloadFirstItem() {
        if(!hasNoDownloads() && isElementDisplayed(downloadBtn)) {
            driver.findElements(downloadBtn).get(0).click();
        }
    }

    /**
     * Clicks "Continue" to return to Account hub.
     */
    public void clickContinue() {
        click(continueBtn);
    }
}
