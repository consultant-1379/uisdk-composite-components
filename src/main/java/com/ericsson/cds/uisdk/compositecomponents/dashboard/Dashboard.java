package com.ericsson.cds.uisdk.compositecomponents.dashboard;

import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.DashboardItem;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasItems;
import com.ericsson.cifwk.taf.api.Nullable;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;

/**
 * Representation of UI SDK Dashboard.
 *
 * @author ehrvkla
 * @since 1.0.26
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#dashboard">
 *       Dashboard</a>
 */
public class Dashboard extends AbstractUiComponent implements HasItems<DashboardItem> {

    /** The Constant TITLE_ELEMENT_SELECTOR. */
    public static final String TITLE_ELEMENT_SELECTOR = "elLayouts-Dashboard-item-header-text";// NOTE- without dot

    /** The dashboard item selector. */
    final String DASHBOARD_ITEM_SELECTOR = "elLayouts-Dashboard-item"; // NOTE- without dot

    /** The dashboard items. */
    @UiComponentMapping("." + DASHBOARD_ITEM_SELECTOR)
    private List<DashboardItem> dashboardItems;

    /**
     * Close dashboard items (with custom "Close" dashboard item localization).
     * 
     * @param localizedItemString use if non-english UI localization (default "Close")
     * @param itemsTitles the items titles
     */
    public void closeItems(@Nullable String localizedItemString, String... itemsTitles) {
        for (String title : itemsTitles) {
            getItem(title).close(localizedItemString);
            waitForDashboardItemToHide(title);
        }
    }

    /**
     * Gets the dashboard item's widget as widget class.
     *
     * @param <T> the UiComponent
     * @param title the dashboard item title
     * @param widgetClass the widget class
     * @return the dashboard widget
     */
    public <T extends UiComponent> T getDashboardItemWidget(String title, Class<T> widgetClass) {
        return getItem(title).getWidgetElement().as(widgetClass);
    }

    /**
     * Gets the item.
     *
     * @param dashboardItemTitle the dashboard item title
     * @return the item
     */
    public DashboardItem getItem(String dashboardItemTitle) {
        waitForDashboardItemToShow(dashboardItemTitle);

        DashboardItem dashboardItem = getDashboardItemInternal(dashboardItemTitle);
        if (dashboardItem != null) {
            return dashboardItem;
        }

        throw new UiComponentNotFoundException("Dashboard item '" + dashboardItemTitle + "' not found.");
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cds.uisdk.compositecomponents.tree.HasItems#getItems()
     */
    public List<DashboardItem> getItems() {
        return dashboardItems;
    }

    /**
     * Wait for dashboard item to hide.
     *
     * @param dashboardItemName the dashboard item name
     */
    public void waitForDashboardItemToHide(String dashboardItemName) {
        waitForDashboardItemToShowHide(dashboardItemName, false);
    }

    /**
     * Wait for dashboard item to show.
     *
     * @param dashboardItemName the dashboard item name
     * @return the dashboard item
     */
    public DashboardItem waitForDashboardItemToShow(String dashboardItemName) {
        return waitForDashboardItemToShowHide(dashboardItemName, true);
    }

    /**
     * Gets the dashboard item or return null if not exist.
     * The method wont wait for item to show and wont throw an exception if not found.
     *
     * @param dashboardItemTitle the dashboard item title
     * @return the dashboard item
     */
    protected DashboardItem getDashboardItemInternal(String dashboardItemTitle) {

        final String xpath = String.format(
                "//*[@class='%s' and translate(text(), '%s', '%s')='%s']/ancestor::div[@class='%s']",
                TITLE_ELEMENT_SELECTOR, dashboardItemTitle.toUpperCase(), dashboardItemTitle.toLowerCase(),
                dashboardItemTitle.toLowerCase(),
                DASHBOARD_ITEM_SELECTOR);
        List<UiComponent> itemsWithTitleIgnoreCase = getDescendantsBySelector(SelectorType.XPATH, xpath);

        return (itemsWithTitleIgnoreCase.size() > 0) ? itemsWithTitleIgnoreCase.get(0).as(DashboardItem.class) : null;
    }

    /**
     * Wait for dashboard item to show/hide.
     *
     * @param title the dashboard item title
     * @param shouldShow true- item should show, false- item should hide
     * @return the dashboard item
     */
    private DashboardItem waitForDashboardItemToShowHide(final String title, final boolean shouldShow) {

        try {
            waitUntil(new GenericPredicate() {

                @Override
                public boolean apply() {

                    if (!shouldShow && dashboardItems.isEmpty()) { // if waiting to hide and no items (all are hidden)
                        return true;
                    }

                    // this will return shouldShow if displayed
                    return (getDashboardItemInternal(title) != null) ? shouldShow : !shouldShow;
                }
            });
        } catch (WaitTimedOutException e) {
            throw new WaitTimedOutException(
                    "Timeout while waiting dashboard item '" + title + "' to "
                            + ((shouldShow == true) ? "show" : "hide") + ".");

        }

        // return dashboard item if should show
        return shouldShow ? getDashboardItemInternal(title) : null;
    }

}
