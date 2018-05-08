package com.wiley.config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Configuration for a test run
 */
public final class Configuration {

    private Configuration() {
    }

    public static final String PLATFORM = "windows";

    public static final String BROWSER = "chrome";

    public static final String GRID_HUB_URL = "http://localhost:4444/wd/hub";

    public static final boolean RUN_WITH_GRID = false;

    public static final boolean HEADLESS = false;

    public static final int TIMEOUT = 10;

    public static final DesiredCapabilities CUSTOM_CAPS = new DesiredCapabilities();

    //null means that default element factory will be used
    public static final String ELEMENT_FACTORY_CLASS = null;
}
