package com.ericsson.cds.uisdk.compositecomponents.paginatedtable;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTableLibViewModel;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PaginatedTableTest extends AbstractWidgetLibraryTest {

    private final static int PAGE_COUNT = 5000;
    private final static int PAGINATION_TABLE_SIZE = 10;

    private PaginatedTable paginatedTable;
    private UiSdkTableLibViewModel tableView;

    @Before
    public void initializeView() {
        tableView = tab.getView(UiSdkTableLibViewModel.class);
        tableView.ensurePageIsLoaded(tab);
        paginatedTable = tableView.getPaginatedTable();
    }

    @Test
    public void getCurrentPage() {
        int currentPage = paginatedTable.getCurrentPage();
        assertEquals(1, currentPage);
    }

    @Test
    public void getPageCount() {
        int pageCount = paginatedTable.getPageCount();
        assertEquals(PAGE_COUNT, pageCount);
    }

    @Test
    public void getPaginationTableSize() {
        assertEquals(PAGINATION_TABLE_SIZE, paginatedTable.getPaginationTableSize());
    }

    @Test
    public void getSupportedTableSizes() {
        List<Integer> supportedTableSizes = paginatedTable.getSupportedTableSizes();

        List<Integer> correctSupportedTableSizes = new ArrayList<Integer>();
        correctSupportedTableSizes.add(10);
        correctSupportedTableSizes.add(25);
        correctSupportedTableSizes.add(50);
        correctSupportedTableSizes.add(75);
        correctSupportedTableSizes.add(100);

        assertEquals(correctSupportedTableSizes, supportedTableSizes);
    }

    @Test
    public void goToLastPage() {
        paginatedTable.goToLastPage();
        assertEquals(PAGE_COUNT, paginatedTable.getCurrentPage());
    }

    @Test
    public void goToFirstPage() {
        paginatedTable.goToLastPage();
        assertEquals(PAGE_COUNT, paginatedTable.getCurrentPage());

        paginatedTable.goToFirstPage();
        assertEquals(1, paginatedTable.getCurrentPage());
    }

    @Test
    public void setPaginationTableSize() {
        final int paginationSize = 50;
        paginatedTable.setPaginationTableSize(paginationSize);
        assertEquals(paginationSize, paginatedTable.getPaginationTableSize());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/paginated-table";
    }
}
