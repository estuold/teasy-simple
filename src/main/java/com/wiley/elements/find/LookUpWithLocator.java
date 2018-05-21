package com.wiley.elements.find;

import org.openqa.selenium.By;

public interface LookUpWithLocator<T> {

    T find(By locator);
}
