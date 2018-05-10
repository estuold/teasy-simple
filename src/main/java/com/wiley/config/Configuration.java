package com.wiley.config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Configuration for a test run
 */
public final class Configuration {

    private Configuration() {
    }

    public static String platform = "windows";

    public static String browser = "chrome";

    public static String gridHubUrl = "http://localhost:4444/wd/hub";

    public static boolean runWithGrid = false;

    public static boolean headless = false;

    public static int timeout = 10;

    public static DesiredCapabilities customCaps = new DesiredCapabilities();
    //null means that default element factory will be used

    public static String elementFactoryClass = null;
}
