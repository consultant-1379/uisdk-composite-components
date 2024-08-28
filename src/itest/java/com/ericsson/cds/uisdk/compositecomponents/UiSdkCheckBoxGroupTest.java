package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkCheckBoxGroupViewModel;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UiSdkCheckBoxGroupTest extends AbstractWidgetLibraryTest {

    private UiSdkCheckBoxGroup checkBoxGroup;

    @Before
    public void initializeView() {
        UiSdkCheckBoxGroupViewModel view = tab.getView(UiSdkCheckBoxGroupViewModel.class);
        view.ensurePageIsLoaded(tab);
        checkBoxGroup = view.getCheckBoxGroup();
    }

    @Test
    public void setValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Checkbox Item 1");
        expectedValues.add("Checkbox Item 2");

        checkBoxGroup.setValues(expectedValues);
        assertTrue(checkBoxGroup.getValues().equals(expectedValues));
    }

    @Test
    public void getValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Checkbox Item 0");
        expectedValues.add("Checkbox Item 1");

        List<String> actualValues = checkBoxGroup.getValues();
        assertTrue(actualValues.equals(expectedValues));
    }

    @Test
    public void getItems() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Checkbox Item 0");
        expectedValues.add("Checkbox Item 1");
        expectedValues.add("Checkbox Item 2");

        List<String> actualValues = checkBoxGroup.getItems();
        assertTrue(actualValues.equals(expectedValues));
    }

    @Test
    public void selectAll() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Checkbox Item 0");
        expectedValues.add("Checkbox Item 1");
        expectedValues.add("Checkbox Item 2");

        checkBoxGroup.selectAll();
        assertTrue(checkBoxGroup.getValues().equals(checkBoxGroup.getItems()));
    }

    @Test
    public void deselectAll() {
        checkBoxGroup.deselectAll();
        assertTrue(checkBoxGroup.getValues().isEmpty());
    }

    @Override
    protected String getWidgetUrl() {
        // NOTE use CheckBoxGroup from the FormLayout example
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#form-example";
    }
}
