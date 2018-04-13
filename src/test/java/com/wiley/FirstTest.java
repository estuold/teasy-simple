package com.wiley;

import com.wiley.pages.PageOne;
import org.testng.annotations.Test;

/**
 * User: ntyukavkin
 * Date: 10.04.2018
 * Time: 15:01
 */
public class FirstTest extends BaseTest {

    private int count = 1;

    @Test(invocationCount = 20)
    public void test() {
        get(PageOne.class, "http://ya.ru")
                .inputQuery(String.valueOf(count))
                .clickSearchButton()
                .checkResultsArePresent();

        count++;
    }
}
