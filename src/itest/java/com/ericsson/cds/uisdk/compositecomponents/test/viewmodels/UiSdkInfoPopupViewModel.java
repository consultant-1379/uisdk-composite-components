package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.infopopup.InfoPopup;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkInfoPopupViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The InfoPopup widget ')]/..")
    private InfoPopup infoPopup;

    @UiComponentMapping(".ebInfoPopup-content")
    private UiComponent popupContent;

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(infoPopup, tab, this);
    }

    public InfoPopup getInfoPopup() {
        return infoPopup;
    }

    public UiComponent getPopupContent() {
        return popupContent;
    }

}
