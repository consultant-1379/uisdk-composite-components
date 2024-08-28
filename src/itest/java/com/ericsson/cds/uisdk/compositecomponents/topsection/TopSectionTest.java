package com.ericsson.cds.uisdk.compositecomponents.topsection;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTopSectionViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TopSectionTest extends AbstractWidgetLibraryTest {

    private TopSection topSection;
    private UiSdkTopSectionViewModel topSectionView;

    @Before
    public void initializeView() {
        topSectionView = tab.getView(UiSdkTopSectionViewModel.class);
        topSection = topSectionView.getTopSection();
        topSectionView.ensurePageIsLoaded(tab);
    }

    @Test
    public void clickAction() {
        topSection.clickAction("Main Action 1");
        String message = topSectionView.getModalDialog().getText();
        assertTrue(message.contains("Pressed Action Main Action 1"));
    }

    @Test
    public void handleLoader() {
        topSection.clickAction("Show Loader");

        // the loader is visible few seconds here, and other actions are not visible
        topSection.clickAction("Flat Button");
        String message = topSectionView.getModalDialog().getText();
        assertTrue(message.contains("Pressed Action"));
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void clickActionWithWrongName() {
        topSection.clickAction("WRONG ACTION NAME");
    }

    @Test
    public void clickButton() {
        topSection.clickButton("Enable");
        assertTrue(topSection.getAvailableActions().contains("Disable"));
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void clickButtonWithWrongName() {
        topSection.clickButton("WRONG BUTTON NAME");
    }

/*    @Test
    public void clickContextualAction() {
        topSectionView.getTable().getBodyRow(0).click(); // highlight row
        topSection.clickContextualAction("Clear selection");
    }*/

/*    @Test(expected = UiComponentNotFoundException.class)
    public void clickContextualActionWithWrongName() {
        topSectionView.getTable().getBodyRow(0).click(); // highlight row
        topSection.clickContextualAction("WRONG CONTEXTUAL ACTION NAME");
    }*/

    @Test
    public void clickDropdown() {
        topSection.clickDropdownItem("Action 2");
        String message = topSectionView.getModalDialog().getText();
        assertTrue(message.contains("Pressed Action Action 2"));
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void clickDropdownItemWithWrongName() {
        topSection.clickDropdownItem("WRONG DROPDOWN ITEM NAME");
    }

    @Test
    public void clickFlatButton() {
        topSection.clickButton("Flat Button");
        String message = topSectionView.getModalDialog().getText();
        assertTrue(message.contains("Pressed Action"));
    }

    @Test
    public void clickHyperLink() {
        String urlBeforeClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        topSection.clickHyperLink("Link");
        waitForBrowserToRedirect(urlBeforeClick);

        String urlAfterClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        assertEquals(
                "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/api/classes/layouts_TopSection.html",
                urlAfterClick);
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void clickHyperLinkWithWrongName() {
        topSection.clickHyperLink("WRONG HYPERLINK NAME");
    }

    @Test
    public void clickLinkButton() {
        String urlBeforeClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        topSection.clickButton("Link Button");
        waitForBrowserToRedirect(urlBeforeClick);

        String urlAfterClick = browser.getAllOpenTabs().get(0).getCurrentUrl();
        assertEquals("https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/",
                urlAfterClick);
    }

    @Test
    public void getButtons() {
        int numberOfButtons = topSection.getButtons().size();
        assertEquals(9, numberOfButtons);
    }

    @Test
    public void getHyperlinks() {
        int nuberOfHyperlinks = topSection.getHyperlinks().size();
        assertEquals(1, nuberOfHyperlinks);
    }

    @Test
    public void getAvailableActions() {
        List<String> actions = topSection.getAvailableActions();

        List<String> expectedActions = new ArrayList<>();
        expectedActions.add("Save");
        expectedActions.add("Show Loader");
        expectedActions.add("Icon on Right");
        expectedActions.add("Enable");
        expectedActions.add("Link Button");
        expectedActions.add("Simulate Loading Actions");
        expectedActions.add("Flat Button");
        expectedActions.add("Main Action 1");
        expectedActions.add("Main Action 2");
        expectedActions.add("Main Action 3");
        expectedActions.add("Action 1");
        expectedActions.add("Action 2");
        expectedActions.add("Link");

        assertEquals(expectedActions, actions);
    }

/*    @Test
    public void getAvailableContextualActions() {
        topSectionView.getTable().getBodyRow(0).click(); // highlight row
        List<String> contextualActions = topSection.getAvailableActions();
        // example fix: Activate/Deactivate button is randomly generated (not hardcoded) so ignore this buttons
        contextualActions.remove("Activate");
        contextualActions.remove("Deactivate");

        List<String> expectedContextualActions = new ArrayList<>();
        expectedContextualActions.add("Clear selection");
        expectedContextualActions.add("View item page");

        assertEquals(expectedContextualActions, contextualActions);
    }*/

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#examples/top-section-example";
    }
}
