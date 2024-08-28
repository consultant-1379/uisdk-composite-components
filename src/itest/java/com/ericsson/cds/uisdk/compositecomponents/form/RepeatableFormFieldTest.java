package com.ericsson.cds.uisdk.compositecomponents.form;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkFormViewModel;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class RepeatableFormFieldTest extends AbstractWidgetLibraryTest {

    private Form form;
    private UiSdkFormViewModel formView;
    private Multimap<String, String> pathValuesMultiMap = LinkedListMultimap.create();
    private RepeatableFormField repeatableFormField;

    @Before
    public void initializeView() {
        formView = tab.getView(UiSdkFormViewModel.class);
        formView.ensurePageIsLoaded(tab);
        form = formView.getForm();
        repeatableFormField = form.getFieldByKey("structure.nested_structure").as(RepeatableFormField.class);

        initializeTestValues();
    }

    @Test
    public void ensureFieldAtIndex() {

        assertEquals(1, getNumberOfFormStructFields());

        repeatableFormField.ensureFieldAtIndex(5);

        assertEquals(5, getNumberOfFormStructFields());
    }

    @Test
    public void clearFieldsByMultimap() {

        // set values
        form.setValues(pathValuesMultiMap);

        // set all multimap values to ""
        pathValuesMultiMap.clear();
        pathValuesMultiMap.put("structure.nested_structure[1].field1", "");
        pathValuesMultiMap.put("structure.nested_structure[1].field3", "");
        pathValuesMultiMap.put("structure.nested_structure[2].field1", "");
        pathValuesMultiMap.put("structure.nested_structure[2].field3", "");

        form.setValues(pathValuesMultiMap);

        // validate empty fields
        for (String key : pathValuesMultiMap.keySet()) {
            assertEquals(form.getFieldsByDataPath(key).getDescendantBySelector("input").getText(), "");
        }
    }

    @Test
    public void removeFieldsByMultiMap() {
        assertEquals(1, getNumberOfFormStructFields());

        Multimap<String, String> tempMultiMap = LinkedListMultimap.create();
        tempMultiMap.put("structure.nested_structure[1]", ""); // empty value on structure field should clear it

        form.setValues(tempMultiMap);

        assertEquals(0, getNumberOfFormStructFields());
    }

    @Test
    public void removeFieldsInternal() {
        assertEquals(1, getNumberOfFormStructFields());

        repeatableFormField.clear();

        assertEquals(0, getNumberOfFormStructFields());
    }

    @Test
    public void setValuesByFieldPath() {
        form.setValues(pathValuesMultiMap);

        validateFields();
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#form-example";
    }

    private int getNumberOfFormStructFields() {
        return repeatableFormField.getFieldsCount();
    }

    private void initializeTestValues() {
        pathValuesMultiMap.put("structure.nested_structure[1].field1", "field 1.1 value");
        pathValuesMultiMap.put("structure.nested_structure[1].field3", "field 1.3 value");

        pathValuesMultiMap.put("structure.nested_structure[2].field1", "field 2.1 value");
        pathValuesMultiMap.put("structure.nested_structure[2].field3", "field 2.3 value");
    }

    private void validateFields() {
        for (String key : pathValuesMultiMap.keySet()) {
            assertEquals(form.getFieldsByDataPath(key).getDescendantBySelector("input").getText(),
                    pathValuesMultiMap.get(key).iterator().next());
        }
    }
}
