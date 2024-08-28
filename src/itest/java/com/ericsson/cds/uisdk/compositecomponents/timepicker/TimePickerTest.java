package com.ericsson.cds.uisdk.compositecomponents.timepicker;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTimePickerViewModel;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.assertEquals;

public class TimePickerTest extends AbstractWidgetLibraryTest {

    private UiSdkTimePickerViewModel timePickerView;
    private TimePicker timePicker;

    @Before
    public void initializeView() {
        timePickerView = tab.getView(UiSdkTimePickerViewModel.class);
        timePickerView.ensurePageIsLoaded(tab);
        timePicker = timePickerView.getTimePicker();
    }

    @Test
    public void getSelectedTime() {
        assertEquals(1, timePicker.getSelectedHours());
        assertEquals(2, timePicker.getSelectedMinutes());
        assertEquals(3, timePicker.getSelectedSeconds());
    }

    @Test
    public void selectTimeStepByStep() {
        int hour = 10;
        int minutes = 30;
        int seconds = 50;

        timePicker.selectHours(hour);
        timePicker.selectMinutes(minutes);
        timePicker.selectSeconds(seconds);

        assertEquals(hour, timePicker.getSelectedHours());
        assertEquals(minutes, timePicker.getSelectedMinutes());
        assertEquals(seconds, timePicker.getSelectedSeconds());
    }

    @Test
    public void selectTime() {
        Date date = new LocalDateTime().toDate();
        timePicker.selectTime(date);

        LocalDateTime localDateTime = new LocalDateTime(date);
        assertEquals(localDateTime.getHourOfDay(), timePicker.getSelectedHours());
        assertEquals(localDateTime.getMinuteOfHour(), timePicker.getSelectedMinutes());
        assertEquals(localDateTime.getSecondOfMinute(), timePicker.getSelectedSeconds());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/time-picker";
    }
}
