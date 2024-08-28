package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class BodyRowTest extends AbstractWidgetLibraryTest {

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
    public void testGetCellItems() {

        List<String> expectedItems = new ArrayList<>();
        expectedItems.add("Phoenix");
        expectedItems.add("Wright");
        expectedItems.add("Defense Attorney");

        List<String> cellItems = table.getBodyRow(0).getCellItems();
        assertTrue(cellItems.equals(expectedItems));
    }

}
