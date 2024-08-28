package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

/**
 * Component representing UI SDK Popup Date Picker.
 * 
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/popup-date-picker">
 *       Popup Date Picker</a>
 */
public class PopupDatePicker extends AbstractUiComponent implements DateComponent {

    private SimpleDateFormat dateFormat;

    @UiComponentMapping(".elWidgets-PopupDatePicker-inputWrapper .ebInput.elWidgets-PopupDatePicker-input")
    private TextBox dateInput;

    public SimpleDateFormat getDateFormat() {
        initDateFormat();
        return dateFormat;
    }

    @Override
    public Date getSelectedDate() {
        initDateFormat();
        String input = dateInput.getText();
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            String message = format("Date input '%s' didn't match expected pattern '%s'", input,
                    dateFormat.toPattern());
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public void selectDate(Date date) {
        initDateFormat();
        dateInput.setText(dateFormat.format(date));
    }

    @VisibleForTesting
    protected SimpleDateFormat toDateFormat(String placeholder) {
        String format = placeholder.replaceAll("D", "d").replaceAll("Y", "y");
        return new SimpleDateFormat(format);
    }

    private void initDateFormat() {
        if (dateFormat == null) {
            String placeholder = dateInput.getProperty("placeholder");
            dateFormat = toDateFormat(placeholder);
        }
    }

}
