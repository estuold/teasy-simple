package com.wiley.driver.factory;

import com.wiley.config.Configuration;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class TeasyDriver {

    public TeasyDriver() {
    }

    public WebDriver init() {
        DriverFactory driverFactory;
        URL gridUrl = getGridHubUrl();
        boolean isHeadless = Configuration.HEADLESS;
        if (Configuration.RUN_WITH_GRID) {
            driverFactory = new RemoteDriverFactory(Configuration.BROWSER, Configuration.PLATFORM, Configuration.CUSTOM_CAPS, isHeadless, gridUrl);
        } else {
            driverFactory = new StandaloneDriverFactory(Configuration.BROWSER, Configuration.PLATFORM, Configuration.CUSTOM_CAPS, isHeadless, gridUrl);
        }

        return driverFactory.get();
    }

    private URL getGridHubUrl() {
        URL url;
        try {
            url = new URL(Configuration.GRID_HUB_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error during gridhuburl creation. For url " + Configuration.GRID_HUB_URL);
        }
        return url;
    }
}
