package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBox;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * UI SDK table body row
 */
public class BodyRow extends AbstractRow<BodyCell> {

    @UiComponentMapping("td")
    protected List<BodyCell> cells;

    @UiComponentMapping(".elTablelib-CheckboxCell .elTablelib-CheckboxCell-wrap")
    private UiSdkCheckBox checkBox;

    @UiComponentMapping(".ebIcon_downArrow")
    private BodyCell downArrow;

    // used to get the right table header element
    @UiComponentMapping(selectorType = SelectorType.XPATH, value = "/ancestor::*[@class='elTablelib-Table']")
    private Table parentTable;

    @UiComponentMapping(".ebIcon_rightArrow")
    private BodyCell rightArrow;

    public void deselect() {
        if (isSelected()) {
            click();
        }
    }

    public BodyCell getCell(String columnName) {
        int columnIndex = parentTable.getHeadRow().getColumnIndexByCellName(columnName);
        return getCell(columnIndex);
    }

    public List<String> getCellItems() {
        List<String> cellsData = new ArrayList<>();
        for (BodyCell bodyCell : cells) {
            cellsData.add(bodyCell.getText());
        }
        return cellsData;
    }

    @Override
    public List<BodyCell> getCells() {
        return cells;
    }

    public boolean isSelected() {
        return getProperty("class").contains("ebTableRow_highlighted");
    }

    public void select() {
        if (!isSelected()) {
            click();
        }
    }

    protected void expand() {
        if (!rightArrow.isDisplayed() && !downArrow.isDisplayed()) {
            throw new UnsupportedOperationException("Can't expand table row, table row doesn't support expaning.");
        }

        if (rightArrow.isDisplayed()) {
            rightArrow.click();
        }
    }

    protected UiSdkCheckBox getCheckBox() {
        return checkBox;
    }
}
