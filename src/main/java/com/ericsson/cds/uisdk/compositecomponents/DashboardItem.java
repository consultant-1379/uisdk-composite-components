package com.ericsson.cds.uisdk.compositecomponents;

import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkContextMenu;
import com.ericsson.cifwk.taf.api.Nullable;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;

/**
 * Representation of UI SDK Dashboard item.
 * 
 * @since 1.0.26
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#dashboard">
 *       Dashboard</a>
 */
public class DashboardItem extends AbstractItemSelector {

    public static String closeString = "Close";
    public static String maximizeString = "Maximize";
    public static String minimizeString = "Minimize";

    @UiComponentMapping(".elLayouts-Dashboard-item-content")
    protected UiComponent content;

    @UiComponentMapping(".ebContextMenu")
    protected UiSdkContextMenu contextMenu;

    @GlobalScope
    @UiComponentMapping(selector = ".elWidgets-ComponentList-list .ebComponentList-item")
    protected List<Label> contextMenuItems;

    @GlobalScope
    @UiComponentMapping(selector = ".elWidgets-ComponentList-list")
    protected UiComponent itemsHolder;

    @UiComponentMapping(".elLayouts-Dashboard-item-menu")
    protected Button menuButton;

    @UiComponentMapping(".elLayouts-Dashboard-item-settings")
    protected Button settingsButton;

    @UiComponentMapping(selector = ".elLayouts-Dashboard-item-header-text")
    protected UiComponent title;

    public void clickMenuItem(String item) {
        contextMenu.clickItem(item);
    }

    /**
     * Close the item.
     *
     * @param localizedItemString use if non-english UI localization (default "Close")
     */
    public void close(@Nullable String... localizedItemString) {
        final String tempString = (localizedItemString.length > 0 && localizedItemString[0] != null)
                ? localizedItemString[0]
                : closeString;
        try {
            contextMenu.clickItem(tempString);
        } catch (UiComponentNotFoundException e) {
            throw new UnsupportedOperationException(
                    "Can't close the dashboard item '" + title.getText() + "'. Dashboard item is not closable.");
        }
    }

    @Override
    public String getText() {
        return content.getText();
    }

    public String getTitle() {
        return title.getText();
    }

    public UiComponent getWidgetElement() {
        return content.getChildren().get(0);
    }

    /**
     * Maximize the item.
     * 
     * @param localizedItemString use if non-english UI localization (default "Maximize")
     */
    public void maximize(@Nullable String... localizedItemString) {
        final String tempString = (localizedItemString.length > 0 && localizedItemString[0] != null)
                ? localizedItemString[0] : maximizeString;

        try {
            contextMenu.clickItem(tempString);
        } catch (UiComponentNotFoundException e) {
            throw new UnsupportedOperationException(
                    "Can't maximize the dashboard item '" + title.getText() + "'. Dashboard item can't be maximized.");
        }
    }

    /**
     * Minimize the item.
     * 
     * @param localizedItemString use if non-english UI localization (default "Minimize")
     */
    public void minimize(@Nullable String... localizedItemString) {
        final String tempString = (localizedItemString.length > 0 && localizedItemString[0] != null)
                ? localizedItemString[0] : minimizeString;

        try {
            contextMenu.clickItem(tempString);
        } catch (UiComponentNotFoundException e) {
            throw new UnsupportedOperationException(
                    "Can't minimize the dashboard item '" + title.getText() + "'. Dashboard item can't be minimized.");
        }
    }

    public void openSettings() {
        settingsButton.click();
    }

    @Override
    protected Button getComponentOpener() {
        return menuButton;
    }

    @Override
    protected List<? extends UiComponent> getItemComponents() {
        return contextMenuItems;
    }

    @Override
    protected UiComponent getItemsHolder() {
        return itemsHolder;
    }
}
