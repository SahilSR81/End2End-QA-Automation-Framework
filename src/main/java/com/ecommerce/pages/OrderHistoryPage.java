package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class OrderHistoryPage extends BasePage {
    private By orderItem = By.cssSelector(".order-item");
    // private By detailsButton = By.cssSelector(".order-details-button");
    private By reorderButton = By.cssSelector(".re-order-button");

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    public int getTotalOrdersCount() {
        return driver.findElements(orderItem).size();
    }

    public List<String> getAllOrderNumbers() {
        List<String> orderNumbers = new ArrayList<>();
        List<WebElement> items = driver.findElements(By.cssSelector(".order-item .title strong"));
        for (WebElement item : items) {
            String text = item.getText();
            orderNumbers.add(text.replaceAll("[^\\d]", ""));
        }
        return orderNumbers;
    }

    public String getOrderStatus(String orderNumber) {
        try {
            return driver.findElement(By.xpath("//strong[contains(text(), '" + orderNumber + "')]/../../ul/li[contains(text(), 'Order status')]")).getText().replace("Order status:", "").trim();
        } catch(Exception e) {
            return "Pending";
        }
    }

    public String getOrderDate(String orderNumber) {
        return "";
    }

    public double getOrderTotal(String orderNumber) {
        try {
            String text = driver.findElement(By.xpath("//strong[contains(text(), '" + orderNumber + "')]/../../ul/li[contains(text(), 'Order Total')]")).getText().replace("Order Total:", "").trim();
            return Double.parseDouble(text.replaceAll("[^\\d.]", ""));
        } catch(Exception e) {
            return 0.0;
        }
    }

    public void viewOrderDetails(String orderNumber) {
        click(By.cssSelector("input[onclick*='/" + orderNumber + "']"));
    }

    public void reorderProduct(String orderNumber) {
        if(isElementDisplayed(reorderButton)){
            click(reorderButton);
        }
    }

    public boolean isOrderPresentInHistory(String orderNumber) {
        return driver.findElements(By.xpath("//strong[contains(text(), '" + orderNumber + "')]")).size() > 0;
    }

    public List<Map<String, String>> getAllOrdersDetails() {
        return new ArrayList<>();
    }

    public void goToNextPage() { }
    public void goToPreviousPage() { }

    public boolean isOrderHistoryPageDisplayed() {
        return driver.getTitle().contains("Orders") || isElementDisplayed(orderItem);
    }
}
