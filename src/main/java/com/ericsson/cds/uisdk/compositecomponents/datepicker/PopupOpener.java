package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;
import com.google.common.annotations.VisibleForTesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Composite component, representing UI SDK Date Picker's calendar opener and selected date.
 *
 * @deprecated please use PopupDatePicker instead
 */
@Deprecated
public class PopupOpener extends AbstractUiComponent {

    private static final String DATE_INPUT_PATTERN = "%d/%d/%d";

    @UiComponentMapping(".elWidgets-PopupDatePicker-inputWrapper .ebInput.elWidgets-PopupDatePicker-input")
    private TextBox dateInput;

    @UiComponentMapping(".ebIcon.ebIcon_calendar.ebIcon_interactive.elWidgets-PopupDatePicker-date")
    private Button calendarButton;

    /**
     * Returns the date selected in calendar.
     * @return date selected in calendar, in MM/DD/YYYY notation.
     */
    public String getPickedDate() {
        return dateInput.getText();
    }

    /**
     * Hide or show calendar.
     */
    public void toggleCalendar() {
        calendarButton.click();
    }

    /**
     * Sets the defined date into Date Picker as current one.
     * @param monthName
     * @param day
     * @param year
     */
    public void typeDate(MonthName monthName, int day, int year) {
        validateDate(day, monthName.getMonthNumber(), year);
        dateInput.setText(getDateAsString(monthName.getMonthNumber(), day, year));
    }

    private String getDateAsString(int month, int day, int year) {
        return String.format(DATE_INPUT_PATTERN, month, day, year);
    }

    @VisibleForTesting
    void validateDate(int day, int monthNumber, int year) {
        String dateAsString = getDateAsString(monthNumber, day, year);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("The date '%s' (MM/DD/YYYY) is incorrect", dateAsString));
        }
    }
}
