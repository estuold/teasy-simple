package com.wiley.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Provides the logic to find element
 */
public interface Locator {

    WebElement find();

    By getBy();
}
