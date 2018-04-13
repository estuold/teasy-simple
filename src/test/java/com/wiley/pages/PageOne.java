package com.wiley.pages;

import com.wiley.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 15:54
 */
public class PageOne extends BasePage {

    @FindBy(css = ".serp-item")
    private List<WebElement> results;

    public PageOne checkResultsArePresent() {
        softAssert().assertFalse(elements(By.cssSelector(".serp-item")).isEmpty(), "List is empty");
        return this;
    }

    public PageOne inputQuery(String query) {
        WebElement input = element(By.cssSelector(".input__box input"));
        input.clear();
        input.sendKeys(query);
        return this;
    }

    public PageOne clickSearchButton() {
        element(By.cssSelector(".search2__button button")).click();
        return this;
    }
}
