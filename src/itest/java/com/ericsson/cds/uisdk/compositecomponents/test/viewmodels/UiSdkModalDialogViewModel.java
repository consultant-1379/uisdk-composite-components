package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.modaldialog.ModalDialog;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkModalDialogViewModel extends GenericViewModel {

    @UiComponentMapping(value = ".ebDialog")
    private ModalDialog dialogBox;

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The Modal Dialog')]/..")
    private Button showDialogButton;

    public ModalDialog getModalDialog() {
        return dialogBox;
    }

    public Button getShowDialogButton() {
        return showDialogButton.getDescendantsBySelector(".ebBtn").get(0).as(Button.class);
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(showDialogButton, tab, this);
    }
}
