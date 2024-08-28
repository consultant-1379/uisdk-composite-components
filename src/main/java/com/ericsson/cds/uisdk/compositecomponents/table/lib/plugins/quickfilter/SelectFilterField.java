package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkSelectBox;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

public class SelectFilterField extends FilterField {

    @UiComponentMapping(".ebSelect")
    protected UiSdkSelectBox selectBox;

    public void filter(String filterValue) {
        validateFilterComponentVisibility(selectBox, filterValue, this.getClass().getName());

        selectBox.setValue(filterValue);
    }

}
