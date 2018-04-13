package com.wiley.pages;

import com.wiley.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 15:54
 */
public class PageTwo extends BasePage {

    @FindBy(css = ".g")
    private List<WebElement> results;

    public PageTwo checkResultsArePresent() {
        softAssert().assertFalse(elements(By.cssSelector(".g")).isEmpty(), "List is empty");
        return this;
    }

    public PageTwo inputQuery(String query) {
        WebElement input = element(By.cssSelector("input[name='q']"));
        input.clear();
        input.sendKeys(query);
        input.sendKeys(Keys.TAB);
        return this;
    }

    public PageTwo clickSearchButton() {
        element(By.cssSelector("input[name='q']")).sendKeys(Keys.ENTER);
        return this;
    }
}
