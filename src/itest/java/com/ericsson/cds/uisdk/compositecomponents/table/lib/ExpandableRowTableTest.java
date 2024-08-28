package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExpandableRowTableTest extends AbstractWidgetLibraryTest {

    private Table table;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        UiSdkTableLibViewModel tableView = tab.getView(UiSdkTableLibViewModel.class);
        tableView.ensurePageIsLoaded(tab);
        table = tableView.getTable();
        table.waitToLoad();
    }

    @Test
    public void expandRow() {
        UiComponent expandedRowContent = table.expandRow(1, UiComponent.class);
        String contentText = expandedRowContent.getText();
        assertTrue(contentText.contains("1 Lorem ipsum dolor sit amet, consectetur adipiscing elit, "));
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/expandable-row-table";
    }
}
