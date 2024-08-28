package com.ericsson.cds.uisdk.compositecomponents.spinner;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkSpinnerViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpinnerTest extends AbstractWidgetLibraryTest {

    private Spinner spinner;
    private UiSdkSpinnerViewModel spinnerView;

    @Before
    public void initializeView() {
        spinnerView = tab.getView(UiSdkSpinnerViewModel.class);
        spinner = spinnerView.getSpinner();
        spinnerView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testSpinnerGetLabel() {
        String label = spinner.getLabel();
        assertTrue(label != null);
    }

    @Test
    public void testSpinnerGetValue() {
        spinner.getValue();
    }

    @Test
    public void testSpinnerGetValueLabel() {
        String valueLabel = spinner.getValueLabel();
        assertTrue(valueLabel != null);
    }

    @Test
    public void testSpinnerIsValid() {
        spinner.setValue(1234567890);
        assertFalse(spinner.isValid());
    }

    @Test
    public void testSpinnerSetValue() {
        final int valueToSet = 9;
        spinner.setValue(valueToSet);

        int spinnerValue = spinner.getValue();
        assertEquals(valueToSet, spinnerValue);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/spinner";
    }
}
