package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.breadcrumb.Breadcrumb;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkBreadcrumbViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//*[contains(text(),'The Breadcrumb')]/parent::*")
    private Breadcrumb breadcrumb;

    public Breadcrumb getBreadcrumb() {
        return breadcrumb;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(breadcrumb, tab, this);
    }

}
