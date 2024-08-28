package com.ericsson.cds.uisdk.compositecomponents.breadcrumb;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkBreadcrumbViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BreadcrumbTest extends AbstractWidgetLibraryTest {

    private Breadcrumb breadcrumb;
    private UiSdkBreadcrumbViewModel breadcrumbView;

    @Before
    public void initializeView() {
        breadcrumbView = tab.getView(UiSdkBreadcrumbViewModel.class);
        breadcrumb = breadcrumbView.getBreadcrumb();
        breadcrumbView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testBreadcrumbgetClickFirstBreadcrumb() {
        String urlBeforeClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        BreadcrumbItem breadcrumbItem = breadcrumb.getBreadcrumbItems().get(0);
        breadcrumbItem.click();

        waitForBrowserToRedirect(urlBeforeClick);
    }

    @Test
    public void testBreadcrumbGetLastBreadcrumb() {
        BreadcrumbItem breadcrumbItem = breadcrumb.getLastBreadcrumbItem();
        assertTrue(breadcrumbItem.exists());
    }

    @Test
    public void testBreadcrumbGetChildrenNames() {
        List<String> childrenNames = breadcrumb.getLastBreadcrumbItem().getChildrenNames();
        assertTrue(childrenNames.size() > 0);
    }

    @Test
    public void testBreadcrumbExpandChildrenItems() {
        breadcrumb.getLastBreadcrumbItem().expandChildrenItems();
    }

    @Test
    public void testBreadcrumbClickBreadcrumb() {
        String urlBeforeClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        breadcrumb.clickBreadcrumb("Level 1");

        waitForBrowserToRedirect(urlBeforeClick);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/app-structure/breadcrumb";
    }
}
