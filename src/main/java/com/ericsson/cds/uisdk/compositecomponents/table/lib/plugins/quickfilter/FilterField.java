package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;

public class FilterField extends AbstractUiComponent {

    public void validateFilterComponentVisibility(UiComponent filterUiComponent, String filterValue,
            String filterClassName) {
        if (!filterUiComponent.isDisplayed()) {
            throw new UnsupportedOperationException(
                    "Filter value " + filterValue + " is not supported for filter " + filterClassName + ".");
        }
    }

}
