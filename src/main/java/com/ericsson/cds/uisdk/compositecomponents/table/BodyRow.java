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

import java.util.List;

/**
 * @deprecated
 * This Component maps to the deprecated BTable widget in the UI-SDK widget library. Please use {@link com.ericsson.cds.uisdk.compositecomponents.table.lib.Table}
 */

@Deprecated
public class BodyRow extends AbstractUiComponent {

    @UiComponentMapping("td")
    private List<Cell> cells;

    /**
     * @param colIndex
     * @return
     */
    public String getCell(int colIndex) {
        return cells.get(colIndex).getText();
    }
}
