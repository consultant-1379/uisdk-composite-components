package com.ericsson.cds.uisdk.compositecomponents.paginatedtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkSelectBox;
import com.ericsson.cds.uisdk.compositecomponents.pagination.Pagination;
import com.ericsson.cds.uisdk.compositecomponents.pinnedcolumntable.PinnedColumnTable;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

/**
 * Composite Component for handling UI SDK Paginated table
 * 
 * @since 1.0.34
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#examples/paginated-table">
 *      Paginated Table example</a>
 * @author ehrvkla
 */
public class PaginatedTable extends PinnedColumnTable {

    protected final static String TABLE_SIZE_FORMAT = "%d Items";

    @UiComponentMapping("header .ebSelect")
    private UiSdkSelectBox pagesizeSelectBox;

    @UiComponentMapping("footer .ebPagination")
    private Pagination pagination;

    public int getCurrentPage() {
        return getPagination().getCurrentPage();
    }

    public int getPageCount() {
        return getPagination().getPageCount();
    }

    public int getPaginationTableSize() {
        return parsePaginationSelectBoxItem(getPagesizeSelectBox().getValue());
    }

    public List<Integer> getSupportedTableSizes() {
        List<Integer> supportedTableSizes = new ArrayList<>();
        for (String itemString : getPagesizeSelectBox().getItems()) {
            supportedTableSizes.add(parsePaginationSelectBoxItem(itemString));
        }

        return supportedTableSizes;
    }

    public PaginatedTable goToFirstPage() {
        int numberOfPages = getPageCount();
        if (numberOfPages != 1) {
            goToPage(1);
        }

        return this;
    }

    public PaginatedTable goToLastPage() {
        int numberOfPages = getPageCount();
        if (numberOfPages != 1) {
            goToPage(numberOfPages);
        }

        return this;
    }

    public PaginatedTable setPaginationTableSize(int tableSize) {
        getPagesizeSelectBox().setValue(String.format(TABLE_SIZE_FORMAT, tableSize));
        return this;
    }

    @Override
    public PaginatedTable waitToLoad() {
        waitUntil(getLoader(), UiComponentPredicates.HIDDEN);
        return this;
    }

    protected UiSdkSelectBox getPagesizeSelectBox() {
        return pagesizeSelectBox;
    }

    protected Pagination getPagination() {
        return pagination;
    }

    protected PaginatedTable goToPage(int pageIndex) {
        getPagination().goTo(pageIndex, 1);

        waitToLoad();
        return this;
    }

    List<HashMap<String, String>> getFirstNItems(int numberOfItems) {
        List<HashMap<String, String>> firstNItems = new ArrayList<>();

        for (int currentPageNumber : getPagination()) {
            waitToLoad();

            int itemsToAddCount = numberOfItems - firstNItems.size();

            List<HashMap<String, String>> currentlyDisplayedItems = getCurrentDisplayedItems();
            if (currentlyDisplayedItems.size() >= itemsToAddCount) {
                firstNItems.addAll(currentlyDisplayedItems.subList(0, itemsToAddCount));
                return firstNItems;
            } else {
                firstNItems.addAll(currentlyDisplayedItems);
            }
        }

        return firstNItems;
    }

    private int parsePaginationSelectBoxItem(String itemText) {
        return Integer.parseInt(itemText.replaceAll("[a-zA-Z]*", "").trim());
    }
}
