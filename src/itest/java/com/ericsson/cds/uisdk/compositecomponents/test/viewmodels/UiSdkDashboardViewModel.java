package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.dashboard.Dashboard;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkDashboardViewModel extends GenericViewModel {

    @UiComponentMapping(".eaDashboard-rMain")
    private Dashboard dashboard;

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(dashboard, tab, this);
    }
}
