package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.table.lib.HeadRow;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

public class QuickFilter extends AbstractUiComponent {

    @UiComponentMapping(".elTablelib-QuickFilterCell-content")
    protected List<FilterField> filterFields;

    protected HeadRow headRow;

    /**
     * Sets the date filter.
     *
     * @param columnName the column name
     * @param filterValue the filter value
     * @return the localized date string
     */
    public String setDateFilter(String columnName, Date filterValue) {
        final String localizedDateString = getFilterField(columnName).as(DateFilterField.class).filter(filterValue);
        return localizedDateString;
    }

    public void setHeadRow(HeadRow headRow) { // TODO should be protected (not visible to testers) but is not visible from HeadRow's package 
        this.headRow = headRow;
    }

    public void setMultiSelectFilter(String columnName, Collection<String> filterValue) {
        getFilterField(columnName).as(MultiSelectFilterField.class).filter(filterValue);
    }

    public void setSelectFilter(String columnName, String filterValue) {
        getFilterField(columnName).as(SelectFilterField.class).filter(filterValue);
    }

    /**
     * Set the value to textual filter for which the specified column values will be filtered.
     * 
     * @param columnName
     *                    name of the column for which the filter will be set
     * @param filterValue
     *                    value for which the values will be filtered
     */
    public void setTextFilter(String columnName, String filterValue) {
        getFilterField(columnName).as(TextFilterField.class).filter(filterValue);
    }

    /**
     * Clear whole text from text filter and automatically apply the filter.
     * @param columnName
     *          name of the column for which the filter will be cleared
     */
    public void clearTextFilter(String columnName) {
        getFilterField(columnName).as(TextFilterField.class).clear();
    }

    private FilterField getFilterField(String columnName) {
        int columnIndex = headRow.getColumnIndexByCellName(columnName);
        return filterFields.get(columnIndex);
    }

}
