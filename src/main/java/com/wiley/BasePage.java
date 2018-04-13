package com.wiley;

import com.wiley.assertions.SoftAssert;
import com.wiley.elements.*;
import com.wiley.elements.waitfor.CustomWaitFor;
import com.wiley.holders.AssertionsHolder;
import com.wiley.provider.PageProvider;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 14:57
 */
public class BasePage {

    protected WebDriver driver;
    private TeasyElementProvider elementFinder;

    public final void load(final String pathString) {
        if (!pathString.isEmpty()) {
            driver.get(pathString);
        }
    }

    public void init(WebDriver driver) {
        this.driver = driver;
        this.elementFinder = new TeasyElementProvider();
        initFindByAnnotations(this);
        init();
    }

    protected void init() {
    }

    protected void handleRedirect() {
    }

    private <E extends BasePage> void initFindByAnnotations(final E page) {
        PageFactory.initElements(driver, page);
    }

    protected <E extends BasePage> E redirectTo(final Class<E> target) {
        final E page = PageProvider.get(target);
        page.handleRedirect();
        return page;
    }

    protected final <E extends BasePage> E getHelper(final Class<E> target) {
        return PageProvider.get(target);
    }

    protected SoftAssert softAssert() {
        return AssertionsHolder.softAssert();
    }

    public CustomWaitFor waitFor() {
        return elementFinder.waitFor();
    }

    public CustomWaitFor waitFor(SearchStrategy strategy) {
        return elementFinder.waitFor(strategy);
    }

    public TeasyElement element(final By locator) {
        return elementFinder.element(locator);
    }

    public TeasyElement element(final By locator, SearchStrategy strategy) {
        return elementFinder.element(locator, strategy);
    }

    public TeasyElementList elements(final By locator) {
        return elementFinder.elements(locator);
    }

    public TeasyElementList elements(final By locator, SearchStrategy strategy) {
        return elementFinder.elements(locator, strategy);
    }

    public TeasyElement domElement(By locator) {
        return elementFinder.domElement(locator);
    }

    public TeasyElement domElement(By locator, SearchStrategy strategy) {
        return elementFinder.domElement(locator, strategy);
    }

    public TeasyElementList domElements(By locator) {
        return elementFinder.domElements(locator);
    }

    public TeasyElementList domElements(By locator, SearchStrategy strategy) {
        return elementFinder.domElements(locator, strategy);
    }

    public Alert alert() {
        return elementFinder.alert();
    }

    public Alert alert(SearchStrategy strategy) {
        return elementFinder.alert(strategy);
    }

    public Window window() {
        return elementFinder.window();
    }
}
