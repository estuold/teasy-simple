package com.wiley.assertions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TeasyError {

    private final Throwable throwable;
    private final MethodType methodType;

    TeasyError(Throwable throwable, MethodType methodType) {
        this.methodType = methodType;
        this.throwable = throwable;
    }

    public String getErrorMessage() {
        String message = throwable.getMessage() != null ? throwable.getMessage() : throwable.getCause().getMessage();

        if (message == null) {
            message = methodType.desc() + " failed";
        }
        return message;
    }

    public List<String> getStackTrace() {
        List<String> s = new ArrayList<>();
        if (throwable instanceof InvocationTargetException) {
            StackTraceElement[] stackTrace = ((InvocationTargetException) throwable).getTargetException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                s.add(stackTraceElement.toString());
            }
        } else {

        }

        return s;
    }
}
