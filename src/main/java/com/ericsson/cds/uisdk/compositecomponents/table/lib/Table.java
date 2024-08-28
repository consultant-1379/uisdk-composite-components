package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cds.uisdk.compositecomponents.flyout.FlyoutPanel;
import com.ericsson.cds.uisdk.compositecomponents.table.TableHelper;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Composite Component for handling UI SDK table.
 *
 * @see <a href=https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
 *      latest/examples/#examples/expandable-row-table>Table</a>
 */
public class Table extends AbstractUiComponent {

    /** The Constant LOGGER. */
    final static Logger LOGGER = LoggerFactory.getLogger(Table.class);

    /** The pretable selector. */
    // pretable contains cloned main table elements (some of them), it is used for some table plugins, IMPORTANT- as it is ABOVE the table wrapper elements, it can sometimes disable click()
    protected final String PRETABLE_SELECTOR = ".elTablelib-Table-pretable table";

    /** The table wrapper selector. */
    protected final String TABLE_WRAPPER_SELECTOR = ".elTablelib-Table-wrapper table";

    /** The table body selector. */
    protected final String TABLE_BODY_SELECTOR = TABLE_WRAPPER_SELECTOR + " tbody";

    /** The table head selector. */
    protected final String TABLE_HEAD_SELECTOR = TABLE_WRAPPER_SELECTOR + " thead";

    /** The body rows. */
    @UiComponentMapping(TABLE_BODY_SELECTOR + " .ebTableRow.elTablelib-row")
    protected List<BodyRow> bodyRows;

    /** The expandable row content. */
    @UiComponentMapping(TABLE_BODY_SELECTOR + " .ebTable-expandableRow")
    protected List<UiComponent> expandableRowContent;

    /** The head row. */
    @UiComponentMapping(TABLE_HEAD_SELECTOR)
    protected HeadRow headRow;

    /** The loader. */
    @UiComponentMapping(".ebLoader-Dots")
    protected UiComponent loader;

    /** The pretable head selector. */
    protected final String PRETABLE_HEAD_SELECTOR = PRETABLE_SELECTOR + " thead";

    /** The pretable head row. */
    @UiComponentMapping(PRETABLE_HEAD_SELECTOR)
    protected HeadRow pretableHeadRow;

    /** The flyout panel. */
    @GlobalScope
    @UiComponentMapping(".eaFlyout .eaFlyout-panel")
    private FlyoutPanel flyoutPanel;

    /** The variable that is used as helper variable in waitUntilRowAppears method. */
    private int rowIndexHelper;

    /** The settings button. */
    @UiComponentMapping(".ebIcon.ebIcon_settings")
    private UiComponent settingsButton;

    /** The table body. */
    @UiComponentMapping(TABLE_BODY_SELECTOR)
    private UiComponent tableBody;

    /** The table settings. */
    @GlobalScope
    @UiComponentMapping(".elTablelib-TableSettings")
    private TableSettings tableSettings;

    /** The table row cell offsets. */
    Pair<Integer, Integer> TABLE_ROW_CELL_OFFSETS;

    /**
     * Check all rows.
     */
    public void checkAllRows() {
        getHeadRow().check();
    }

    /**
     * Check items.
     *
     * @param columnName the column name
     * @param items the items
     */
    public void checkItems(String columnName, String... items) {
        this.checkUncheckItems(true, columnName, items);
    }

    /**
     * Check rows.
     *
     * @param rowsIndex the rows index
     */
    public void checkRows(int... rowsIndex) {
        for (int rowIndex : rowsIndex) {
            getBodyRow(rowIndex).check();
        }
    }

    /**
     * Deselect rows.
     *
     * @param rowsIndex the rows index
     */
    public void deselectRows(int... rowsIndex) {
        for (int rowIndex : rowsIndex) {
            getBodyRow(rowIndex).deselect();
        }
    }

    /**
     * Expand row.
     *
     * @param <T> the generic type
     * @param rowIndex the row index
     * @param contentClass class of expanded row's content clas
     * @return the expanded row's content
     */
    public <T extends UiComponent> T expandRow(int rowIndex, Class<T> contentClass) {
        getBodyRow(rowIndex).expand();

        T expandedRow = getExpandableRowContent().get(rowIndex).as(contentClass);
        waitUntil(expandedRow, UiComponentPredicates.DISPLAYED);
        return expandedRow;
    }

    /**
     * Gets the all items.
     *
     * @return the all items
     */
    public List<HashMap<String, String>> getAllItems() {
        return getCurrentDisplayedItems();
    }

