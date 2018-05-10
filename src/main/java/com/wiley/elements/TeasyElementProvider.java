package com.wiley.elements;

import com.wiley.elements.waitfor.CustomWaitFor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import static com.wiley.holders.DriverHolder.getDriver;

/**
 * Entry point to search for all elements on the screen
 */
public class TeasyElementProvider {

    private TeasyElementFinder finder;

    private TeasyElementFinder customFinder(SearchStrategy strategy) {
        return new TeasyElementFinder(getDriver(), strategy);
    }

    private TeasyElementFinder finder() {
        if (finder == null) {
            finder = new TeasyElementFinder(getDriver(), new SearchStrategy());
        }
        return finder;
    }

    public CustomWaitFor waitFor() {
        return new CustomWaitFor();
    }

    public CustomWaitFor waitFor(SearchStrategy strategy) {
        return new CustomWaitFor(strategy);
    }

    public TeasyElement element(final By locator) {
        return finder().visibleElement(locator);
    }

    public TeasyElement element(final By locator, SearchStrategy strategy) {
        return customFinder(strategy).visibleElement(locator);
    }

    public TeasyElementList elements(final By locator) {
        return new TeasyElementList(finder().visibleElements(locator), locator);
    }

    public TeasyElementList elements(final By locator, SearchStrategy strategy) {
        return new TeasyElementList(customFinder(strategy).visibleElements(locator), locator);
    }

    public TeasyElement domElement(By locator) {
        return finder().presentInDomElement(locator);
    }

    public TeasyElement domElement(By locator, SearchStrategy strategy) {
        return customFinder(strategy).presentInDomElement(locator);
    }

    public TeasyElementList domElements(By locator) {
        return new TeasyElementList(finder().presentInDomElements(locator), locator);
    }

    public TeasyElementList domElements(By locator, SearchStrategy strategy) {
        return new TeasyElementList(customFinder(strategy).presentInDomElements(locator), locator);
    }

    public Alert alert() {
        return finder().alert();
    }

    public Alert alert(SearchStrategy strategy) {
        return customFinder(strategy).alert();
    }

    public Window window() {
        return new TeasyWindow(getDriver());
    }
}

