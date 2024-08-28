package com.ericsson.cds.uisdk.compositecomponents.pagination;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkPaginationViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PaginationTest extends AbstractWidgetLibraryTest {

    private Pagination pagination;
    private UiSdkPaginationViewModel paginationView;

    @Before
    public void initializeView() {
        paginationView = tab.getView(UiSdkPaginationViewModel.class);
        pagination = paginationView.getPagination();
        paginationView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testPaginationGoToPage() {
        int pageToGo = 25;
        pagination.goTo(pageToGo, 3);
        pagination.getCurrentPage();
        int currentPage = pagination.getCurrentPage();
        assertEquals(pageToGo, currentPage);
    }

    @Test
    public void testPaginationGoToPageThatDoesNotExist() {
        int pageToGo = 40;
        pagination.goTo(pageToGo, 3);
        pagination.getCurrentPage();
        int currentPage = pagination.getCurrentPage();
        assertNotEquals(pageToGo, currentPage);
    }

    @Test
    public void testPaginationPageCount() {
        int pageCount = pagination.getPageCount();
        assertEquals(30, pageCount);
    }

    @Test
    public void testPaginationIterator() {

        int pageCount = 0;
        for (Integer currentPageNumber : pagination) {
            pageCount++;
        }

        assertEquals(30, pageCount);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/pagination";
    }
}
