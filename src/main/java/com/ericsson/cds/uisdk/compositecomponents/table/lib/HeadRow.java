package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBox;
import com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter.QuickFilter;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;

import java.util.List;

public class HeadRow extends AbstractRow<HeadCell> {

    protected final String FIRST_ROW_SELECTOR = ".ebTableRow.elTablelib-row:first-of-type";

    @UiComponentMapping(FIRST_ROW_SELECTOR + " th")
    private List<HeadCell> cells;

    @UiComponentMapping(FIRST_ROW_SELECTOR + " .elTablelib-CheckboxHeaderCell .elTablelib-CheckboxHeaderCell-wrap")
    private UiSdkCheckBox checkBox;

    @UiComponentMapping(".ebTableRow.elTablelib-row.elTablelib-QuickFilter")
    private QuickFilter quickFilter;

    @Override
    public List<HeadCell> getCells() {
        return cells;
    }

    protected UiSdkCheckBox getCheckBox() {
        return checkBox;
    }

    public QuickFilter getQuickFilter() {
        try {
            waitUntil(quickFilter, UiComponentPredicates.DISPLAYED);
        } catch (WaitTimedOutException e) {
            throw new UnsupportedOperationException("QuickFilter element is not found in the table!");
        }
        quickFilter.setHeadRow(this); // important
        return quickFilter;
    }

    public int getColumnIndexByCellName(String cellName) {

        // get header cell by text (case insensitive)
        // example    //tr[@class='ebTableRow elTablelib-row'][1]/th[descendant-or-self::*[translate(text(), 'FIRST NAME', 'first name')='first name']]
        final String getTitleCellContainingTextSelector = String.format(
                "//tr[@class='ebTableRow elTablelib-row'][1]/th[descendant-or-self::*[translate(text(), '%s', '%s')='%s']]",
                cellName.toUpperCase(), cellName.toLowerCase(), cellName.toLowerCase());

        List<UiComponent> cells = getDescendantsBySelector(SelectorType.XPATH, getTitleCellContainingTextSelector);
        if (cells.isEmpty()) {
            throw new UiComponentNotFoundException("Column with title " + cellName + " not found in the table");
        }

        // get preceding-sibling (left cells)
        UiComponent cell = cells.get(0);
        final String getLeftCellsSelector = "/preceding-sibling::*";
        List<UiComponent> leftCells = cell.getDescendantsBySelector(SelectorType.XPATH, getLeftCellsSelector);

        // column index is the same as number of left elements
        return leftCells.size();
    }
}