    /**
     * Get a body row at the specified index in the table.
     *
     * @param rowIndex the row index
     * @return the body row
     * @since 0.1.1 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public BodyRow getBodyRow(int rowIndex) {
        Preconditions.checkArgument(rowIndex > -1, "rowIndex should be non negative");
        Preconditions.checkArgument(rowIndex < getBodyRows().size(), String
                .format("rowIndex %d should not be greater than number of rows, %d", rowIndex, getBodyRows().size()));

        return getBodyRows().get(rowIndex);
    }

    /**
     * Get the rows in the table.
     *
     * @return bodyRows
     * @since 0.1.1 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public List<BodyRow> getBodyRows() {
        return bodyRows;
    }

    /**
     * Get a cell value at the specified indices in the table.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the cell value
     * @since 0.1.1 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public String getCellValue(int rowIndex, int columnIndex) {
        return getBodyRows().get(rowIndex).getCell(columnIndex).getText();
    }

    /**
     * Gets the checked rows.
     *
     * @return the checked rows
     */
    public List<BodyRow> getCheckedRows() {
        List<BodyRow> checkedRows = new ArrayList<>();
        for (BodyRow bodyRow : getBodyRows()) {
            if (bodyRow.isChecked()) {
                checkedRows.add(bodyRow);
            }
        }
        return checkedRows;
    }

    /**
     * Gets the column headers.
     *
     * @return the column headers
     */
    public List<String> getColumnHeaders() {
        List<String> headCellsStringList = new ArrayList<>();
        for (HeadCell headCell : getHeadRow().getCells()) {
            final String cellText = headCell.getText().trim();
            if (!cellText.isEmpty()) { // ignore empty columns
                headCellsStringList.add(cellText);
            }
        }
        return headCellsStringList;
    }

    /**
     * Get the row of headers for the table.
     *
     * @return headRow
     * @since 0.1.1 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public HeadRow getHeadRow() {
        // return pretable element exist (it has higher priority) otherwise return head element of main (wrapped) table
        return pretableHeadRow.exists() ? pretableHeadRow : headRow;
    }

    /**
     * Find out what direction the table is sorted in. @see SortDirection
     *
     * @param columnIndex the column index
     * @return the sort direction for column
     * @since 0.4.0 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public SortDirection getSortDirectionForColumn(int columnIndex) {
        HeadCell column = getHeadRow().getCell(columnIndex);
        return column.getSortDirection();
    }

    /**
     * Gets the table settings.
     *
     * @return the table settings
     */
    public TableSettings getTableSettings() {
        // close flyout if open
        if (flyoutPanel.isDisplayed()) {
            flyoutPanel.close();
        }

        settingsButton.click();
        try {
            waitUntil(tableSettings, UiComponentPredicates.DISPLAYED);
        } catch (WaitTimedOutException e) {
            throw new WaitTimedOutException("Cant open Table settings flyout!");
        }

        return tableSettings;
    }

    /**
     * Select rows.
     *
     * @param rowsIndex the rows index
     */
    public void selectRows(int... rowsIndex) {
        for (int rowIndex : rowsIndex) {
            getBodyRow(rowIndex).select();
        }
    }

    /**
     * Sort the table on the columnIndex specified.
     *
     * @param columnIndex the column index
     * @return true, if successful
     * @since 0.4.0 of Table Library @link
     *        https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/
     *        latest/revisions.html
     */
    public boolean sortByColumn(int columnIndex) {
        HeadCell columnToSortOn = getHeadRow().getCell(columnIndex);
        if (!columnToSortOn.isSortable())
            return false;
        columnToSortOn.sort();
        return columnToSortOn.isSorted();
    }

    /**
     * Uncheck all rows.
     */
    public void uncheckAllRows() {
        getHeadRow().uncheck();
    }

    /**
     * Uncheck items.
     *
     * @param columnName the column name
     * @param items the items
     * @return the table
     */
    public Table uncheckItems(String columnName, String... items) {
        return this.checkUncheckItems(false, columnName, items);
    }

    /**
     * Uncheck rows.
     *
     * @param rowsIndex the rows index
     */
    public void uncheckRows(int... rowsIndex) {
        for (int rowIndex : rowsIndex) {
            getBodyRow(rowIndex).uncheck();
        }
    }

    /**
     * Wait table to load.
     *
     * @return the table
     */
    public Table waitToLoad() {
        return this;
    }

    /**
     * Wait until row appears.
     *
     * @param columnName the column name
     * @param cellValue the cell value
     * @return the body row
     */
    public BodyRow waitUntilRowAppears(String columnName, String cellValue) {
        return waitUntilRowAppears(columnName, cellValue, null, null);
    }

