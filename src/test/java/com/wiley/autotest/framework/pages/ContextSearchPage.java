package com.wiley.autotest.framework.pages;

import com.wiley.BasePage;
import com.wiley.elements.TeasyElement;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.List;

@Component
public class ContextSearchPage extends BasePage {

    public ContextSearchPage checkContextSearch() {
        List<TeasyElement> elements = element(By.cssSelector(".test")).elements(By.cssSelector(".contextTest"));
        Assert.assertEquals(elements.size(), 1);
        elements.get(0).should().haveText("Hello context");
        return this;
    }
}
