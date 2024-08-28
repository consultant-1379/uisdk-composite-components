package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.radiobuttons.RadioButtonGroup;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkRadioButtonsViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//p[contains(text(),'Radio buttons ')]/..")
    private RadioButtonGroup radioButtons;

    public RadioButtonGroup getRadioButtons() {
        return radioButtons;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(radioButtons, tab, this);
    }
}
