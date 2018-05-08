package com.wiley.basetests;

import com.wiley.holders.AssertionsHolder;
import com.wiley.page.BasePage;
import com.wiley.page.PageProvider;
import org.testng.ITestResult;

import static com.wiley.driver.WebDriverFactory.initDriver;

/**
 * Base test for Appium tests
 */
public class MobileBaseTest extends BaseTest {

    protected <T extends BasePage> T openApp(Class<T> page) {
        initDriver();
        return PageProvider.get(page);
    }

    protected void setThrowable(ITestResult testResult, String methodType) {
        final Throwable testResultThrowable = testResult.getThrowable();
        String message = testResultThrowable.getMessage() != null
                ? testResultThrowable.getMessage()
                : testResultThrowable.getCause().getMessage();

        if (message == null) {
            message = methodType + " failed";
        }

        AssertionsHolder.softAssert().addWithScreenshot(message);
    }
}
