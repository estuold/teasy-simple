package com.wiley.elements.conditions;

import com.wiley.elements.TeasyElement;
import com.wiley.elements.TeasyExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

/**
 * Created by vefimov on 25/05/2017.
 */
public class FirstFoundInContext implements ElementCondition {

    private TeasyElement context;

    public FirstFoundInContext(TeasyElement context) {
        this.context = context;
    }

    @Override
    public Function<WebDriver, List<WebElement>> visibilities(By locator) {
        return TeasyExpectedConditions.visibilityOfFirstElements(context, locator);
    }

    @Override
    public Function<WebDriver, WebElement> visibility(By locator) {
        return TeasyExpectedConditions.visibilityOfElementLocatedBy(context, locator);
    }

    @Override
    public Function<WebDriver, List<WebElement>> presences(By locator) {
        return TeasyExpectedConditions.presenceOfAllElementsLocatedBy(context, locator);
    }

    @Override
    public Function<WebDriver, WebElement> presence(By locator) {
        return TeasyExpectedConditions.presenceOfElementLocatedBy(context, locator);
    }
}
