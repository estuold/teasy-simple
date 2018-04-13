package com.wiley.utils;

/**
 * User: ntyukavkin
 * Date: 13.04.2018
 * Time: 11:53
 */
public class TestUtils {

    private TestUtils() {
    }

    public static void waitForSomeTime(long timeInMills) {
        waitForSomeTime(timeInMills, "wait some time");
    }

    public static void waitForSomeTime(long timeInMills, String expain) {
        try {
            Thread.sleep(timeInMills);
        } catch (InterruptedException e) {
        }
    }
}
