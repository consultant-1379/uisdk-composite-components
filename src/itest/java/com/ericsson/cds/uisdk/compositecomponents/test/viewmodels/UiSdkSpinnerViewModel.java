package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.spinner.Spinner;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkSpinnerViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//*[contains(text(),'The Spinner widget')]/parent::*")
    private Spinner spinner;

    public Spinner getSpinner() {
        return spinner;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(spinner, tab, this);
    }

}
