package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.compositecomponents.table.Table;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

@Deprecated
public class UiSdkTableViewModel extends GenericViewModel {

    @UiComponentMapping(".ebTable")
    private Table table;

    public Table getTable(){
        return table;
    }
}
