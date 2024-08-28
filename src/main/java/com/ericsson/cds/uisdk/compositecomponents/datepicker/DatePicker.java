package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import com.ericsson.cds.uisdk.compositecomponents.exceptions.DatePickerException;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Label;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

/**
 * Representation of UI SDK Date Picker's calendar part
 * 
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/date-picker">
 *       Date Picker</a>
 */
public class DatePicker extends AbstractUiComponent implements DateComponent {

    @UiComponentMapping(".ebDatePicker-body")
    private MonthDaysTable monthDaysTable;

    @UiComponentMapping(".ebDatePicker-head")
    private CalendarNavigator calendarNavigator;

    public void selectDay(int dayOfMonth) {
        monthDaysTable.selectDay(dayOfMonth);
    }

    public void selectDay(int week, int dayOfWeek) {
        monthDaysTable.selectDay(week, dayOfWeek);
    }

    public void selectDay(MonthName monthName, int day, int year) {
        calendarNavigator.navigateTo(monthName, year);
        monthDaysTable.selectDay(day);
    }

    @Override
    public void selectDate(Date date) {
        LocalDate localDate = new LocalDate(date);
        MonthName monthName = MonthName.valueOf(localDate.getMonthOfYear());
        int dayOfMonth = localDate.getDayOfMonth();
        int year = localDate.getYear();
        selectDay(monthName, dayOfMonth, year);
    }

    @Override
    public Date getSelectedDate() {
        int selectedDay = getSelectedDay();
        int selectedMonth = getSelectedMonth().getMonthNumber();
        int selectedYear = getSelectedYear();
        return toDate(selectedDay, selectedMonth, selectedYear);
    }

    @VisibleForTesting
    protected Date toDate(int day, int month, int year) {
        return new LocalDate(year, month, day).toDate();
    }

    public MonthName getSelectedMonth() {
        return calendarNavigator.getSelectedMonth();
    }

    public int getSelectedYear() {
        return calendarNavigator.getSelectedYear();
    }

    public int getSelectedDay() {
        return monthDaysTable.getSelectedDay();
    }

    /**
     * Representation of UI SDK Date Picker's current month
     */
    static class MonthDaysTable extends AbstractUiComponent {

        @UiComponentMapping("tbody tr")
        private List<UiComponent> bodyRows;

        void selectDay(int week, int dayOfWeek) throws DatePickerException {
            Preconditions.checkArgument(week > 0 && week <= 6, "Week number is 1-6");
            Preconditions.checkArgument(dayOfWeek > 0 && dayOfWeek <= 7, "Day number is 1-7");

            if (bodyRows.size() < week) {
                throw new DatePickerException("There is no such week number in current calendar month");
            }
            UiComponent bodyRow = bodyRows.get(week - 1);
            List<UiComponent> days = bodyRow.getChildren();
            if (days.size() < dayOfWeek) {
                throw new DatePickerException(String.format("There is no day %d in week %d", dayOfWeek, week));
            }
            UiComponent day = days.get(dayOfWeek - 1);
            UiComponent span = day.getChildren().get(0);

            span.click();
        }

        void selectDay(int dayOfMonth) {
            try {
                String selector = "//*[contains(text(),'" + dayOfMonth + "')]";
                getDescendantsBySelector(XPATH, selector).get(0).click();
            } catch (IndexOutOfBoundsException e) {
                throw new DatePickerException(
                        String.format("There is no day %d found in the selected month", dayOfMonth));
            }
        }

        /**
         * Returns the day selected in currently open month
         * 
         * @return <code>-1</code> if there's no selection done
         */
        int getSelectedDay() {
            try {
                UiComponent selectedCell = getDescendantsBySelector(".ebDatePicker-day_selected").get(0);
                return Integer.parseInt(selectedCell.getText().trim());
            } catch (IndexOutOfBoundsException e) {
                return -1;
            }
        }

        @VisibleForTesting
        void setBodyRows(List<UiComponent> bodyRows) {
            this.bodyRows = bodyRows;
        }

    }

    /**
     * Representation of UI SDK Date Picker's calendar navigation
     */
    static class CalendarNavigator extends AbstractUiComponent {

        @UiComponentMapping(".ebDatePicker-prevYear .ebIcon")
        private Label prevYearBtn;

        @UiComponentMapping(".ebDatePicker-prevMonth .ebIcon")
        private Label prevMonthBtn;

        @UiComponentMapping(".ebDatePicker-nextYear .ebIcon")
        private Label nextYearBtn;

        @UiComponentMapping(".ebDatePicker-nextMonth .ebIcon")
        private Label nextMonthBtn;

        @UiComponentMapping(".ebDatePicker-monthYear")
        private Label currentMonthAndYear;

        MonthName getSelectedMonth() {
            String[] split = getMonthAndYearArray();
            String month = split[0].trim();
            return MonthName.getFrom(month);
        }

        int getSelectedYear() {
            String[] split = getMonthAndYearArray();
            return Integer.parseInt(split[1].trim());
        }

        void navigateTo(MonthName targetMonth, int targetYear) {
            // TODO: use smarter strategy: always go the shortest way to save time
            int selectedYear = getSelectedYear();
            while (targetYear != selectedYear) {
                String textBefore = currentMonthAndYear.getText();
                if (targetYear > selectedYear) {
                    nextYearBtn.click();
                } else {
                    prevYearBtn.click();
                }
                waitForCurrentSelectionUpdate(textBefore);
                selectedYear = getSelectedYear();
            }

            MonthName selectedMonth = getSelectedMonth();
            while (targetMonth != selectedMonth) {
                String textBefore = currentMonthAndYear.getText();
                if (targetMonth.isAfter(selectedMonth)) {
                    nextMonthBtn.click();
                } else {
                    prevMonthBtn.click();
                }
                waitForCurrentSelectionUpdate(textBefore);
                selectedMonth = getSelectedMonth();
            }
        }

        private void waitForCurrentSelectionUpdate(final String textBefore) {
            waitUntil(new GenericPredicate() {

                @Override
                public boolean apply() {
                    return !textBefore.equals(currentMonthAndYear.getText());
                }
            });
        }

        private String[] getMonthAndYearArray() {
            String monthAndYear = currentMonthAndYear.getText();
            return StringUtils.split(monthAndYear, " ");
        }

    }
}