    /**
     * Wait until row appears.
     *
     * @param columnName1 the column name 1
     * @param cellValue1 the cell value 1
     * @param columnName2 the column name 2
     * @param cellValue2 the cell value 2
     * @return the body row
     */
    public BodyRow waitUntilRowAppears(final String columnName1, final String cellValue1, final String columnName2,
            final String cellValue2) {

        showNeededColumns(columnName1, columnName2);

        try {
            waitUntil(new GenericPredicate() {

                @Override
                public boolean apply() {
                    rowIndexHelper = checkIfRowIsDisplayed(columnName1, cellValue1, columnName2, cellValue2);
                    return rowIndexHelper >= 0;
                }
            }, UI.getDefaultWaitTimeout());

        } catch (WaitTimedOutException e) {
            // print error
            printErrorMsg(true, columnName1, cellValue1, columnName2, cellValue2, false);
            return null;
        }

        // return body row
        return getBodyRow(rowIndexHelper).as(BodyRow.class);
    }

    /**
     * Wait until row hide.
     *
     * @param columnName1 the column name 1
     * @param cellValue1 the cell value 1
     * @param columnName2 the column name 2
     * @param cellValue2 the cell value 2
     * @return the table
     */
    public Table waitUntilRowHide(final String columnName1, final String cellValue1, final String columnName2,
            final String cellValue2) {

        showNeededColumns(columnName1, columnName2);

        try {
            waitUntil(new GenericPredicate() {

                @Override
                public boolean apply() {
                    rowIndexHelper = checkIfRowIsDisplayed(columnName1, cellValue1, columnName2, cellValue2);
                    return rowIndexHelper == -1;
                }
            });
        } catch (WaitTimedOutException e) {
            // print error
            printErrorMsg(false, columnName1, cellValue1, columnName2, cellValue2, true);
        }

        return this;
    }

    /**
     * Wait until rows hide.
     *
     * @param columnName the column name
     * @param cellValues the cell values
     * @return the table
     */
    public Table waitUntilRowsHide(String columnName, String... cellValues) {
        for (String cellValue : cellValues) {
            waitUntilRowHide(columnName, cellValue, null, null);
        }
        return this;
    }

    /**
     * Check if row is displayed.
     *
     * @param columnName1 the column name 1
     * @param cellValue1 the cell value 1
     * @param columnName2 the column name 2
     * @param cellValue2 the cell value 2
     * @return the row index, -1 if not found
     */
    protected int checkIfRowIsDisplayed(final String columnName1, final String cellValue1, final String columnName2,
            final String cellValue2) {

        final boolean checkAnotherCell = (columnName2 != null && cellValue2 != null);

        int rowIndex = 0;
        for (HashMap<String, String> rowData : getAllItems()) {
            // wait first cell
            if (TableHelper.hashMapContainsKeyValueIgnoreKeyCase(rowData, columnName1, cellValue1)) {
                if (checkAnotherCell) {
                    // wait second cell
                    if (TableHelper.hashMapContainsKeyValueIgnoreKeyCase(rowData, columnName2, cellValue2)) {
                        return rowIndex; // second cell found
                    } else {
                        continue; // second cell not found
                    }
                } else {
                    return rowIndex;
                }
            }
            rowIndex++;
        }

        return -1; // cell not found
    }

    /**
     * Gets the headers raw.
     *
     * @return the headers raw
     */
    protected List<String> getHeadersRaw() {
        UiComponent firstHeadRow = getHeadRow().getDescendantsBySelector(".ebTableRow.elTablelib-row:first-of-type").get(0); // dont get quick filter etc

        return TableHelper.getTextContentOfElementsByTagName(firstHeadRow, "th");
    }

    /**
     * Determine table row cell offset.
     *
     * @return the pair
     */
    public Pair<Integer, Integer> determineTableRowCellOffset() {
        List<String> headerCells = getHeadersRaw();
        if (headerCells.isEmpty()) {
            throw new UiComponentNotFoundException("Cant found Table headers!");
        }

        if (TABLE_ROW_CELL_OFFSETS == null) {
            int leftOffset = 0; // offset is number of fist N empty cells
            for (String header : headerCells) {
                if (!header.isEmpty())
                    break;
                leftOffset++;
            }

            int rightOffset = 0; // offset is number of fist N empty cells
            for (String header : Lists.reverse(headerCells)) {
                if (!header.isEmpty())
                    break;
                rightOffset++;
            }

            TABLE_ROW_CELL_OFFSETS = new ImmutablePair<>(leftOffset, rightOffset);
        }

        return TABLE_ROW_CELL_OFFSETS;
    }

