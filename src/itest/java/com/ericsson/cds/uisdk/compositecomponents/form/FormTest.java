package com.ericsson.cds.uisdk.compositecomponents.form;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBoxGroup;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboMultiSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkMultiSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.datepicker.PopupDatePicker;
import com.ericsson.cds.uisdk.compositecomponents.radiobuttons.RadioButtonGroup;
import com.ericsson.cds.uisdk.compositecomponents.spinner.Spinner;
import com.ericsson.cds.uisdk.compositecomponents.switcher.Switcher;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkFormViewModel;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FormTest extends AbstractWidgetLibraryTest {

    private static final Logger LOG = LoggerFactory.getLogger(FormTest.class);

    String checkBoxGroupValue1 = "Checkbox Item 1";
    String checkBoxGroupValue2 = "Checkbox Item 2";
    String combomultiselectValue = "Item 2";
    String comboValue = "Item 1";
    SimpleDateFormat datePickerFormat;
    String datePickerValue;
    String inputValue = "input value";
    String multiselectValue1 = "Item 0";
    String multiselectValue2 = "Item 1";
    String passwordValue = "password value";
    String radiogroupValue = "Radio Item 1";
    String selectValue = "Item 2";
    String spinnerValue = "10";
    String switcherValue = "false";
    String textareaValue = "textarea value";

    private Form form;
    private UiSdkFormViewModel formView;

    @Test
    public void checkIsValid() {
        assertTrue(form.isValid());
    }

    @Test
    public void checkValidationError() {

        form.clear("input");

        formView.getTopSection().clickAction("Validate");

        assertFalse(form.isValid());
    }

    @Test
    public void clearAllFields() {

        validateFields(false);

        // set values to fields (mustn't be empty)
        Multimap<String, String> multimap = LinkedListMultimap.create();
        multimap.put("input", inputValue);
        multimap.put("textarea", textareaValue);
        multimap.put("password", passwordValue);
        multimap.put("select", selectValue);
        multimap.put("combo[1]", comboValue);
        multimap.put("multiselect", multiselectValue1);
        multimap.put("multiselect", multiselectValue2);
        multimap.put("combomultiselect", combomultiselectValue);
        multimap.put("switcher", switcherValue);
        multimap.put("radiogroup", radiogroupValue);
        multimap.put("spinner", spinnerValue);
        multimap.put("checkboxgroup", checkBoxGroupValue1);
        multimap.put("checkboxgroup", checkBoxGroupValue2);
        multimap.put("popupdatepicker", datePickerValue);

        form.setValues(multimap);

        form.clearAll();

        // clear variables for validateFields method and check if fields are cleared
        setClearValues();
        comboValue = null;

        validateFields(true);
    }

    @Test
    public void clearValuesByFieldPath() {

        validateFields(false);

        // set values to fields (mustn't be empty)
        Multimap<String, String> multimap = LinkedListMultimap.create();
        multimap.put("input", inputValue);
        multimap.put("textarea", textareaValue);
        multimap.put("password", passwordValue);
        multimap.put("select", selectValue);
        multimap.put("combo[1]", comboValue);
        multimap.put("multiselect", multiselectValue1);
        multimap.put("multiselect", multiselectValue2);
        multimap.put("combomultiselect", combomultiselectValue);
        multimap.put("switcher", switcherValue);
        multimap.put("radiogroup", radiogroupValue);
        multimap.put("spinner", spinnerValue);
        multimap.put("checkboxgroup", checkBoxGroupValue1);
        multimap.put("checkboxgroup", checkBoxGroupValue2);
        multimap.put("popupdatepicker", datePickerValue);

        form.setValues(multimap);

        // clear fields
        form.clear("input", "textarea", "password", "combo[1]", "multiselect", "combomultiselect", "switcher",
                "spinner", "checkboxgroup", "popupdatepicker");

        // clear variables for validateFields method and check if fields are cleared
        setClearValues();
        validateFields(true);
    }

    @Before
    public void initializeView() {
        formView = tab.getView(UiSdkFormViewModel.class);
        formView.ensurePageIsLoaded(tab);
        form = formView.getForm();

        datePickerFormat = getElementByDataPath("popupdatepicker", false).as(PopupDatePicker.class).getDateFormat();
        datePickerValue = datePickerFormat.format(new Date());
    }

    @Test
    public void parentFieldsExpandedTest() {

        form.getFieldByKey("structure.nested_structure.field1");
        assertTrue(formView.getNestedField().isDisplayed());
    }

    @Test
    public void setValuesByFieldPath() {
        validateFields(false);

        Multimap<String, String> multimap = LinkedListMultimap.create();
        multimap.put("input", inputValue);
        multimap.put("textarea", textareaValue);
        multimap.put("password", passwordValue);
        multimap.put("select", selectValue);
        multimap.put("combo[1]", comboValue);
        multimap.put("multiselect", multiselectValue1);
        multimap.put("multiselect", multiselectValue2);
        multimap.put("combomultiselect", combomultiselectValue);
        multimap.put("switcher", switcherValue);
        multimap.put("radiogroup", radiogroupValue);
        multimap.put("spinner", spinnerValue);
        multimap.put("checkboxgroup", checkBoxGroupValue1);
        multimap.put("checkboxgroup", checkBoxGroupValue2);
        multimap.put("popupdatepicker", datePickerValue);

        form.setValues(multimap);

        validateFields(true);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#form-example";
    }

    private void assertEquals(Object expected, Object actual, boolean shouldBeEqual) {

        LOG.info(String.format("Comparing two %s objects, should %sbe equal.\nExpected: %s, \nActual: %s",
                expected.getClass(), (shouldBeEqual ? "" : "not "), expected.toString(), actual.toString()));
        try {
            if (shouldBeEqual) {
                assertTrue(expected.equals(actual));
            } else {
                assertFalse(expected.equals(actual));
            }
        } catch (AssertionError e) {
            throw new AssertionError(String.format("Assertion failed! %s are %sequal.\nExpected: %s, \nActual: %s",
                    expected.getClass(), (shouldBeEqual ? "not " : ""), expected.toString(), actual.toString()));
        }
    }

    private UiComponent getElementByDataPath(String dataPath, boolean goToElementInside) {
        List<UiComponent> elements = form.getDescendantsBySelector(SelectorType.XPATH,
                "//*[@data-path='" + dataPath + "']/*[contains(@class, 'elLayouts-Form-FieldBody-body')]/*[1]"
                        + (goToElementInside ? "/*[1]" : ""));
        return elements.isEmpty() ? null : elements.get(0);
    }

    private void setClearValues() {
        // clear variables for validateFields method
        inputValue = "";
        textareaValue = "";
        passwordValue = "";
        comboValue = "";
        multiselectValue1 = null;
        multiselectValue2 = null;
        combomultiselectValue = null;
        switcherValue = "false";
        spinnerValue = "0";
        datePickerValue = "";
        checkBoxGroupValue1 = null;
        checkBoxGroupValue2 = null;
    }

    /**
     * Validate fields.
     *
     * @param equalsOrNot flag if all fields must be equal (as test values) or not
     */
    private void validateFields(boolean equalsOrNot) {

        TextBox input = getElementByDataPath("input", true).as(TextBox.class);
        TextBox textArea = getElementByDataPath("textarea", true).as(TextBox.class);
        TextBox password = getElementByDataPath("password", true).as(TextBox.class);
        UiSdkSelectBox selectBox = getElementByDataPath("select", true).as(UiSdkSelectBox.class);

        UiSdkComboBox comboBox = getElementByDataPath("combo", true) == null ? null
                : getElementByDataPath("combo", true).as(UiSdkComboBox.class);
        UiSdkMultiSelectBox multiselectBox = getElementByDataPath("multiselect", true).as(UiSdkMultiSelectBox.class);
        UiSdkComboMultiSelectBox comboMultiSelectBox = getElementByDataPath("combomultiselect", true)
                .as(UiSdkComboMultiSelectBox.class);
        Switcher switcher = getElementByDataPath("switcher", true).as(Switcher.class);
        RadioButtonGroup radioButtonGroup = getElementByDataPath("radiogroup", false).as(RadioButtonGroup.class);
        Spinner spinner = getElementByDataPath("spinner", true).as(Spinner.class);
        UiSdkCheckBoxGroup checkBoxGroup = getElementByDataPath("checkboxgroup", false).as(UiSdkCheckBoxGroup.class);
        PopupDatePicker datePicker = getElementByDataPath("popupdatepicker", false).as(PopupDatePicker.class);

        assertEquals(inputValue, input.getText(), equalsOrNot);
        assertEquals(textareaValue, textArea.getText(), equalsOrNot);
        assertEquals(passwordValue, password.getText(), equalsOrNot);
        assertEquals(selectValue, selectBox.getValue(), equalsOrNot);
        if (comboValue == null) {
            assertTrue(comboBox == null);
        } else {
            assertEquals(comboValue, comboBox.getValue(), equalsOrNot);
        }

        List<String> multiselectExpectedValues = new ArrayList<>();
        if (multiselectValue1 != null) { // dont add if null
            multiselectExpectedValues.add(multiselectValue1);
        }
        if (multiselectValue2 != null) { // dont add if null
            multiselectExpectedValues.add(multiselectValue2);
        }

        assertEquals(multiselectExpectedValues, multiselectBox.getValues(), equalsOrNot);

        List<String> combomultiselectExpectedValues = new ArrayList<>();
        if (combomultiselectValue != null) {
            combomultiselectExpectedValues.add(combomultiselectValue);
        }
        assertEquals(combomultiselectExpectedValues, comboMultiSelectBox.getValues(), equalsOrNot);

        assertEquals(Boolean.valueOf(switcherValue), switcher.isActivated(), equalsOrNot);
        assertEquals(radiogroupValue, radioButtonGroup.getValue(), equalsOrNot);

        assertEquals(Integer.parseInt(spinnerValue), spinner.getValue(), equalsOrNot);

        List<String> checkBoxGroupValues = new ArrayList<>();
        if (checkBoxGroupValue1 != null) { // dont add if null
            checkBoxGroupValues.add(checkBoxGroupValue1);
        }
        if (checkBoxGroupValue2 != null) { // dont add if null
            checkBoxGroupValues.add(checkBoxGroupValue2);
        }
        assertEquals(checkBoxGroupValues, checkBoxGroup.getValues(), equalsOrNot);

        final String actualDatePickerString = datePicker.getSelectedDate() == null ? ""
                : datePickerFormat.format(datePicker.getSelectedDate());
        assertEquals(datePickerValue, actualDatePickerString, equalsOrNot);
    }
}
