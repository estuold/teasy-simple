package com.wiley;

import com.wiley.assertions.SoftAssert;
import com.wiley.holders.AssertionsHolder;
import com.wiley.holders.TestParamsHolder;
import com.wiley.provider.PageProvider;
import org.testng.*;

import static com.wiley.driver.WebDriverFactory.initDriver;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 14:57
 */
public class BaseTest implements IConfigurable, IHookable {

    protected <T extends BasePage> T get(Class<T> page) {
        return PageProvider.get(page);
    }

    protected <T extends BasePage> T get(Class<T> page, String url) {
        initDriver();
        return PageProvider.get(page, url);
    }

    @Override
    public void run(IConfigureCallBack callBack, ITestResult testResult) {
        TestParamsHolder.setTestName(testResult.getName());
        AssertionsHolder.setSoftAssert(new SoftAssert());

        callBack.runConfigurationMethod(testResult);

        if (testResult.getThrowable() != null) {
            setThrowable(testResult, "Configuration method");
        }
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        TestParamsHolder.setTestName(testResult.getName());
        if (AssertionsHolder.softAssert() == null) {
            AssertionsHolder.setSoftAssert(new SoftAssert());
        }

        callBack.runTestMethod(testResult);

        if (testResult.getThrowable() != null) {
            setThrowable(testResult, "Test");
        }
        AssertionsHolder.softAssert().assertAll();
    }

    private void setThrowable(ITestResult testResult, String methodType) {
        final Throwable testResultThrowable = testResult.getThrowable();
        String message = testResultThrowable.getMessage() != null ? testResultThrowable.getMessage() : testResultThrowable.getCause().getMessage();

        if (message == null) {
            message = methodType + " failed";
        }

        AssertionsHolder.softAssert().add(message);
    }
}
