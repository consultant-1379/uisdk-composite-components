package com.ericsson.cds.uisdk.compositecomponents.dashboard;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.DashboardItem;
import com.ericsson.cds.uisdk.compositecomponents.table.lib.Table;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkDashboardViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DashboardTest extends AbstractWidgetLibraryTest {

    private final String pieChartTitle = "Pie Chart";
    private final String simpleLineChartTitle = "Simple Line Chart";
    private final String sampleTableTitle = "Sample Table";

    private Dashboard dashboard;
    private UiSdkDashboardViewModel dashboardView;

    @Before
    public void initializeView() {
        dashboardView = tab.getView(UiSdkDashboardViewModel.class);
        dashboard = dashboardView.getDashboard();
        dashboardView.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItems() {
        List<DashboardItem> items = dashboard.getItems();

        assertEquals(8, items.size());
    }

    @Test
    public void getItemByTitle() {
        DashboardItem item = dashboard.getItem(pieChartTitle);
        assertTrue(item.isDisplayed());
    }

/*    @Test
    public void getDashboardItemWidget() {
        Table table = dashboard.getDashboardItemWidget(sampleTableTitle, Table.class);

        assertTrue(table.getBodyRows().size() > 0);
    }*/

    @Test
    public void closeItem() {
        int numberOfItems = dashboard.getItems().size();
        dashboard.getItems().get(0).close();

        assertEquals(numberOfItems - 1, dashboard.getItems().size());
    }

    @Test
    public void closeItems() {
        int numberOfItems = dashboard.getItems().size();
        dashboard.closeItems(null, pieChartTitle, simpleLineChartTitle);

        assertEquals(numberOfItems - 2, dashboard.getItems().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void closeUnclosableItem() {
        DashboardItem item = dashboard.getItem("This widget can not be closed");
        item.close();
    }

    @Test
    public void maximizeAndMinimizeItem() {
        final String dashboardItemTitle = simpleLineChartTitle;

        // maximize
        dashboard.getItem(dashboardItemTitle).maximize();
        DashboardItem firstItem = dashboard.getItems().get(0);

        assertEquals(dashboardItemTitle.toLowerCase(), firstItem.getTitle().toLowerCase()); // when maximized, dashboard item is on the top of the dashboard

        // minimize
        dashboard.getItem(dashboardItemTitle).minimize();
        firstItem = dashboard.getItems().get(0);
        assertNotEquals(dashboardItemTitle, firstItem.getTitle());
    }

    @Test
    public void waitForDashboardItemToShow() {
        dashboard.waitForDashboardItemToShow(pieChartTitle);
    }

    @Test
    public void waitForDashboardItemToHide() {
        dashboard.getItem(pieChartTitle).close();
        dashboard.waitForDashboardItemToHide(pieChartTitle);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#dashboard";
    }
}
