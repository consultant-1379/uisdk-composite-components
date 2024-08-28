package com.ericsson.cds.uisdk.compositecomponents.systembar;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkSystemBarViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SystemBarTest extends AbstractWidgetLibraryTest {

    private SystemBar systemBar;
    private UiSdkSystemBarViewModel systemBarView;

    @Before
    public void initializeView() {
        systemBarView = tab.getView(UiSdkSystemBarViewModel.class);
        systemBar = systemBarView.getSystemBar();
        systemBarView.ensurePageIsLoaded(tab);
    }

    @Test
    public void clickComponent() {
        systemBar.clickItem("Notifications");

        systemBarView.waitUntil(systemBarView.getFlyoutPanel(), UiComponentPredicates.DISPLAYED);
    }

    @Test
    public void clickComponentItem() {
        String urlBeforeClick = browser.getAllOpenTabs().get(0).getCurrentUrl();

        systemBar.getComponent("Settings").clickItem("App Settings");

        waitForBrowserToRedirect(urlBeforeClick);
    }

    @Test
    public void getComponentItems() {
        List<String> names = systemBar.getItems();

        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Notifications");
        expectedNames.add("Settings");
        expectedNames.add("Help");
        expectedNames.add("Administrator");

        assertEquals(expectedNames, names);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/uisdkcontainer/latest/examples/systembarcomponents/#myapp";
    }
}
