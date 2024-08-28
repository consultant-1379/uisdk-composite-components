package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Mihails Volkovs mihails.volkovs@ericsson.com
 *         Date: 09.06.2016
 */
public class PopupDatePickerUnitTest {

    @Test
    public void toDateFormat() {
        PopupDatePicker datePicker = new PopupDatePicker();
        String actual = datePicker.toDateFormat("DD.MM.YYYY").toPattern();
        Assertions.assertThat(actual).isEqualTo("dd.MM.yyyy");
    }

}