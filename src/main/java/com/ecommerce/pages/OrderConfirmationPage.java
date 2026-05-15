package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {
    private By confirmationMessage = By.cssSelector(".title strong");
    private By orderDetailsLink = By.cssSelector("a[href*='/orderdetails/']");
    private By continueButton = By.cssSelector(".order-completed-continue-button");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getOrderNumber() {
        if(isElementDisplayed(orderDetailsLink)) {
            String href = driver.findElement(orderDetailsLink).getAttribute("href");
            return href.substring(href.lastIndexOf("/") + 1);
        }
        return "";
    }

    public String getConfirmationMessage() {
        return getText(confirmationMessage);
    }

    public boolean isConfirmationPageDisplayed() {
        try {
            waitForElementVisibility(confirmationMessage, 15);
            return isElementDisplayed(confirmationMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickContinueButton() {
        if(driver.findElements(continueButton).size() > 0) {
            click(continueButton);
        }
    }
}
