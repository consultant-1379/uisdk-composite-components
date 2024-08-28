package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.timepicker.TimePicker;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkTimePickerViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[@data-namespace='ebTimePicker']")
    private TimePicker timePicker;

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(timePicker, tab, this);
    }
}
