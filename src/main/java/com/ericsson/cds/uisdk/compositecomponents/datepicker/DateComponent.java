package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import java.util.Date;

/**
 * @author Mihails Volkovs mihails.volkovs@ericsson.com
 *         Date: 09.06.2016
 */
public interface DateComponent {

    void selectDate(Date date);

    Date getSelectedDate();

}
