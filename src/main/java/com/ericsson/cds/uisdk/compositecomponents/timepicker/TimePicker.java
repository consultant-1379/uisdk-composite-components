package com.ericsson.cds.uisdk.compositecomponents.timepicker;

import com.ericsson.cds.uisdk.compositecomponents.spinner.Spinner;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.joda.time.LocalDateTime;
import org.openqa.selenium.Keys;

import java.util.Date;
import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

/**
 * Representation of UI SDK Time Picker component
 * 
 * @author ehrvkla
 * @since 1.0.29
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/time-picker">
 *       Time Picker</a>
 */
public class TimePicker extends AbstractUiComponent {

    @UiComponentMapping(selectorType = XPATH, value = "//table[1]")
    private Spinner hoursSpinner;

    @UiComponentMapping(selectorType = XPATH, value = "//table[2]")
    private Spinner minutesSpinner;

    @UiComponentMapping(selectorType = XPATH, value = "//table[3]")
    private Spinner secondsSpinner;

    public int getSelectedHours() {
        return hoursSpinner.getValue();
    }

    public int getSelectedMinutes() {
        return minutesSpinner.getValue();
    }

    public int getSelectedSeconds() {
        return secondsSpinner.getValue();
    }

    public void selectHours(int hour) {
        Preconditions.checkArgument(hour >= 0 && hour < 24, "Hours number is 0-23, selected: " + hour);
        hoursSpinner.setValue(hour);
    }

    public void selectMinutes(int minutes) {
        Preconditions.checkArgument(minutes >= 0 && minutes < 60, "Minutes number is 0-59, selected: " + minutes);
        minutesSpinner.setValue(minutes);
    }

    public void selectSeconds(int seconds) {
        Preconditions.checkArgument(seconds >= 0 && seconds < 60, "Seconds number is 0-59, selected: " + seconds);
        secondsSpinner.setValue(seconds);
    }

    public void selectTime(Date date) {
        LocalDateTime localDateTime = new LocalDateTime(date);
        selectHours(localDateTime.getHourOfDay());
        selectMinutes(localDateTime.getMinuteOfHour());
        selectSeconds(localDateTime.getSecondOfMinute());
    }

    @VisibleForTesting
    protected Date toDate(int hours, int minutes, int seconds) {
        return new LocalDateTime(0, 0, 0, hours, minutes, seconds).toDate();
    }
}
