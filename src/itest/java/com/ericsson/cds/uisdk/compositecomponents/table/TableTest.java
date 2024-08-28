package com.ericsson.cds.uisdk.compositecomponents.table;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableViewModel;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore("Deprecated Table example no longer exists")
@Deprecated
public class TableTest extends AbstractWidgetLibraryTest {

    private Table table;
    private UiSdkTableViewModel tableView;

    @Before
    public void initializeView(){
        tableView = tab.getView(UiSdkTableViewModel.class);
        table = tableView.getTable();
    }

    @Test
    public void testGetBodyCell(){
        assertEquals("Uzumaki", table.getBodyRow(0).getCell(0));
    }

    @Test
    public void testGetHeadCell(){
        assertEquals("Last Name", table.getHeadRow().getCell(0));
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html";
    }
}