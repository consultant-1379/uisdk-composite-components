package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBox;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.google.common.base.Preconditions;

import java.util.List;

public abstract class AbstractRow<T extends Cell> extends AbstractUiComponent implements Row<T> {

    public void check() {
        if (!getCheckBox().isDisplayed()) {
            throw new UnsupportedOperationException(
                    "Can't set Checkbox value for table row, table row doesn't contain checkboxes.");
        }
        getCheckBox().select();
    }

    @Override
    public T getCell(int cellIndex) {
        Preconditions.checkArgument(cellIndex >= 0, "cellIndex should be non negative");
        List<T> cells = getCells();
        Preconditions.checkArgument(cellIndex < cells.size(),
                String.format("cellIndex %d should not be greater than number of cells, %d", cellIndex, cells.size()));
        return (cellIndex < cells.size()) ? cells.get(cellIndex) : null;
    }

    public boolean isChecked() {
        if (!getCheckBox().isDisplayed()) {
            throw new UnsupportedOperationException(
                    "Can't get table row Checkbox value, table row doesn't contain checkboxes.");
        }

        return getCheckBox().getValue();
    }

    public void uncheck() {
        if (!getCheckBox().isDisplayed()) {
            throw new UnsupportedOperationException(
                    "Can't unset Checkbox value for table row, table row doesn't contain checkboxes.");
        }
        getCheckBox().deselect();
    }

    protected abstract UiSdkCheckBox getCheckBox();

}