    /**
     * Gets the current displayed items.
     *
     * @return the current displayed items
     */
    protected List<HashMap<String, String>> getCurrentDisplayedItems() {

        List<String> headers = getColumnHeaders();
        List<HashMap<String, String>> tableItems = new ArrayList<>();
        for (List<String> row : getTableData()) {
            HashMap<String, String> cells = new HashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                cells.put(headers.get(i), row.get(i));
            }
            tableItems.add(cells);
        }
        return tableItems;
    }

    /**
     * Gets the expandable row content.
     *
     * @return the expandable row content
     */
    protected List<UiComponent> getExpandableRowContent() {
        return expandableRowContent;
    }

    /**
     * Gets the loader.
     *
     * @return the loader
     */
    protected UiComponent getLoader() {
        return loader;
    }

    /**
     * Gets the table data.
     *
     * @return the table data
     */
    protected List<List<String>> getTableData() {
        determineTableRowCellOffset();

        List<List<String>> tableDataWithoutEmptyColumns = new ArrayList<>();
        for (List<String> rowData : getTableDataRaw()) {
            tableDataWithoutEmptyColumns.add(rowData.subList(TABLE_ROW_CELL_OFFSETS.getLeft(),
                    rowData.size() - TABLE_ROW_CELL_OFFSETS.getRight()));
        }

        return tableDataWithoutEmptyColumns;
    }

    /**
     * Gets the table data raw (with empty columns etc).
     *
     * @return the table data raw
     */
    protected List<List<String>> getTableDataRaw() {
        return tableBody.isDisplayed() ? TableHelper.getTextContentOfTable(tableBody, "ebTableRow elTablelib-row")
                : new ArrayList<List<String>>();
    }

    /**
     * Show column if hidden.
     *
     * @param columnNames the column names
     */
    protected void showNeededColumns(String... columnNames) {
        for (String columnName : columnNames) {
            if (columnName == null) { // ignore null value
                continue;
            }
            if (!TableHelper.listToLowercase(getColumnHeaders()).contains(columnName.toLowerCase())) {
                // NOTE- show all columns instead of displaying one by one while executing the test- possible performance improvements
                getTableSettings().showAllColumns();
                break;
            }
        }
    }

    /**
     * Throws an exception for waitUntilAppears/hide method error.
     *
     * @param show the show
     * @param columnName1 the column name 1
     * @param cellValue1 the cell value 1
     * @param columnName2 the column name 2
     * @param cellValue2 the cell value 2
     * @param throwException the throw exception
     */
    protected void printErrorMsg(boolean show, String columnName1, String cellValue1, String columnName2,
            String cellValue2, boolean throwException) {

        final boolean checkAnotherCell = (columnName2 != null && cellValue2 != null);
        final String appearHideString = show ? "appear" : "hide";

        String errorMsg;
        if (checkAnotherCell) {
            errorMsg = String.format("Timeout while waiting row with cells '%s','%s' and '%s','%s' to %s.", columnName1,
                    cellValue1, columnName2, cellValue2, appearHideString);
        } else {
            errorMsg = String.format("Timeout while waiting row with cell '%s','%s' to %s.", columnName1, cellValue1,
                    appearHideString);
        }

        if (throwException) {
            throw new WaitTimedOutException(errorMsg);
        } else {
            LOGGER.warn(errorMsg);
        }
    }

    /**
     * Check/Uncheck items.
     *
     * @param check      the check
     * @param columnName the column name
     * @param items      the items
     * @return the table
     */
    protected Table checkUncheckItems(boolean check, String columnName, String... items) {
        showNeededColumns(columnName);

        final int columnIndex = getColumnIndex(columnName.toLowerCase());
        if (columnIndex == -1) {
            throw new UiComponentNotFoundException(
                    "Checking items failed: column name '" + columnName + "' not found in table header");
        }

        List<String> itemsToCheckUncheck = new ArrayList<>(Arrays.asList(items));

        int currentRowIndex = 0;
        for (List<String> rowCells : getTableData()) {
            final String rowValue = rowCells.get(columnIndex);
            if (itemsToCheckUncheck.contains(rowValue)) {
                if (check) {
                    this.getBodyRow(currentRowIndex).check();
                } else {
                    this.getBodyRow(currentRowIndex).uncheck();
                }
                itemsToCheckUncheck.remove(rowValue);
            }
            if (itemsToCheckUncheck.isEmpty()) {
                break;
            }
            currentRowIndex++;
        }

        if (!itemsToCheckUncheck.isEmpty()) {
            if (check) {
                throw new UiComponentNotFoundException(
                        "Checking items failed: " + itemsToCheckUncheck.size() + " items were not found in table");
            } else {
                throw new UiComponentNotFoundException(
                        "Unchecking items failed: " + itemsToCheckUncheck.size() + " items were not found in table");
            }
        }
        return this;
    }

    /**
     * Gets the column index.
     *
     * @param columnHeader the column header
     * @return the column index
     */
    protected int getColumnIndex(String columnHeader) {
        return TableHelper.listToLowercase(getColumnHeaders()).indexOf(columnHeader.toLowerCase());
    }

}
