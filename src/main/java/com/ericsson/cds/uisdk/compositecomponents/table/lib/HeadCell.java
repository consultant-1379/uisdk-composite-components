package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.google.common.base.Preconditions;

import static com.ericsson.cds.uisdk.compositecomponents.table.lib.SortDirection.ASC;
import static com.ericsson.cds.uisdk.compositecomponents.table.lib.SortDirection.DESC;
import static com.ericsson.cds.uisdk.compositecomponents.table.lib.SortDirection.NOT_SORTED;

/**
 * UI SDK table head cell
 */
public class HeadCell extends AbstractUiComponent implements Cell {

    @UiComponentMapping(".ebSort-arrow_down")
    private SortArrow downArrow;

    @UiComponentMapping(".ebTable-headerSort.ebSort")
    private UiComponent sortableIcon;

    @UiComponentMapping(".ebSort-arrow_up")
    private SortArrow upArrow;

    /**
     * If the table is sortable and is sorted return what direction the table is sorted in
     * 
     * @return
     */
    public SortDirection getSortDirection() {
        if (!isSorted()) {
            return NOT_SORTED;
        }
        return upArrow.isActive() ? ASC : DESC;
    }

    /**
     * Is it possible to sort the table on this column
     * 
     * @return
     */
    public boolean isSortable() {
        return sortableIcon.exists();
    }

    /**
     * Is the table sorted on this column
     * 
     * @return
     */
    public boolean isSorted() {
        return isSortable() && (upArrow.isActive() || downArrow.isActive());
    }

    /**
     * Sort the table on this column
     */
    public void sort() {
        Preconditions.checkState(isSortable(), "This column is not sortable");
        sortableIcon.click();
    }

}
