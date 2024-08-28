package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MonthNameTest {

    @Test
    public void testIsAfter() throws Exception {
        assertTrue(MonthName.APRIL.isAfter(MonthName.FEBRUARY));
        assertFalse(MonthName.JANUARY.isAfter(MonthName.FEBRUARY));
        assertFalse(MonthName.JANUARY.isAfter(MonthName.JANUARY));
    }

    @Test
    public void testIsBefore() throws Exception {
        assertTrue(MonthName.JANUARY.isBefore(MonthName.FEBRUARY));
        assertFalse(MonthName.APRIL.isBefore(MonthName.FEBRUARY));
        assertFalse(MonthName.JANUARY.isBefore(MonthName.JANUARY));
    }

    @Test
    public void testGetFrom() throws Exception {
        assertEquals(MonthName.JANUARY, MonthName.getFrom("jAnUAry"));
        assertNull(MonthName.getFrom("noSuchMonth"));
    }
}