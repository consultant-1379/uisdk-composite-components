package com.ericsson.cds.uisdk.compositecomponents.notification;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

/**
 * Representation of Notification.
 * 
 * @since 1.0.29
 * @author ehrvkla
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/notification">
 *      Notification</a>
 */
public class Notification extends AbstractUiComponent {

    @UiComponentMapping(".ebNotification-close .ebIcon")
    private UiComponent closeIcon;

    @UiComponentMapping(".ebNotification-label")
    private UiComponent label;

    @UiComponentMapping(".ebNotification")
    private UiComponent rootElement;

    public void close() {
        if (!this.isDisplayed()) {
            // handle case when UI notification is automatically hidden
            return;
        }

        if (!closeIcon.isDisplayed()) {
            // handle not closable notifications (without X icon)
            throw new UnsupportedOperationException("Notification's close icon is hidden!");
        }
        closeIcon.click();
        waitUntil(closeIcon, UiComponentPredicates.HIDDEN);
    }

    public NotificationType getNotificationType() {
        final String className = getRootElement().getProperty("class");
        return NotificationType.forClass(className);
    }

    private UiComponent getRootElement() {
        // if component is mapped with parent/container selector, return .ebNotification
        return rootElement.exists() ? rootElement : this;
    }

    @Override
    public String getText() {
        return label.getText();
    }

}
