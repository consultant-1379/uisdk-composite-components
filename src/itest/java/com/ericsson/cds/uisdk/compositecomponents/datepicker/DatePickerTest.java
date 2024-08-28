package com.ericsson.cds.uisdk.compositecomponents.datepicker;


import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkDatePickerViewModel;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatePickerTest extends AbstractWidgetLibraryTest {

    private static final String WIDGET_URL = "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/date-picker";

    private DatePicker datePicker;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        // Some versions of FF can open a "splash" screen, and widget URL will be opened in a second tab
        switchToWidgetTab();
        tab.maximize();
        UiSdkDatePickerViewModel view = tab.getView(UiSdkDatePickerViewModel.class);
        datePicker = view.getDatePicker();
        view.ensurePageIsLoaded(tab);
    }

    private void switchToWidgetTab() {
        List<BrowserTab> allOpenTabs = browser.getAllOpenTabs();
        for (BrowserTab browserTab : allOpenTabs) {
            if (WIDGET_URL.equals(browserTab.getCurrentUrl())) {
                browser.switchWindow(browserTab);
                tab = browserTab;
                break;
            }
        }
    }

    @Test
    public void shouldSelectDateByWeekAndDayOfWeek() {
        int weekNumber = 2;
        int dayOfWeek = 4;
        datePicker.selectDay(weekNumber, dayOfWeek);

        Date expected = new LocalDate(getDateForCalendarSheetSelection(weekNumber, dayOfWeek)).toDate();
        assertThat(datePicker.getSelectedDate()).isEqualTo(expected);
    }

    @Test
    public void testPickAnyDate() {
        datePicker.selectDay(MonthName.NOVEMBER, 15, 2016);

        Date actual = datePicker.getSelectedDate();
        assertThat(actual).hasDayOfMonth(15);
        assertThat(actual).hasMonth(11);
        assertThat(actual).hasYear(2016);

        assertThat(datePicker.getSelectedDay()).isEqualTo(15);
        assertThat(datePicker.getSelectedMonth().getMonthNumber()).isEqualTo(11);
        assertThat(datePicker.getSelectedYear()).isEqualTo(2016);
    }

    @Test
    public void selectionByDate() {
        LocalDate today = new LocalDate();
        assertThat(datePicker.getSelectedDate()).isEqualTo(today.toDate());

        // selecting some other day in another month
        LocalDate anotherDay = today.plusDays(33);
        datePicker.selectDate(anotherDay.toDate());
        assertThat(datePicker.getSelectedDate()).isEqualTo(anotherDay.toDate());
    }

    @Override
    protected String getWidgetUrl() {
        return WIDGET_URL;
    }

    private Date getDateForCalendarSheetSelection(int weekNumber, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.set(Calendar.WEEK_OF_MONTH, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek == 7 ? 1 : dayOfWeek + 1);

        // there is different Date Format on Selenium Grid
        return calendar.getTime();
    }
}
