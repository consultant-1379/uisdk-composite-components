package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import com.ericsson.cds.uisdk.compositecomponents.datepicker.DatePicker.MonthDaysTable;
import com.ericsson.cds.uisdk.compositecomponents.exceptions.DatePickerException;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiException;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MonthDaysTableTest {

    private MonthDaysTable unit = spy(new MonthDaysTable());

    @Before
    public void setUp() {
        doReturn(asList(dayComponent(19))).when(unit).getDescendantsBySelector(XPATH, "//*[contains(text(),'19')]");
        doReturn(new ArrayList<UiComponent>()).when(unit).getDescendantsBySelector(XPATH, "//*[contains(text(),'12')]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSelectDayInWeek_badWeekNumber() throws Exception {
        unit.selectDay(0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSelectDayInWeek_badDayNumber() throws Exception {
        unit.selectDay(1, 8);
    }

    @Test(expected = DatePickerException.class)
    public void testSelectDayInWeek_noSuchWeek() throws Exception {
        UiComponent week1 = mock(UiComponent.class);
        unit.setBodyRows(asList(week1));
        try {
            unit.selectDay(2, 5);
        } catch (DatePickerException e) {
            assertEquals("There is no such week number in current calendar month", e.getMessage());
            throw e;
        }
    }

    @Test(expected = DatePickerException.class)
    public void testSelectDayInWeek_noSuchDay() throws Exception {
        UiComponent week1 = mock(UiComponent.class);
        UiComponent day1 = mock(UiComponent.class);
        when(week1.getChildren()).thenReturn(asList(day1));
        unit.setBodyRows(asList(week1));
        try {
            unit.selectDay(1, 5);
        } catch (DatePickerException e) {
            assertEquals("There is no day 5 in week 1", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testSelectDayInWeek_happyPath() throws Exception {
        UiComponent week1 = mock(UiComponent.class);
        UiComponent day1 = mock(UiComponent.class);
        UiComponent day2 = mock(UiComponent.class);
        when(week1.getChildren()).thenReturn(asList(day1, day2));
        UiComponent clickableElt = mock(UiComponent.class);
        when(day2.getChildren()).thenReturn(asList(clickableElt));
        unit.setBodyRows(asList(week1));
        unit.selectDay(1, 2);
        verify(clickableElt).click();
    }

    @Test
    public void testSelectDayOfMonth_noSuchDay() {
        try {
            unit.selectDay(12);
            fail();
        } catch (UiException e) {
            assertEquals("There is no day 12 found in the selected month", e.getMessage());
        }
    }

    @Test
    public void testSelectDayOfMonth_happyPath() {
        unit.selectDay(19);
    }

    private UiComponent weekComponentWithDays(int... days) {
        UiComponent result = mock(UiComponent.class);
        List<UiComponent> dayComponents = Lists.newArrayList();
        for (int day : days) {
            dayComponents.add(dayComponent(day));
        }
        when(result.getChildren()).thenReturn(dayComponents);
        return result;
    }

    private UiComponent dayComponent(int day) {
        UiComponent result = mock(UiComponent.class, Mockito.RETURNS_DEEP_STUBS);
        UiComponent clickableElt = mock(UiComponent.class);
        when(result.getChildren()).thenReturn(asList(clickableElt));
        when(clickableElt.getText()).thenReturn(String.valueOf(day));
        return result;
    }

}