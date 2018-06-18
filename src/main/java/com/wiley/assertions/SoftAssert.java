package com.wiley.assertions;

import com.wiley.holders.TestParamsHolder;
import com.wiley.screenshots.Screenshoter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import java.util.LinkedList;
import java.util.List;

/**
 * User: ntyukavkin
 * Date: 11.04.2018
 * Time: 14:39
 */
public class SoftAssert extends Assertion {

    private final List<TeasyError> errors = new LinkedList<>();

    @Override
    protected void doAssert(IAssert<?> assertCommand) {
        onBeforeAssert(assertCommand);
        try {
            assertCommand.doAssert();
            onAssertSuccess(assertCommand);
        } catch (AssertionError error) {
            add(error, MethodType.CONFIG);
            onAssertFailure(assertCommand, error);
        }
    }

    public void addWithScreenshot(Throwable throwable, MethodType methodType) {
        TeasyError teasyError = new TeasyError(throwable, methodType);
        errors.add(teasyError);
        new Screenshoter().takeScreenshot(teasyError.getErrorMessage(), TestParamsHolder.getTestName());
    }

    public void add(Throwable throwable, MethodType methodType) {
        TeasyError teasyError = new TeasyError(throwable, methodType);
        errors.add(teasyError);
    }

    public void assertAll() {
        if (hasErrors()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Next ");
            sb.append(errors.size());
            sb.append(" assert");
            if (errors.size() > 1) {
                sb.append("s");
            }
            sb.append(" failed:");
            for (TeasyError e : errors) {
                sb.append("\n\t");
                sb.append((errors.indexOf(e) + 1));
                sb.append(" fail:");
                sb.append("\n\t");
                sb.append(e.getStackTraceAsString());
                sb.append("\n\t");
                sb.append("####################################################################################################");
                sb.append("\n\t");
                sb.append("####################################################################################################");
                sb.append("\n\t");
                sb.append("####################################################################################################");
                sb.append("\n\t");
            }
            errors.clear();
            throw new AssertionError(sb.toString());
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
