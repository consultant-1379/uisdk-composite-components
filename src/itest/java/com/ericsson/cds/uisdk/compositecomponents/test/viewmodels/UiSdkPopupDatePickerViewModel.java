package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.datepicker.PopupDatePicker;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import java.util.Date;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkPopupDatePickerViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//*[contains(text(),'Popup Date Picker widget')]/..")
    private PopupDatePicker popupDatePicker;

    public void selectDate(Date date) {
        popupDatePicker.selectDate(date);
    }

    public Date getSelectedDate() {
        return popupDatePicker.getSelectedDate();
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(popupDatePicker, tab, this);
    }
}
