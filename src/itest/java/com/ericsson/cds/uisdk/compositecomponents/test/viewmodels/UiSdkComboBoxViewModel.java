package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboBox;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkComboBoxViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The Combo Box widget')]/..")
    private UiSdkComboBox comboBox;

    public UiSdkComboBox getComboBox() {
        return comboBox;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(comboBox, tab, this);
    }

}
