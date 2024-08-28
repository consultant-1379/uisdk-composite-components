package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.editablefield.EditableField;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkEditableFieldViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The EditableField')]/..")
    private EditableField editableField;

    public EditableField getEditableField() {
        return editableField;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(editableField, tab, this);
    }

}
