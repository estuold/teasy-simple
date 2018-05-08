package com.wiley.autotest.framework.config;

import com.wiley.page.BasePage;
import com.wiley.basetests.SeleniumBaseTest;
import com.wiley.exceptions.StopTestExecutionException;
import com.wiley.holders.AssertionsHolder;
import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestResult;

/**
 * User: ntyukavkin
 * Date: 19.05.2017
 * Time: 14:17
 */
public class BaseUnitTest extends SeleniumBaseTest {

    public <E extends BasePage> E openPage(String fileName, Class<E> page) {
        return get(page, "file://" + getClass().getResource("/html/framework/").getPath() + fileName);
    }

    protected void setThrowable(ITestResult testResult, String methodType) {
        final Throwable testResultThrowable = testResult.getThrowable();
        String message = testResultThrowable.getMessage() != null ? testResultThrowable.getMessage() : testResultThrowable.getCause().getMessage();

        if (message == null) {
            message = methodType + " failed";
        }

        if (!(testResult.getThrowable().getCause() instanceof StopTestExecutionException)
                && !(testResult.getThrowable().getCause() instanceof NoSuchElementException)) {
            AssertionsHolder.softAssert().addWithScreenshot(message);
        }
    }
}
