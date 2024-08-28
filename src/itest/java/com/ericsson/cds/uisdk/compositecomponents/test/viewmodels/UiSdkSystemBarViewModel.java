package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.flyout.FlyoutPanel;
import com.ericsson.cds.uisdk.compositecomponents.systembar.SystemBar;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkSystemBarViewModel extends GenericViewModel {

    @UiComponentMapping(".eaFlyout-panel")
    private FlyoutPanel flyoutPanel;

    @UiComponentMapping(".eaContainer-SystemBarHolder")
    private SystemBar systemBar;

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(systemBar, tab, this);
    }

    public FlyoutPanel getFlyoutPanel() {
        return flyoutPanel;
    }

    public SystemBar getSystemBar() {
        return systemBar;
    }
}
