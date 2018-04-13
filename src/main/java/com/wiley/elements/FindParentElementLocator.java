package com.wiley.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Find parent element
 */
public class FindParentElementLocator implements Locator {

    private SearchContext searchContext;
    private By by;

    public FindParentElementLocator(SearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.by = by;
    }

    @Override
    public WebElement find() {
        return (WebElement) ((JavascriptExecutor) searchContext).executeScript("return arguments[0].parentNode", searchContext.findElement(by));
    }

    @Override
    public By getBy() {
        return by;
    }
}
