package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import java.util.Date;

import org.openqa.selenium.Keys;

import com.ericsson.cds.uisdk.compositecomponents.datepicker.PopupDatePicker;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

public class DateFilterField extends FilterField {

    @UiComponentMapping(".elWidgets-PopupDatePicker")
    protected PopupDatePicker popupDatePicker;

    @UiComponentMapping(".elWidgets-PopupDatePicker-input")
    protected TextBox input;

    /**
     * Filter.
     *
     * @param filterValue the filter value
     * @return localited date string
     */
    public String filter(Date filterValue) {
        validateFilterComponentVisibility(popupDatePicker, filterValue.toString(), this.getClass().getName());
        popupDatePicker.selectDate(filterValue);
        input.sendKeys(Keys.ENTER);
        return input.getText();
    }

}
