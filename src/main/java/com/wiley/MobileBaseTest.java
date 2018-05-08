package com.wiley;

import com.wiley.holders.AssertionsHolder;
import com.wiley.provider.PageProvider;
import org.testng.ITestResult;

import static com.wiley.driver.WebDriverFactory.initDriver;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 14:57
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
