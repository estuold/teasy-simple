package com.wiley.elements.custom;

public interface Inputable {

    void type(String text);

    void clear();

    void typeAnClear(String text);

    String getValue();
}
