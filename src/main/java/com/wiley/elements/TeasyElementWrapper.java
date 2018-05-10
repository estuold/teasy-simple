package com.wiley.elements;

import com.wiley.config.Configuration;
import com.wiley.utils.Report;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrap element with TeasyElement
 */
public class TeasyElementWrapper {

    public static <T extends TeasyElement> T wrap(WebElement webElement, By by, TeasyElementType type) {
        return wrapBase(new TeasyElementData(webElement, by), type);
    }

    public static <T extends TeasyElement> T wrap(TeasyElement searchContext, WebElement webElement, By by, TeasyElementType type) {
        return wrapBase(new TeasyElementData(searchContext, webElement, by), type);
    }

    public static <T extends TeasyElement> T wrap(WebElement webElement, By by, int index, TeasyElementType type) {
        return wrapBase(new TeasyElementData(webElement, by, index), type);
    }

    public static <T extends TeasyElement> T wrap(TeasyElement searchContext, WebElement webElement, By by, int index, TeasyElementType type) {
        return wrapBase(new TeasyElementData(searchContext, webElement, by, index), type);
    }

    public static <T extends TeasyElement> List<T> wrapList(List<WebElement> webElementList, By by, TeasyElementType type) {
        List<T> list = new ArrayList<>(webElementList.size());
        for (int i = 0; i < webElementList.size(); i++) {
            list.add(wrap(webElementList.get(i), by, i, type));
        }
        return list;
    }

    public static <T extends TeasyElement> List<T> wrapList(TeasyElement searchContext, List<WebElement> webElementList, By by, TeasyElementType type) {
        List<T> list = new ArrayList<>(webElementList.size());
        for (int i = 0; i < webElementList.size(); i++) {
            list.add(wrap(searchContext, webElementList.get(i), by, i, type));
        }
        return list;
    }

    private static <T extends TeasyElement> T wrapBase(TeasyElementData data, TeasyElementType type) {
        try {
            TeasyElementFactory elementFactory;
            if (Configuration.elementFactoryClass != null) {
                elementFactory = (TeasyElementFactory) Class.forName(Configuration.elementFactoryClass)
                        .getDeclaredConstructor(TeasyElementData.class).newInstance(data);
            } else {
                elementFactory = new DefaultTeasyElementFactory(data);
            }

            switch (type) {
                case VISIBLE: {
                    TeasyElement visibleElement = elementFactory.getVisibleElement() != null
                            ? elementFactory.getVisibleElement()
                            : new DefaultTeasyElementFactory(data).getVisibleElement();
                    return (T) visibleElement;
                }
                case DOM: {
                    TeasyElement domElement = elementFactory.getDomElement() != null
                            ? elementFactory.getDomElement()
                            : new DefaultTeasyElementFactory(data).getDomElement();
                    return (T) domElement;
                }
                case NULL: {
                    TeasyElement nullElement = elementFactory.getNullElement() != null
                            ? elementFactory.getNullElement()
                            : new DefaultTeasyElementFactory(data).getNullElement();
                    return (T) nullElement;
                }
                default: {
                    throw new ClassNotFoundException("Cannot create instance of TeasyElement for type '" + type + "'");
                }
            }
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Report.jenkins("Cannot create instance of TeasyElement." + e.getClass().getName(), e);
            throw new WrapElementException("Cannot create instance of TeasyElement. " + e.getClass().getName() + " occurred. ", e);
        }
    }
}
