package com.wiley.page;

import com.wiley.assertions.SoftAssert;
import com.wiley.elements.*;
import com.wiley.elements.waitfor.CustomWaitFor;
import com.wiley.holders.AssertionsHolder;
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
    private TeasyElementProvider teasyElementProvider;

    public final void open(final String url) {
        if (!url.isEmpty()) {
            driver.get(url);
        }
    }

    public void init(WebDriver driver) {
        this.driver = driver;
        this.teasyElementProvider = new TeasyElementProvider();
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
        return teasyElementProvider.waitFor();
    }

    public CustomWaitFor waitFor(SearchStrategy strategy) {
        return teasyElementProvider.waitFor(strategy);
    }

    public TeasyElement element(final By locator) {
        return teasyElementProvider.element(locator);
    }

    public TeasyElement element(final By locator, SearchStrategy strategy) {
        return teasyElementProvider.element(locator, strategy);
    }

    public TeasyElementList elements(final By locator) {
        return teasyElementProvider.elements(locator);
    }

    public TeasyElementList elements(final By locator, SearchStrategy strategy) {
        return teasyElementProvider.elements(locator, strategy);
    }

    public TeasyElement domElement(By locator) {
        return teasyElementProvider.domElement(locator);
    }

    public TeasyElement domElement(By locator, SearchStrategy strategy) {
        return teasyElementProvider.domElement(locator, strategy);
    }

    public TeasyElementList domElements(By locator) {
        return teasyElementProvider.domElements(locator);
    }

    public TeasyElementList domElements(By locator, SearchStrategy strategy) {
        return teasyElementProvider.domElements(locator, strategy);
    }

    public Alert alert() {
        return teasyElementProvider.alert();
    }

    public Alert alert(SearchStrategy strategy) {
        return teasyElementProvider.alert(strategy);
    }

    public Window window() {
        return teasyElementProvider.window();
    }
}
