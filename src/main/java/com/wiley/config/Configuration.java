package com.wiley.config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * User: ntyukavkin
 * Date: 13.04.2018
 * Time: 11:42
 */
public class Configuration {

    private Configuration() {
    }

    public static String platform = "windows";

    public static String browser = "chrome";

    public static String gridHubUrl = "http://localhost:4444/wd/hub";

    public static boolean runWithGrid = false;

    public static boolean headless = false;

    public static int timeout = 10;

    public static DesiredCapabilities customCaps = new DesiredCapabilities();

    public static String elementFactoryClass;
}
