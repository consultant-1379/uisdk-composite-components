package com.ericsson.cds.uisdk.compositecomponents.form;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBoxGroup;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkComboMultiSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkMultiSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.datepicker.PopupDatePicker;
import com.ericsson.cds.uisdk.compositecomponents.radiobuttons.RadioButtonGroup;
import com.ericsson.cds.uisdk.compositecomponents.spinner.Spinner;
import com.ericsson.cds.uisdk.compositecomponents.switcher.Switcher;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiException;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

public class FormField extends AbstractUiComponent {

    private static String SWITCHER_DISABLED_TEXT = "DISABLED";
    private static String SWITCHER_ENABLED_TEXT = "ENABLED";

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "//div[@class='elLayouts-Form-FieldBody-body' or @class='elLayouts-Form-StructBody-body']//*[@class!='']")
    protected UiComponent fieldBody;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "/ancestor::*[@data-path][1]")
    protected FormField parentField;

    protected void clear() {

        expandParentElements();

        // handle repeatable fields- just clear all sub items
        if (this.getProperty("data-repeatable") != null) {
            this.as(RepeatableFormField.class).clear();
            return;
        }

        final String fieldClassName = fieldBody.getProperty("class").trim();
        switch (fieldClassName) {
        case "elLayouts-Form-Text":
        case "elLayouts-Form-Password":
            TextBox testBox = getDescendantBySelector("input").as(TextBox.class);
            if (!testBox.isEnabled()) {
                return;
            }
            testBox.clear();
            break;

        case "elLayouts-Form-InputTextArea":
            TextBox textArea = getDescendantBySelector("textarea").as(TextBox.class);
            if (!textArea.isEnabled()) {
                break;
            }
            textArea.clear();
            break;

        case "elLayouts-Form-select":
            break;// selectBox cant be cleared

        case "elLayouts-Form-combo":
            getDescendantBySelector(".ebCombobox").as(UiSdkComboBox.class).setValue("");
            // TODO get all items and checkItem if exist, otherwise setValue 
            break;

        case "elLayouts-Form-combomultiselect":
            getDescendantBySelector(".ebComboMultiSelect").as(UiSdkComboMultiSelectBox.class)
                    .setValues(new ArrayList<String>());
            break;

        case "elLayouts-Form-multiselect":
            getDescendantBySelector(".ebSelect").as(UiSdkMultiSelectBox.class).setValues(new ArrayList<String>());
            break;

        case "elLayouts-Form-Switcher":
            setSwitcherValue("false");
            break;

        case "elLayouts-Form-Spinner":
            getDescendantBySelector(".ebSpinner").as(Spinner.class).setValue(0);
            break;

        case "elLayouts-Form-PopupDatePicker":
            getDescendantBySelector(".elWidgets-PopupDatePicker input").as(TextBox.class).setText("");
            break;

        case "elLayouts-Form-StructBody":
            fieldBody.as(FormStructure.class).clear();
            break;

        case "elLayouts-Form-CheckboxGroup":
            fieldBody.as(UiSdkCheckBoxGroup.class).deselectAll();
            break;

        default:
            throw new UnsupportedOperationException(
                    "Can't clear form field with class '" + fieldClassName + "'. Field type is not supported.");
        }
    }

    /**
     * This method will expand every parent structure elements.
     * It is used to prevent NotVisibleException- the case when field element's parent element(s)
     * (structure) is not expanded
     *
     * @param field the field
     */
    protected FormField expandParentElements(FormField... field) {
        FormField currentField = field.length > 0 ? field[0] : this;

        FormField parentField = currentField.getParentField();
        if (parentField.exists()) {
            expandParentElements(parentField);
        }

        // expand repeatable structure
        if (currentField.getProperty("class").contains("elLayouts-Form-Struct")) {
            currentField.as(FormStructure.class).expand();
        }
        return this;
    }

    protected UiComponent getDescendantBySelector(final String selector) {
        this.waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return !fieldBody.getDescendantsBySelector(selector).isEmpty();
            }
        });
        return fieldBody.getDescendantsBySelector(selector).get(0);
    }

    protected FormField getParentField() {
        return parentField;
    }

    protected void setPopupDatePickerValue(String stringValue) {
        try {
            PopupDatePicker datePicker = getDescendantBySelector(".elWidgets-PopupDatePicker")
                    .as(PopupDatePicker.class);
            datePicker.selectDate(datePicker.getDateFormat().parse(stringValue));
        } catch (ParseException e) {
            throw new UiException("Problem while parsing PopupDatePicker value", e);
        }
    }

    protected void setSwitcherValue(String stringValue) {
        boolean booleanValue = Boolean.valueOf(stringValue);

        // handle "enabled" and "disabled" strings
        if (stringValue.equalsIgnoreCase(SWITCHER_ENABLED_TEXT)) {
            booleanValue = true;
        } else if (stringValue.equalsIgnoreCase(SWITCHER_DISABLED_TEXT)) {
            booleanValue = false;
        }
        getDescendantBySelector(".ebSwitcher").as(Switcher.class).setValue(booleanValue);
    }

    protected void setValue(Collection<String> stringCollectionValues) {

        final String stringValue = stringCollectionValues.isEmpty() ? null : stringCollectionValues.iterator().next();

        final String fieldClassName = fieldBody.getProperty("class").trim();
        switch (fieldClassName) {
        case "elLayouts-Form-Text":
        case "elLayouts-Form-Password":
            getDescendantBySelector("input").as(TextBox.class).setText(stringValue);
            break;

        case "elLayouts-Form-InputTextArea":
            getDescendantBySelector("textarea").as(TextBox.class).setText(stringValue);
            break;

        case "elLayouts-Form-select":
            getDescendantBySelector(".ebSelect").as(UiSdkSelectBox.class).setValue(stringValue);
            break;

        case "elLayouts-Form-combo":
            getDescendantBySelector(".ebCombobox").as(UiSdkComboBox.class).setValue(stringValue);
            break;

        case "elLayouts-Form-combomultiselect":
            getDescendantBySelector(".ebComboMultiSelect").as(UiSdkComboMultiSelectBox.class)
                    .setValues(stringCollectionValues);
            break;

        case "elLayouts-Form-multiselect":
            getDescendantBySelector(".ebSelect").as(UiSdkMultiSelectBox.class)
                    .setValues(stringCollectionValues);
            break;

        case "elLayouts-Form-Switcher":
            setSwitcherValue(stringValue);
            break;

        case "elLayouts-Form-RadioGroup":
            fieldBody.as(RadioButtonGroup.class).setValue(stringValue);
            break;

        case "elLayouts-Form-Spinner":
            getDescendantBySelector(".ebSpinner").as(Spinner.class).setValue(Integer.parseInt(stringValue));
            break;

        case "elLayouts-Form-PopupDatePicker":
            setPopupDatePickerValue(stringValue);
            break;

        case "elLayouts-Form-CheckboxGroup":
            fieldBody.as(UiSdkCheckBoxGroup.class).setValues(stringCollectionValues);
            break;

        default:
            throw new UnsupportedOperationException(
                    "Can't fill form field with class '" + fieldClassName + "'. Field type is not supported.");
        }
    }
}
