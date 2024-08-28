package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import org.junit.Test;

public class PopupOpenerTest {

    private PopupOpener unit = new PopupOpener();

    @Test
    public void shouldValidateDate_happyPath() throws Exception {
        unit.validateDate(3, 5, 2015);
        unit.validateDate(30, 5, 2015);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateDate_badDate() throws Exception {
        unit.validateDate(30, 2, 2015);
    }
}