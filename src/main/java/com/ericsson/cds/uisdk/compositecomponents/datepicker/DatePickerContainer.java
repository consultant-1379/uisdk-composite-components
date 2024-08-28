package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * Component that keeps the date picker pop-up opener and the date picker itself.
 *
 * @deprecated Please use {@link PopupDatePicker} instead
 */
@Deprecated
public class DatePickerContainer extends AbstractUiComponent {

    @UiComponentMapping(".elWidgets-PopupDatePicker")
    private PopupOpener popupOpener;

    @UiComponentMapping(".elWidgets-PopupDatePicker-popup")
    private DatePicker datePicker;

    public String getPickedDate() {
        return popupOpener.getPickedDate();
    }

    public void toggleCalendar() {
        popupOpener.toggleCalendar();
    }

    public void selectDay(int week, int dayOfWeek) {
        datePicker.selectDay(week, dayOfWeek);
    }

    public void selectDay(MonthName monthName, int day, int year) {
        datePicker.selectDay(monthName, day, year);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void typeDate(MonthName monthName, int day, int year) {
        popupOpener.typeDate(monthName, day, year);
    }
}