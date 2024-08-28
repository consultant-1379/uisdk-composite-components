package com.ericsson.cds.uisdk.compositecomponents.notification;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkNotificationViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NotificationTest extends AbstractWidgetLibraryTest {

    private Notification notification;
    private UiSdkNotificationViewModel notificationView;

    @Before
    public void initializeView() {
        notificationView = tab.getView(UiSdkNotificationViewModel.class);
        notification = notificationView.getNotification();
        notificationView.ensurePageIsLoaded(tab);
    }

    @Test
    public void getText() {
        String text = notification.getText();
        assertEquals("This is a Notification", text);
    }

    @Test
    public void getType() {
        NotificationType notificationType = notification.getNotificationType();
        assertEquals(NotificationType.ERROR, notificationType);
    }

    @Test
    public void closeNotification() {
        notification.close();
        assertFalse(notification.isDisplayed());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/notification";
    }
}
