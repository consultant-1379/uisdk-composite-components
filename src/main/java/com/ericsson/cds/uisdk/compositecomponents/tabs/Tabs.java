package com.ericsson.cds.uisdk.compositecomponents.tabs;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

/**
 * Representation of UI SDK Tabs component.
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/tabs">
 *       Tabs</a>
 */
public class Tabs extends AbstractUiComponent {

    static final String TAB_AREA = ".ebTabs-top .ebTabs-tabArea";
    static final String TAB_ITEM = ".ebTabs-tabItem";
    static final String TAB_ITEMS = TAB_AREA + " " + TAB_ITEM;

    @UiComponentMapping(selector = ".ebTabs-top .ebTabs-tabArea .ebTabs-tabItem.ebTabs-tabItem_selected_true")
    private UiComponent selectedTab;

    @UiComponentMapping(selector = TAB_AREA)
    private UiComponent tabArea;

    @UiComponentMapping(selector = ".ebTabs-contentDiv")
    private UiComponent tabContentDiv;

    @UiComponentMapping(selector = TAB_ITEMS)
    private List<UiComponent> tabItems;

    public String getSelectedTabName() {
        return selectedTab.getText();
    }

    public List<String> getTabNames() {

        List<String> itemNames = new ArrayList<String>();
        for (UiComponent item : tabItems) {
            itemNames.add(item.getText());
        }
        //TODO List<String> names = JavaScriptHelper.getTextContentOfElementsByClass(tabArea, TAB_ITEM);    // much faster
        return itemNames;
    }

    /**
     * Open tab.
     *
     * @param tabName the tab name
     * @return the opened tab's content as UiComponent
     */
    public UiComponent openTab(String tabName) {
        UiComponent tab = getTabButton(tabName);
        tab.click();
        waitUntil(tabContentDiv, UiComponentPredicates.DISPLAYED);

        waitUntilTabSelected(tab);
        return tabContentDiv;
    }

    private UiComponent getTabButton(String tabName) {

        List<UiComponent> tabButtons = tabArea.getDescendantsBySelector(SelectorType.XPATH,
                String.format(
                        "//*[contains(concat(' ', @class, ' '), ' ebTabs-tabItem ') and (translate(., '%s', '%s')='%s')]",
                        tabName.toUpperCase(), tabName.toLowerCase(), tabName.toLowerCase()));

        if (tabButtons.isEmpty()) {
            return null;
        }

        UiComponent tab = tabButtons.get(0);
        waitForTabToEnable(tab);
        return tab;
    }

    private void waitForTabToEnable(final UiComponent tab) {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return !tab.getProperty("class").contains("tabItem_enabled_false");
            }
        });
    }

    private void waitUntilTabSelected(final UiComponent tab) {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return tab.getProperty("class").contains("ebTabs-tabItem_selected_true");
            }
        });
    }

}
