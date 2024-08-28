package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;

public class SimpleSelectionTableTest extends AbstractWidgetLibraryTest {

    private Table table;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        UiSdkTableLibViewModel tableView = tab.getView(UiSdkTableLibViewModel.class);
        tableView.ensurePageIsLoaded(tab);
        table = tableView.getTable();
    }

    @Test
    public void selectRows() {
        assertEquals(0, table.getCheckedRows().size());

        table.checkRows(0, 2, 3);
        assertEquals(3, table.getCheckedRows().size());
    }

    @Test
    public void deselectRows() {
        assertEquals(0, table.getCheckedRows().size());

        table.checkAllRows();
        assertEquals(10, table.getCheckedRows().size());

        table.uncheckRows(0, 2, 7);
        assertEquals(7, table.getCheckedRows().size());

    }

    @Test
    public void selectDeselectAllRows() {
        assertEquals(0, table.getCheckedRows().size());

        table.checkAllRows();
        assertEquals(10, table.getCheckedRows().size());

        table.uncheckAllRows();
        assertEquals(0, table.getCheckedRows().size());
    }

    @Test
    public void getSelectedRows() {
        assertEquals(0, table.getCheckedRows().size());
        table.checkRows(0, 1);

        assertEquals(2, table.getCheckedRows().size());

        BodyRow bodyRow = table.getCheckedRows().get(1);
        String firstNameCellText = bodyRow.getCell(1).getText();
        assertEquals("John", firstNameCellText);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/simple-selection-table";
    }
}
