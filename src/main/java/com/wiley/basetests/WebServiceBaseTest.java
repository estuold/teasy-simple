package com.wiley.basetests;

import com.wiley.holders.AssertionsHolder;
import org.testng.ITestResult;

/**
 * Base test for Rest Assured tests
 */
public class WebServiceBaseTest extends BaseTest {

    protected void setThrowable(ITestResult testResult, String methodType) {
        final Throwable testResultThrowable = testResult.getThrowable();
        String message = testResultThrowable.getMessage() != null
                ? testResultThrowable.getMessage()
                : testResultThrowable.getCause().getMessage();

        if (message == null) {
            message = methodType + " failed";
        }

        AssertionsHolder.softAssert().add(message);
    }
}
