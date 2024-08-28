package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.form.Form;
import com.ericsson.cds.uisdk.compositecomponents.form.FormField;
import com.ericsson.cds.uisdk.compositecomponents.form.FormStructure;
import com.ericsson.cds.uisdk.compositecomponents.topsection.TopSection;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkFormViewModel extends GenericViewModel {

    @UiComponentMapping(value = ".elLayouts-Form")
    private Form form;

    @UiComponentMapping(value = "div[data-path='structure.nested_structure.field1'] input")
    private FormField nestedField;

    @UiComponentMapping(value = "div[data-path='structure']")
    private FormStructure structureField;

    @UiComponentMapping(".elLayouts-QuickActionBarWrapper")
    private TopSection topSection;

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(form, tab, this);
    }

    public Form getForm() {
        return form;
    }

    public FormField getNestedField() {
        return nestedField;
    }

    public FormStructure getStructureField() {
        return structureField;
    }

    public TopSection getTopSection() {
        return topSection;
    }
}
