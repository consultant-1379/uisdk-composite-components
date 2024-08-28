package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Mihails Volkovs mihails.volkovs@ericsson.com
 *         Date: 08.06.2016
 */
public class DatePickerUnitTest {

    @Test
    public void toDate() {
        DatePicker datePicker = new DatePicker();
        Date date = datePicker.toDate(1, 2, 2000);

        LocalDate actual = new LocalDate(date);
        assertEquals(1, actual.getDayOfMonth());
        assertEquals(2, actual.getMonthOfYear());
        assertEquals(2000, actual.getYear());
    }

}