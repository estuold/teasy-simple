package com.wiley.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Find element in list by index
 */
public class FindElementsLocator implements Locator {

    private TeasyElement searchContext;
    private WebDriver driver;
    private By by;
    private int index;

    public FindElementsLocator(TeasyElement searchContext, By by, int index) {
        this.searchContext = searchContext;
        this.by = by;
        this.index = index;
    }

    public FindElementsLocator(WebDriver driver, By by, int index) {
        this.driver = driver;
        this.by = by;
        this.index = index;
    }

    @Override
    public WebElement find() {
        try {
            return driver != null ? driver.findElements(by).get(index) : searchContext.domElements(by).get(index).getWrappedWebElement();
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Unable to find element with locator " + by + " and index " + index + ", Exception - " + e);
        }
    }

    @Override
    public By getBy() {
        return by;
    }
}
