package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.accordion.Accordion;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkAccordionViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//*[contains(text(),'Accordion widget')]/parent::*")
    private Accordion accordion;

    public Accordion getAccordion() {
        return accordion;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(accordion, tab, this);
    }

}
