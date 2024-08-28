package com.ericsson.cds.uisdk.compositecomponents.form;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkFormViewModel;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class FormStructureTest extends AbstractWidgetLibraryTest {

    private Form form;
    private FormStructure formStructure;
    private UiSdkFormViewModel formView;

    @Before
    public void initializeView() {
        formView = tab.getView(UiSdkFormViewModel.class);
        formView.ensurePageIsLoaded(tab);
        formStructure = formView.getStructureField();
        form = formView.getForm();
    }

    @Test
    public void expandCollapseStructureTest() {
        assertFalse(formStructure.isExpanded());

        // expand
        formStructure.expand();
        assertTrue(formStructure.isExpanded());

        // collapse
        formStructure.collapse();
        assertFalse(formStructure.isExpanded());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setUnsupportedValueToStructure() {

        Multimap<String, String> tempMultiMap = LinkedListMultimap.create();
        tempMultiMap.put("structure.nested_structure[1]", "notSupportedValue");

        form.setValues(tempMultiMap);
    }

    @Test
    public void isExpandedTest() {
        assertFalse(formStructure.isExpanded());
    }

    @Test
    public void getSubForm() {
        Form subForm = formStructure.getSubForm();
        assertTrue(subForm.isDisplayed());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#form-example";
    }

}
