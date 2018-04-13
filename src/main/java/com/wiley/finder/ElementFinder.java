package com.wiley.finder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 16:39
 */
public class ElementFinder {

    private WebDriver driver;
    private long timeout;
    private WebDriverWait wait;

    public ElementFinder(WebDriver driver, long timeout) {
        this.driver = driver;
        this.timeout = timeout;
        this.wait = new WebDriverWait(driver, timeout, 1000);
    }

    public WebElement element(By locator) {
        return waitFor(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> elements(By locator) {
        return waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    private <T> T waitFor(final ExpectedCondition<T> condition) {
        return wait.pollingEvery(1, TimeUnit.SECONDS).until(condition);
    }
}
