package com.wiley.elements;

import com.wiley.elements.types.DomTeasyElement;
import com.wiley.elements.types.NullTeasyElement;
import com.wiley.elements.types.VisibleTeasyElement;

/**
 * Abstract factory to create different types of Teasy Element
 */
public abstract class TeasyElementFactory {

    protected TeasyElementData elementData;

    public TeasyElementFactory(TeasyElementData elementData) {
        this.elementData = elementData;
    }

    public abstract VisibleTeasyElement getVisibleElement();

    public abstract DomTeasyElement getDomElement();

    public abstract NullTeasyElement getNullElement();
}
