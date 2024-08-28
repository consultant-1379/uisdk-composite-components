package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.modaldialog.ModalDialog;
import com.ericsson.cds.uisdk.compositecomponents.table.lib.Table;
import com.ericsson.cds.uisdk.compositecomponents.topsection.TopSection;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkTopSectionViewModel extends GenericViewModel {

    @UiComponentMapping(".ebDialog")
    private ModalDialog modalDialog;

    @UiComponentMapping(".elTablelib-Table")
    private Table table;

    @UiComponentMapping(".elLayouts-QuickActionBarWrapper")
    private TopSection topSection;

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(topSection, tab, this);
    }

    public ModalDialog getModalDialog() {
        waitUntilComponentIsDisplayed(modalDialog);
        return modalDialog;
    }

    public Table getTable() {
        return table;
    }

    public TopSection getTopSection() {
        return topSection;
    }

}
