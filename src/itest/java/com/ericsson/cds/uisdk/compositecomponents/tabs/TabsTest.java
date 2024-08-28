package com.ericsson.cds.uisdk.compositecomponents.tabs;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTabsViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TabsTest extends AbstractWidgetLibraryTest {

    private Tabs tabs;
    private UiSdkTabsViewModel tabsView;

    @Before
    public void initializeView() {
        tabsView = tab.getView(UiSdkTabsViewModel.class);
        tabs = tabsView.getTabs();
        tabsView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testTabsGetTabNames() {
        List<String> tabNames = tabs.getTabNames();

        assertTrue(tabNames.size() > 0);
    }

    @Test
    public void testTabsGetSelectedTabName() {
        List<String> tabNames = tabs.getTabNames();
        String selectedTab = tabs.getSelectedTabName();

        assertEquals(tabNames.get(0), selectedTab);
    }

    @Test
    public void testTabsOpenTabButton() {

        String tab1Text = tabs.openTab("Tab 1").getText().trim();
        assertEquals("Tab 1 Content", tab1Text);

        String tab2Text = tabs.openTab("Tab 2").getText().trim();
        assertEquals("Tab 2 Content", tab2Text);
    }

    @Test
    public void testTabsGetContentElement() {
        List<String> tabNames = tabs.getTabNames();
        UiComponent tabContent = tabs.openTab(tabNames.get(1));

        assertTrue(tabContent.isDisplayed());
        assertFalse(tabContent.getText().isEmpty());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/tabs";
    }
}
