package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBoxGroup;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkCheckBoxGroupViewModel extends GenericViewModel {

    @UiComponentMapping(".elLayouts-Form-CheckboxGroup")
    private UiSdkCheckBoxGroup checkBoxGroup;

    public UiSdkCheckBoxGroup getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(checkBoxGroup, tab, this);
    }
}
