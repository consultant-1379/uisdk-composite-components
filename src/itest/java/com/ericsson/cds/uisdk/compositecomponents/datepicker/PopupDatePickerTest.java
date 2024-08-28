package com.ericsson.cds.uisdk.compositecomponents.datepicker;


import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkPopupDatePickerViewModel;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class PopupDatePickerTest extends AbstractWidgetLibraryTest {

    private static final String WIDGET_URL = "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/popup-date-picker";

    private UiSdkPopupDatePickerViewModel view;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        view = tab.getView(UiSdkPopupDatePickerViewModel.class);
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void popupDatePicker() {

        // selected date
        Date componentDate = view.getSelectedDate();
        Assertions.assertThat(componentDate).isNull();

        // selecting today
        Date today = new LocalDate().toDate();
        view.selectDate(today);
        componentDate = view.getSelectedDate();
        Assertions.assertThat(componentDate).isEqualTo(today);

        // selecting another day
        Date anotherDate = new LocalDate(today).plusDays(33).toDate();
        view.selectDate(anotherDate);
        componentDate = view.getSelectedDate();
        Assertions.assertThat(componentDate).isEqualTo(anotherDate);
    }

    @Override
    protected String getWidgetUrl() {
        return WIDGET_URL;
    }
}
