/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.cds.uisdk.compositecomponents.table;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * @deprecated
 * This Component maps to the deprecated Table widget in the UI-SDK widget library. Please use {@link com.ericsson.cds.uisdk.compositecomponents.table.lib.Table}
 */
@Deprecated
public class Table extends AbstractUiComponent {

    @UiComponentMapping("thead tr")
    private HeadRow headRow;

    @UiComponentMapping("tbody tr")
    private List<BodyRow> bodyRows;

    public HeadRow getHeadRow() {
        return headRow;
    }

    public List<BodyRow> getBodyRows() {
        return bodyRows;
    }

    public BodyRow getBodyRow(int rowIndex) {
        Preconditions.checkArgument(rowIndex > -1, "rowIndex should be non negative");
        Preconditions.checkArgument(rowIndex < bodyRows.size(),
                String.format("rowIndex %d should not be greater than number of rows, %d", rowIndex, bodyRows.size()));
        return bodyRows.get(rowIndex);
    }
}