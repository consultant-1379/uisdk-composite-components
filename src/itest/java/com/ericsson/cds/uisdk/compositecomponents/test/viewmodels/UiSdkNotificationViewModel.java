package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.notification.Notification;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkNotificationViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = SelectorType.XPATH, value = "//div[contains(text(),'The Notification message widget allows developers to show short messages')]/..//*[contains(@class, 'ebNotification')]")

    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(notification, tab, this);
    }

}
