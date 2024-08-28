package com.ericsson.cds.uisdk.compositecomponents.form;

import java.util.List;

import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.google.common.collect.Lists;

public class RepeatableFormField extends FormField {

    @UiComponentMapping(selector = ".elLayouts-Form-AddButton")
    protected Button addButton;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "//*[contains(@class, 'elLayouts-Form-Field-body')]/*[contains(@class, 'elLayouts-Form-FieldBody')]/*[contains(@class, 'elLayouts-Form-FieldBody-delete')]") // get only first level children /*
    protected List<Button> fieldDeleteButtons;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "/*[contains(@class, 'elLayouts-Form-Struct-body')]/*[contains(@class, 'elLayouts-Form-StructBody')]/*[contains(@class, 'elLayouts-Form-StructBody-header')]/*[contains(@class, 'elLayouts-Form-StructBody-delete')]") // get only first level children /*
    protected List<Button> structDeleteButtons;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "//*[contains(@class, 'elLayouts-Form-Field-body')]/*[@data-path]") // get only first level children /*
    protected List<FormField> subFieldsFirstLevelField;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "//*[contains(@class, 'elLayouts-Form-Struct-body')]/*[@data-path]") // get only first level children /*
    protected List<FormField> subFieldsFirstLevelStruct;

    @UiComponentMapping(selector = ".elLayouts-Form-StructBody-body")
    protected Form subForm;

    private Boolean isStructureField; // used as cache

    protected FormField addField() {
        final int fieldsCount = getFieldsCount();
        addButton.click();
        waitUntilFieldsCount(fieldsCount + 1);

        return getSubFields().get(getSubFields().size() - 1);
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cds.uisdk.compositecomponents.form.FormField#clear()
     */
    @Override
    protected void clear() {
        // delete all fields, reverse is important
        expand();
        for (Button closeButton : Lists.reverse(getDeleteButtons())) {
            closeButton.click();
        }
        waitUntilFieldsCount(0);
    }

    protected FormField ensureFieldAtIndex(int fieldIndex) {
        expand();

        // calculate if any additional field required and add if any
        final int additionalFieldsRequired = fieldIndex - getSubFields().size();
        for (int i = 0; i < additionalFieldsRequired; i++) {
            addField();
        }

        // expand created field
        FormField ensuredField = getSubFields().get(fieldIndex - 1).as(FormField.class);
        if (ensuredField.getProperty("class").contains("elLayouts-Form-StructBody")) {
            return ensuredField.as(FormStructure.class).expand();
        }

        return ensuredField;
    }

    protected void expand() {
        if (isStructureField()) {
            this.as(FormStructure.class).expand();
        }
    }

    protected List<Button> getDeleteButtons() {
        return isStructureField() ? structDeleteButtons : fieldDeleteButtons;
    }

    protected int getFieldsCount() {
        return getDeleteButtons().size();
    }

    protected List<FormField> getSubFields() {
        return isStructureField() ? subFieldsFirstLevelStruct : subFieldsFirstLevelField;
    }

    protected Form getSubForm() {
        expand();
        return subForm;
    }

    protected boolean isStructureField() {
        if (isStructureField == null) {
            isStructureField = this.getProperty("class").contains("elLayouts-Form-Struct");
        }
        return isStructureField;
    }

    protected void waitUntilFieldsCount(final int fieldsCount) {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return getFieldsCount() == fieldsCount;
            }
        });
    }
}
