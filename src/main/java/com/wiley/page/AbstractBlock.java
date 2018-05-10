package com.wiley.page;

import com.wiley.elements.*;
import com.wiley.elements.types.NullTeasyElement;
import com.wiley.elements.waitfor.CustomWaitFor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.wiley.holders.DriverHolder.getDriver;

/**
 * Representation of an abstract block of a Page.
 * Basically it is any area of a page located inside of a mainElement.
 */
public abstract class AbstractBlock {

    private final TeasyElement mainElement;
    private TeasyElementFinder finder;

    public AbstractBlock(TeasyElement element) {
        //in case not-found-element is passed it does not make sense to create new block
        mainElement = element;
        if (element instanceof NullTeasyElement) {
            throwException();
        }

        this.finder = new TeasyElementFinder(getDriver(), new SearchStrategy(), mainElement);
    }

    /**
     * Overriding default behavior to make it search only within context of a block
     * <p>
     * do not call this methods directly. it's only needed for inner logic
     */
    private final TeasyElementFinder customFinder(SearchStrategy strategy) {
        return new TeasyElementFinder(getDriver(), strategy, mainElement);
    }

    /**
     * Gets main element for given block
     *
     * @return TeasyElement representing main element
     */
    protected TeasyElement getMainElement() {
        return mainElement;
    }

    public CustomWaitFor waitFor() {
        return new CustomWaitFor();
    }

    public CustomWaitFor waitFor(SearchStrategy strategy) {
        return new CustomWaitFor(strategy);
    }

    public TeasyElement element(final By locator) {
        return finder.visibleElement(locator);
    }

    public TeasyElement element(final By locator, SearchStrategy strategy) {
        return customFinder(strategy).visibleElement(locator);
    }

    public TeasyElementList elements(final By locator) {
        return new TeasyElementList(finder.visibleElements(locator), locator);
    }

    public TeasyElementList elements(final By locator, SearchStrategy strategy) {
        return new TeasyElementList(customFinder(strategy).visibleElements(locator), locator);
    }

    public TeasyElement domElement(By locator) {
        return finder.presentInDomElement(locator);
    }

    public TeasyElement domElement(By locator, SearchStrategy strategy) {
        return customFinder(strategy).presentInDomElement(locator);
    }

    public TeasyElementList domElements(By locator) {
        return new TeasyElementList(finder.presentInDomElements(locator), locator);
    }

    public TeasyElementList domElements(By locator, SearchStrategy strategy) {
        return new TeasyElementList(customFinder(strategy).presentInDomElements(locator), locator);
    }

    public Alert alert() {
        return finder.alert();
    }

    public Alert alert(SearchStrategy strategy) {
        return customFinder(strategy).alert();
    }

    public Window window() {
        return new TeasyWindow(getDriver());
    }

    private void throwException() {
        throw new NoSuchElementException("Failed to create Block. Unable to find main element of a block with locator '" + mainElement.getLocator().getBy() + "'");
    }
}

