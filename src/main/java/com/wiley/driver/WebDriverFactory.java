package com.wiley.driver;

import com.wiley.driver.factory.TeasyDriver;
import com.wiley.driver.frames.FramesTransparentWebDriver;
import com.wiley.driver.frames.WebDriverDecorator;
import com.wiley.holders.DriverHolder;
import com.wiley.holders.TestParamsHolder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import static com.wiley.holders.DriverHolder.getDriver;

/**
 * User: ntyukavkin
 * Date: 12.04.2018
 * Time: 17:22
 */
public class WebDriverFactory {

    private static final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> -1);
    private static final ThreadLocal<Integer> driverRestartCount = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<UnexpectedAlertBehaviour> alertCapability = ThreadLocal.withInitial(() -> UnexpectedAlertBehaviour.ACCEPT);

    public static void initDriver() {
        if (getDriver() == null || isBrowserDead()) {
            try {
                FramesTransparentWebDriver driver = createDriver();
                addShutdownHook(driver);

                setGridParams(driver);
                setDriverParams(driver);
                setMobileParams(driver);
//                setCustomElementFactory();
            } catch (Throwable t) {
                lastTryToCreateDriver(t);
            }
        }
    }

    private static FramesTransparentWebDriver createDriver() {
        TeasyDriver teasyDriver = new TeasyDriver();
        FramesTransparentWebDriver driver = new FramesTransparentWebDriver(teasyDriver.init());
        return driver;
    }

    private static void lastTryToCreateDriver(Throwable t) {
        if (driverRestartCount.get() < 1) {
//            TestUtils.waitForSomeTime(5000, "Wait for retry create driver");
//            new Report("*****Try to wrap driver, count - " + driverRestartCount.get() + " *****").jenkins();
            initDriver();
        } else {
            throw new WebDriverException("*****Unable to wrap driver after " + driverRestartCount.get() + " attempts!***** " + t.getMessage(), t);
        }
    }

//    private void setCustomElementFactory() {
//        if (configuration.getCustomElementFactoryClass() != null) {
//            SeleniumHolder.setCustomElementFactoryClass(configuration.getCustomElementFactoryClass().getName());
//        }
//    }

    private static void setDriverParams(FramesTransparentWebDriver driver) {
        DriverHolder.setDriver(driver);
    }

    private static void setMobileParams(FramesTransparentWebDriver driver) {
        AndroidDriver androidDriver = castToAndroidDriver(driver);
        IOSDriver iosDriver = castToIOSDriver(driver);
        if (androidDriver != null || iosDriver != null) {
            DriverHolder.setAppiumDriver((AppiumDriver) castToWebDriverDecorator(driver));
            DriverHolder.setAndroidDriver(androidDriver);
            DriverHolder.setIOSDriver(iosDriver);
        }
    }

    private static void setGridParams(FramesTransparentWebDriver driver) {
        try {
            SessionId sessionId = ((RemoteWebDriver) driver.getDriver()).getSessionId();
            TestParamsHolder.setSessionId(sessionId);
//            String nodeIp = Configuration.runWithGrid ? getNodeIpBySessionId(sessionId, settings.getGridHubUrl()) : InetAddress.getLocalHost().getHostAddress();
//            TestParamsHolder.setNodeIP(nodeIp);
        } catch (Throwable ignored) {
//            new Report("*****Throwable occurs when set node id*****", ignored).everywhere();
        }
    }

    /**
     * Checks whether browser is dead. Used to catch
     * situations like "Error communicating with the remote browser. It may have died." exceptions
     *
     * @return true if browser is dead
     */
    private static boolean isBrowserDead() {
        try {
            if (((FramesTransparentWebDriver) getDriver()).getWrappedDriver() instanceof AppiumDriver) {
                getDriver().getPageSource();
            } else {
                getDriver().getCurrentUrl();
            }
            return false;
        } catch (Throwable t) {
//            new Report("*****BROWSER IS DEAD ERROR***** ", t).everywhere();
            return true;
        }
    }

    private static void quitWebDriver() {
        count.set(1);
        try {
            getDriver().quit();
        } catch (Throwable t) {
//            new Report("*****ERROR***** TRYING TO QUIT DRIVER ", t).everywhere();
        }
        DriverHolder.setDriver(null);
    }

    private static AndroidDriver castToAndroidDriver(WebDriver driver) {
        WebDriver castToWebDriverDecorator = castToWebDriverDecorator(driver);
        if (castToWebDriverDecorator instanceof AndroidDriver) {
            return (AndroidDriver) castToWebDriverDecorator;
        } else {
            return null;
        }
    }

    private static IOSDriver castToIOSDriver(WebDriver driver) {
        WebDriver castToWebDriverDecorator = castToWebDriverDecorator(driver);
        if (castToWebDriverDecorator instanceof IOSDriver) {
            return (IOSDriver) castToWebDriverDecorator;
        } else {
            return null;
        }
    }

    private static WebDriver castToWebDriverDecorator(WebDriver driver) {
        return ((WebDriverDecorator) driver).getWrappedDriver();
    }

    private static void addShutdownHook(final WebDriver driver) {
        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
    }
}
