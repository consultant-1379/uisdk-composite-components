package com.ericsson.cds.uisdk.compositecomponents.form;

import java.util.Collection;

import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.sdk.Button;

public class FormStructure extends FormField {

    @UiComponentMapping(selector = ".elLayouts-Form-StructBody-headerExpander .ebIcon.ebIcon_downArrow")
    protected Button collapseButton;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "//*[contains(@class, 'elLayouts-Form-StructBody-header')]/*[contains(@class, 'elLayouts-Form-StructBody-delete')]") // get only first level children /*
    protected Button deleteButton;

    @UiComponentMapping(selector = ".elLayouts-Form-StructBody-headerExpander .ebIcon.ebIcon_rightArrow")
    protected Button expandButton;

    @UiComponentMapping(selector = ".elLayouts-Form-StructBody-body .elLayouts-Form-Render")
    protected Form subForm;

    @UiComponentMapping(selector = ".elLayouts-Form-StructBody-body .elLayouts-Form-Render .elLayouts-Form-Field")
    protected FormField formField;

    @Override
    protected void clear() {
        getSubForm().clear();
    }

    protected FormStructure collapse() {
        if (isExpanded()) {
            collapseButton.click();
            waitUntil(expandButton, UiComponentPredicates.DISPLAYED);
            waitUntil(subForm, UiComponentPredicates.HIDDEN);
        }
        return this;
    }

    /**
     * Used to delete form structure if setValue with empty string
     */
    protected void delete() {
        deleteButton.click();
    }

    protected FormStructure expand() {
        if (!isExpanded()) {
            expandButton.click();
            waitUntil(collapseButton, UiComponentPredicates.DISPLAYED);
            waitUntil(subForm, UiComponentPredicates.DISPLAYED);
        }
        return this;
    }

    protected Form getSubForm() {
        expand();
        return subForm;
    }

    protected boolean isExpanded() {
        return collapseButton.isDisplayed();
    }

    @Override
    protected void setValue(Collection<String> stringCollectionValues) {
        expand();

        // if structure value is set to [] or "", it should be deleted
        if (stringCollectionValues.isEmpty() || stringCollectionValues.iterator().next().isEmpty()) {
            delete();
        } else if (stringCollectionValues.isEmpty()) {
            formField.setValue(stringCollectionValues);
        } else {
            throw new UnsupportedOperationException(
                    "Can't set value to Form Structure element. Value: " + stringCollectionValues.toArray().toString());
        }
    }
}
