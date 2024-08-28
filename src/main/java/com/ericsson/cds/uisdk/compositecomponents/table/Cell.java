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
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * @deprecated
 * This Component maps to the deprecated Table widget in the UI-SDK widget library. Please use {@link com.ericsson.cds.uisdk.compositecomponents.table.lib.Table}
 */

@Deprecated
public class Cell extends AbstractUiComponent {

    @UiComponentMapping(".elWidgets-TableSortable-sorter")
    private UiComponent sorter;

    public void sort() {
        sorter.click();
    }
}
