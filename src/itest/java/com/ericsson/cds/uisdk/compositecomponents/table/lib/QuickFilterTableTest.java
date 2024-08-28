package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import static org.junit.Assert.assertTrue;

public class QuickFilterTableTest extends AbstractWidgetLibraryTest {

    private static final String WIDGET_URL = "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/quick-filter-table";

    private Table table;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        UiSdkTableLibViewModel tableView = tab.getView(UiSdkTableLibViewModel.class);
        table = tableView.getTable();
        tableView.ensurePageIsLoaded(tab);
        tableView.getQuickFilterToggleButton().click();
    }

    @Override
    protected String getWidgetUrl() {
        return WIDGET_URL;
    }

    private void validateFilter(String columnName, String filterValue) {

        for (BodyRow bodyRow : table.getBodyRows()) {
            final String bodyCellText = bodyRow.getCell(columnName).getText();
            assertTrue(
                    "Filter validation failed. Table column " + columnName + " should contain cells matching filter '"
                            + filterValue + "', but found '"
                            + bodyCellText + "'.",
                    bodyCellText.toLowerCase().contains(filterValue.toLowerCase()));

            break; // TODO how to validate the filter? too slow if validating each row
        }
    }

    @Test
    public void setDateFilter() {
        Date today = new LocalDate().toDate();
        final String filterDateValue = table.getHeadRow().getQuickFilter().setDateFilter("Registration Date", today);

        validateFilter("Registration Date", filterDateValue);
    }

    @Test
    public void setMultiSelectFilter() {
        List<String> filterValue = new ArrayList<>();
        filterValue.add("Administrator Role");
        table.getHeadRow().getQuickFilter().setMultiSelectFilter("Role", filterValue);

        validateFilter("Role", filterValue.get(0).toString());
    }

    @Test
    public void setSelectFilter() {
        table.getHeadRow().getQuickFilter().setSelectFilter("Status", "Restricted");

        validateFilter("Status", "Restricted");
    }

    @Test
    public void setTextFilter() {
        final String filterValue = "jamesmit";
        table.getHeadRow().getQuickFilter().setTextFilter("Id", filterValue);

        validateFilter("Id", filterValue);
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void setTextFilterToNotExistingColumn() {
        table.getHeadRow().getQuickFilter().setTextFilter("WRONG_COLUMN_NAME", "filter value");
    }

}
