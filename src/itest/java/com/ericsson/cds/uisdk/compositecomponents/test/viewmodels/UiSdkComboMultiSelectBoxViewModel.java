package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboMultiSelectBox;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkComboMultiSelectBoxViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The ComboMultiSelect widget')]/..")
    private UiSdkComboMultiSelectBox comboMultiSelect;

    public UiSdkComboMultiSelectBox getComboMultiSelect() {
        return comboMultiSelect;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(comboMultiSelect, tab, this);
    }

}
