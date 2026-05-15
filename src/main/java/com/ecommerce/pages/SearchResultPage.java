package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.ArrayList;

/**
 * Page object representing a search result listing page.
 */
public class SearchResultPage extends BasePage {

    private By searchCriteriaInput = By.cssSelector(".search-text, #Q");
    private By searchButton = By.cssSelector("input.button-1.search-button");
    private By searchResultsTitles = By.cssSelector(".product-title a, h2.product-title a");
    private By noResultsMessage = By.cssSelector("strong.result");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Conducts a granular search within the results page.
     * @param keyword The keyword to search.
     */
    public void searchAgain(String keyword) {
        sendKeys(searchCriteriaInput, keyword);
        click(searchButton);
    }

    /**
     * Gets a list of product titles returned by the search.
     * @return List of string titles.
     */
    public List<String> getResultTitles() {
        List<String> titles = new ArrayList<>();
        if (driver.findElements(noResultsMessage).size() > 0 && isElementDisplayed(noResultsMessage)) {
            if (getText(noResultsMessage).contains("No products were found")) {
                return titles;
            }
        }
        
        try {
            waitForElementVisibility(searchResultsTitles, 10);
            List<org.openqa.selenium.WebElement> elements = driver.findElements(searchResultsTitles);
            for(org.openqa.selenium.WebElement e : elements) {
                titles.add(e.getText().trim());
            }
        } catch(Exception e) {}
        
        return titles;
    }

    /**
     * Clicks on the first search result.
     */
    public void clickFirstResult() {
        try {
            waitForElementVisibility(searchResultsTitles, 10);
            if(driver.findElements(searchResultsTitles).size() > 0) {
                click(searchResultsTitles);
            }
        } catch(Exception e) {}
    }
}
