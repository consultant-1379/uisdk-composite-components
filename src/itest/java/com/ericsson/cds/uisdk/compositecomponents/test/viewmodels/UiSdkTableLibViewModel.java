package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.paginatedtable.PaginatedTable;
import com.ericsson.cds.uisdk.compositecomponents.table.lib.Table;
import com.ericsson.cds.uisdk.compositecomponents.table.lib.TableSettings;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkTableLibViewModel extends GenericViewModel {

    @UiComponentMapping(".eaFlyout .elTablelib-wFormControls-apply")
    private Button flyoutPanelApplyButton;

    @UiComponentMapping(".eaPaginatedTable-wUserTable")
    private PaginatedTable paginatedTable;

    @UiComponentMapping(".eaQuickFilterTable-wUserTable-filter")
    private Button quickFilterToggleButton;

    @UiComponentMapping(".elTablelib-Table")
    private Table table;

    @UiComponentMapping(".elTablelib-TableSettings")
    private TableSettings tableSettings;

    @UiComponentMapping(".elTablelib-lTableHeader-settings")
    private Button tableSettingsButton;

    @UiComponentMapping(".elTablelib-lPaginatedTable .elWidgets-Loader")
    private UiComponent loader;

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(table, tab, this);
        table.waitToLoad();
    }

    public Button getFlyoutPanelApplyButton() {
        return flyoutPanelApplyButton;
    }

    public PaginatedTable getPaginatedTable() {
        return paginatedTable;
    }

    public Button getQuickFilterToggleButton() {
        return quickFilterToggleButton;
    }

    public Table getTable() {
        table.waitToLoad();
        return table;
    }

    public TableSettings getTableSettings() {
        if (tableSettings.isDisplayed()) {
            return tableSettings;
        }

        waitUntilComponentIsHidden(loader);

        waitUntilComponentIsHidden(tableSettings);
        waitUntilComponentIsDisplayed(tableSettingsButton);

        tableSettingsButton.click();
        waitUntilComponentIsDisplayed(tableSettings);
        return tableSettings;
    }
}
