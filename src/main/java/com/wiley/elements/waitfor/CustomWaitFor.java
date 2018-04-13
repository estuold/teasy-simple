package com.wiley.elements.waitfor;

import com.wiley.elements.SearchStrategy;
import com.wiley.elements.TeasyFluentWait;

import java.util.function.Function;

/**
 * Waiter for a custom condition represented by a function
 */
public class CustomWaitFor {

    public <T> void condition(Function<? super T, ?> condition, T input) {
        new TeasyFluentWait<>(input).waitFor(condition);
    }

    public <T> void condition(Function<? super T, ?> condition, T input, SearchStrategy strategy) {
        new TeasyFluentWait<>(input, strategy).waitFor(condition);
    }

}
