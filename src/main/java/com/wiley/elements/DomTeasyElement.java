package com.wiley.elements;

import com.wiley.elements.should.DomShould;
import com.wiley.elements.should.Should;
import com.wiley.elements.waitfor.DomElementWaitFor;
import com.wiley.elements.waitfor.ElementWaitFor;
import com.wiley.holders.DriverHolder;

/**
 * Reprsents element that is present in DOM (not necessarily visible)
 */
public class DomTeasyElement extends BaseTeasyElement {

    public DomTeasyElement(TeasyElementData teasyElementData) {
        super(teasyElementData);
    }

    @Override
    public Should should() {
        return new DomShould(this, new TeasyFluentWait<>(DriverHolder.getDriver()));
    }

    @Override
    public Should should(SearchStrategy strategy) {
        return new DomShould(this, strategy, new TeasyFluentWait<>(DriverHolder.getDriver()));
    }

    @Override
    public ElementWaitFor waitFor() {
        return new DomElementWaitFor(this, new TeasyFluentWait<>(DriverHolder.getDriver()));
    }

    @Override
    public ElementWaitFor waitFor(SearchStrategy strategy) {
        return new DomElementWaitFor(this, strategy, new TeasyFluentWait<>(DriverHolder.getDriver()));
    }

}
