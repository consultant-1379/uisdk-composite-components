package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableLibTest extends AbstractWidgetLibraryTest {

    private static final String WIDGET_URL = "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#example1";

    private Table table;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        UiSdkTableLibViewModel tableView = tab.getView(UiSdkTableLibViewModel.class);
        table = tableView.getTable();
        tableView.ensurePageIsLoaded(tab);
    }

    @Override
    protected String getWidgetUrl() {
        return WIDGET_URL;
    }

    @Test
    public void testGetHeadRow() {
        assertNotNull(table.getHeadRow());
    }

    @Test
    public void testGetBodyRow() {
        assertNotNull(table.getBodyRow(0));
    }

    @Test
    public void testGetCell() {
        assertEquals("Defense Attorney", table.getBodyRow(0).getCell(2).getText());
    }

    @Test
    public void testSort() {
        table.getHeadRow().getCell(1).sort();
        assertEquals("Prosecutor", table.getBodyRow(0).getCell(2).getText());
    }

    @Test
    public void getColumnIndexByCellName() {
        assertEquals(0, table.getHeadRow().getColumnIndexByCellName("First Name"));
        assertEquals(1, table.getHeadRow().getColumnIndexByCellName("Last Name"));
        assertEquals(2, table.getHeadRow().getColumnIndexByCellName("Occupation"));
    }

    @Test
    public void getColumnHeaders() {

        List<String> expectedHeaders = new ArrayList<>();
        expectedHeaders.add("First Name");
        expectedHeaders.add("Last Name");
        expectedHeaders.add("Occupation");

        List<String> headers = table.getColumnHeaders();

        assertTrue(headers.equals(expectedHeaders));
    }

    @Test
    public void getTableData() {

        final String row1 = "Phoenix, Wright, Defense Attorney";
        final String row2 = "Miles, Edgeworth, Prosecutor";
        final String row3 = "Okabe, Rintaro, Scientist";
        final String row4 = "Eren, Jaeger, Military";
        final String row5 = "Ema, Skye, Investigator";

        List<String> row1List = Arrays.asList(row1.split("\\s*,\\s*"));
        List<String> row2List = Arrays.asList(row2.split("\\s*,\\s*"));
        List<String> row3List = Arrays.asList(row3.split("\\s*,\\s*"));
        List<String> row4List = Arrays.asList(row4.split("\\s*,\\s*"));
        List<String> row5List = Arrays.asList(row5.split("\\s*,\\s*"));

        List<List<String>> expectedTableData = new ArrayList<>();
        expectedTableData.add(row1List);
        expectedTableData.add(row2List);
        expectedTableData.add(row3List);
        expectedTableData.add(row4List);
        expectedTableData.add(row5List);

        List<List<String>> tableData = table.getTableData();
        assertEquals(expectedTableData, tableData);
    }

    @Test
    public void waitUntilRowAppears() {
        assertNotNull(table.waitUntilRowAppears("First Name", "Phoenix"));

        assertNotNull(table.waitUntilRowAppears("First Name", "Phoenix", "Last Name", "Wright"));

        assertNull(table.waitUntilRowAppears("First Name", "non existing cell"));
    }

    @Test
    public void waitUntilRowAppearsNegative() {
        assertNull(table.waitUntilRowAppears("First Name", "non existing name"));
    }

    @Test
    public void waitUntilRowHide() {
        assertNotNull(table.waitUntilRowsHide("First Name", "non existing name"));
    }

    @Test(expected = WaitTimedOutException.class)
    public void waitUntilRowHideNegative() {
        // this must return null because 
        table.waitUntilRowsHide("First Name", "Phoenix");
    }

}
