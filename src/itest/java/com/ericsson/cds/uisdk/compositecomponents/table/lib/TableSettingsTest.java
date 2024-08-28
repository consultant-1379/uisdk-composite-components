package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TableSettingsTest extends AbstractWidgetLibraryTest {

    private UiSdkTableLibViewModel tableView;

    @Test
    public void hideAllColumns() {
        tableView.getTableSettings().hideAllColumns();
        List<String> tableColumns = tableView.getTableSettings().getSelectedItems();

        List<String> expectedColumns = new ArrayList<>();
        expectedColumns.add("Username"); // hardcoded (non removable) column
        assertEquals(expectedColumns, tableColumns);
    }

    @Test
    public void hideColumns() {
        tableView.getTableSettings().hideColumns("First Name", "Last Login", "Status");
        List<String> tableColumns = tableView.getTableSettings().getSelectedItems();

        List<String> expectedColumns = new ArrayList<>();
        expectedColumns.add("Username"); // hardcoded (non removable) column
        expectedColumns.add("Last Name");
        expectedColumns.add("Role");
        expectedColumns.add("e-mail");
        assertEquals(expectedColumns, tableColumns);
    }

    @Before
    public void initializeView() {
        tableView = tab.getView(UiSdkTableLibViewModel.class);
        tableView.ensurePageIsLoaded(tab);
    }

    @Test
    public void showAllColumns() {
        tableView.getTableSettings().showAllColumns();
        List<String> tableColumns = tableView.getTableSettings().getSelectedItems();

        List<String> expectedColumns = new ArrayList<>();
        expectedColumns.add("Username"); // hardcoded (non removable) column
        expectedColumns.add("Status");
        expectedColumns.add("First Name");
        expectedColumns.add("Last Name");
        expectedColumns.add("Role");
        expectedColumns.add("e-mail");
        expectedColumns.add("Last Login");
        assertEquals(expectedColumns, tableColumns);
    }

    @Test
    public void showColumns() {
        tableView.getTableSettings().showColumns("First Name", "Last Login", "Status");
        List<String> tableColumns = tableView.getTableSettings().getSelectedItems();

        List<String> expectedColumns = new ArrayList<>();
        expectedColumns.add("Username"); // hardcoded (non removable) column
        expectedColumns.add("Status");
        expectedColumns.add("First Name");
        expectedColumns.add("Last Name");
        expectedColumns.add("Role");
        expectedColumns.add("e-mail");
        expectedColumns.add("Last Login");
        assertEquals(expectedColumns, tableColumns);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/paginated-table-layout";
    }
}
