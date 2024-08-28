package com.ericsson.cds.uisdk.compositecomponents.notification;

/**
 * NotificationType.
 * 
 * @since 1.0.29
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/notification">
 *       Notification</a>
 */
public enum NotificationType {

    ERROR("_red"),
    INFORMATION("_paleBlue"),
    SUCCESS("_green"),
    WARNING("_yellow");

    protected static NotificationType forClass(String className) {
        for (NotificationType type : NotificationType.values()) {
            if (className.toUpperCase().contains(type.className.toUpperCase())) {
                return type;
            }
        }

        throw new UnsupportedOperationException("Unknown notification type with class name '" + className + "'.");
    }

    private String className;

    NotificationType(String className) {
        this.className = className;
    }
}
