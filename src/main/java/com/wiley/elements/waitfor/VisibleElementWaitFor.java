package com.wiley.elements.waitfor;

import com.wiley.elements.SearchStrategy;
import com.wiley.elements.TeasyElement;
import com.wiley.elements.TeasyFluentWait;
import com.wiley.elements.conditions.element.*;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Condition waiter for Visible Element
 */
public class VisibleElementWaitFor implements ElementWaitFor {

    private final TeasyFluentWait<WebDriver> fluentWait;
    private final TeasyElement element;

    public VisibleElementWaitFor(TeasyElement element, TeasyFluentWait<WebDriver> fluentWait) {
        this.element = element;
        this.fluentWait = fluentWait;
    }

    public VisibleElementWaitFor(TeasyElement element, SearchStrategy strategy, TeasyFluentWait<WebDriver> fluentWait) {
        this(element, fluentWait);
        this.fluentWait.withTimeout(Duration.ofSeconds(strategy.getCustomTimeout()));
        this.fluentWait.pollingEvery(Duration.of(strategy.getPoolingEvery(), strategy.getUnit()));
    }

    public void displayed() {
        fluentWait.waitFor(new ElementDisplayed(element));
    }

    public void absent() {
        fluentWait.waitFor(new ElementAbsent(element));
    }

    public void text(String text) {
        fluentWait.waitFor(new ElementHasText(element, text));
    }

    public void attribute(String attributeName, String value) {
        fluentWait.waitFor(new ElementAttributeValue(element, attributeName, value));
    }

    public void attribute(String attributeName) {
        fluentWait.waitFor(new ElementHasAttribute(element, attributeName));
    }

    public void notContainsAttributeValue(String attributeName, String value) {
        fluentWait.waitFor(new ElementAttributeNotContain(element, attributeName, value));
    }

    public void containsAttributeValue(String attributeName, String value) {
        fluentWait.waitFor(new ElementAttributeContain(element, attributeName, value));
    }

    public void stale() {
        fluentWait.waitFor(new ElementStale(element));
    }

    public void clickable() {
        fluentWait.waitFor(new ElementClickable(element));
    }

    public void condition(Function<? super WebDriver, ?> condition) {
        fluentWait.waitFor(condition);
    }

}
