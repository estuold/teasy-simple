package com.wiley.holders;

import com.wiley.assertions.SoftAssert;

/**
 * User: ntyukavkin
 * Date: 11.04.2018
 * Time: 15:01
 */
public class AssertionsHolder {

    private static final ThreadLocal<SoftAssert> softAssertHolder = new ThreadLocal<>();

    public static SoftAssert softAssert() {
        return softAssertHolder.get();
    }

    public static void setSoftAssert(SoftAssert softAssert) {
        AssertionsHolder.softAssertHolder.set(softAssert);
    }
}
