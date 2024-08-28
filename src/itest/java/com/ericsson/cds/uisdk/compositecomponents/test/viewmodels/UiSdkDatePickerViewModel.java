package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.datepicker.DatePicker;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkDatePickerViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//*[contains(text(),'The Date Picker widget')]/..")
    private DatePicker datePicker;

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(datePicker, tab, this);
    }
}
