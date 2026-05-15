package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for user's Reward Points.
 */
public class RewardPointsPage extends BasePage {

    private By emptyStateMessage = By.xpath("//p[contains(text(), 'You do not have any reward points!')]");
    private By totalPointsLocator = By.xpath("//td[contains(text(), 'Total')]/following-sibling::td");
    private By continueBtn = By.xpath("//a[contains(text(), 'Continue')]");

    public RewardPointsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks if there's no reward points.
     * @return true if user has 0 points message displayed.
     */
    public boolean hasNoRewardPoints() {
        return isElementDisplayed(emptyStateMessage);
    }

    /**
     * Retrieves the total points if displayed in the bottom row.
     * @return Points as integer or 0 if not visible.
     */
    public int getTotalRewardPoints() {
        if(isElementDisplayed(totalPointsLocator)) {
            String text = getText(totalPointsLocator);
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Clicks "Continue" to go back to My Account.
     */
    public void clickContinue() {
        click(continueBtn);
    }
}
