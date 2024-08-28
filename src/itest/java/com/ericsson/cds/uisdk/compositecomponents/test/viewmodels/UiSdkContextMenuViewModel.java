package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkContextMenu;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkContextMenuViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The Context Menu widget ')]/..")
    private UiSdkContextMenu contextMenu;

    public UiSdkContextMenu getContextMenu() {
        return contextMenu;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(contextMenu, tab, this);
    }

}
