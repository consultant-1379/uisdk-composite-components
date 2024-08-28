package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import java.util.Collection;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkMultiSelectBox;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

public class MultiSelectFilterField extends FilterField {

    @UiComponentMapping(".ebSelect")
    private UiSdkMultiSelectBox multiSelectBox;

    public void filter(Collection<String> filterValue) {
        validateFilterComponentVisibility(multiSelectBox, filterValue.toString(), this.getClass().getName());

        multiSelectBox.setValues(filterValue);
    }

}
