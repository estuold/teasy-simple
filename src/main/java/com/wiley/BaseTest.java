package com.wiley;

import com.wiley.assertions.SoftAssert;
import com.wiley.holders.AssertionsHolder;
import com.wiley.holders.TestParamsHolder;
import org.testng.*;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 14:57
 */
public abstract class BaseTest implements IConfigurable, IHookable {

    private static final Object SYNC = new Object();

    @Override
    public void run(IConfigureCallBack callBack, ITestResult testResult) {
        synchronized (SYNC) {
            AssertionsHolder.setSoftAssert(new SoftAssert());

            TestParamsHolder.setTestName(testResult.getName());
        }

        callBack.runConfigurationMethod(testResult);

        synchronized (SYNC) {
            if (testResult.getThrowable() != null) {
                setThrowable(testResult, "Configuration method");
            }
        }
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        synchronized (SYNC) {
            if (AssertionsHolder.softAssert() == null
                    || (TestParamsHolder.getTestName() != null && TestParamsHolder.getTestName().equals(testResult.getName()))) {
                AssertionsHolder.setSoftAssert(new SoftAssert());
            }

            TestParamsHolder.setTestName(testResult.getName());
        }

        callBack.runTestMethod(testResult);

        synchronized (SYNC) {
            if (testResult.getThrowable() != null) {
                setThrowable(testResult, "Test");

                AssertionsHolder.softAssert().assertAll();
            }
        }
    }

    protected abstract void setThrowable(ITestResult testResult, String methodType);
}
