package com.wiley.screenshots;

import com.wiley.elements.TeasyElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.coordinates.JqueryCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wiley.utils.ExecutionUtils.isChrome;
import static com.wiley.utils.JsActions.executeScript;
import static ru.yandex.qatools.ashot.cropper.indent.IndentFilerFactory.monochrome;

/**
 * A Screenshot class based on a {@link AShot} screenshoter
 * Provides an opportunity to capture screenshot by the following options:
 * 1) Exclude and include locators
 * 2) Exclude locators
 * 3) Include locators
 * 4) Full page
 */
public class TeasyScreenshot {

    private final WebDriver driver;
    private final CoordsProvider coordsProvider;

    /**
     * Create TeasyScreenshot with default coordinates provider set to {@link JqueryCoordsProvider}
     *
     * @param driver see {@link WebDriver}
     */
    public TeasyScreenshot(WebDriver driver) {
        this.driver = driver;
        this.coordsProvider = new JqueryCoordsProvider();
    }

    /**
     * Create TeasyScreenshot with any type of coordinates provider
     *
     * @param driver         see {@link WebDriver}
     * @param coordsProvider see {@link CoordsProvider}
     */
    public TeasyScreenshot(WebDriver driver, CoordsProvider coordsProvider) {
        this.driver = driver;
        this.coordsProvider = coordsProvider;
    }

    public Screenshot excludeAndInclude(List<TeasyElement> excludeElements, List<TeasyElement> includeElements) {
        AShot aShot = new AShotChromeDecorator(coordsProvider);
        addIgnoredAreas(excludeElements, aShot);
        addMonochromeIndentFilter(aShot);
        return aShot.takeScreenshot(driver, asWebElements(includeElements));
    }

    public Screenshot exclude(List<TeasyElement> elements) {
        AShot aShot = new AShotChromeDecorator(coordsProvider);
        addIgnoredAreas(elements, aShot);
        return aShot.takeScreenshot(driver);
    }

    public Screenshot include(List<TeasyElement> elements) {
        AShot aShot = new AShotChromeDecorator(coordsProvider);
        addMonochromeIndentFilter(aShot);
        return aShot.takeScreenshot(driver, asWebElements(elements));
    }

    public Screenshot fullPage() {
        AShot aShot = new AShotChromeDecorator(coordsProvider);
        return aShot.takeScreenshot(driver);
    }

    private void addMonochromeIndentFilter(AShot aShot) {
        aShot.imageCropper(new IndentCropper().addIndentFilter(monochrome()));
    }

    private void addIgnoredAreas(List<TeasyElement> excludeElements, AShot aShot) {
        for (TeasyElement element : excludeElements) {
            Point location = element.getLocation();
            Dimension size = element.getSize();
            aShot.addIgnoredArea(new Coords(location.getX(), location.getY(), size.getWidth(), size.getHeight()));
        }
    }

    private List<WebElement> asWebElements(List<TeasyElement> elements) {
        List<WebElement> result = new ArrayList<>();
        elements.forEach(element -> result.add(element.getWrappedWebElement()));
        return result;
    }

    /**
     * Decorator for AShot which adds the following logic for Chrome
     * 1. before taking screenshot we hide scrollbar and set viewport pasting strategy with defined scroll timeout
     * <p>
     * 2. after taking screenshot we set the page to default state returning the scrollbar
     */
    private class AShotChromeDecorator extends AShot {
        private final boolean isChrome;

        AShotChromeDecorator(CoordsProvider coordsProvider) {
            super();
            super.coordsProvider(coordsProvider);
            this.isChrome = isChrome();
        }

        @Override
        public Screenshot takeScreenshot(WebDriver driver, Collection<WebElement> elements) {
            if (isChrome) {
                hideScrollbar();
                super.shootingStrategy(ShootingStrategies.viewportPasting(100));
            }
            try {
                return super.takeScreenshot(driver, elements);
            } finally {
                if (isChrome) {
                    revealScrollbar();
                }
            }
        }

        @Override
        public Screenshot takeScreenshot(WebDriver driver) {
            if (isChrome) {
                hideScrollbar();
                super.shootingStrategy(ShootingStrategies.viewportPasting(100));
            }
            try {
                return super.takeScreenshot(driver);
            } finally {
                if (isChrome) {
                    revealScrollbar();
                }
            }
        }

        private void hideScrollbar() {
            executeScript("document.body.style.overflow = 'hidden';");
        }

        private void revealScrollbar() {
            executeScript("document.body.style.overflow = 'visible';");
        }
    }
}
