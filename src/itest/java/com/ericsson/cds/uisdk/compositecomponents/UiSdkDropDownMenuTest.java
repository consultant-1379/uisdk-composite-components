package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkDropDownViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.MessageBox;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UiSdkDropDownMenuTest extends AbstractWidgetLibraryTest {

    private static final String URL = "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/dropdown";

    private static final String ITEM1 = "Alert \"message 1\"";

    private static final String ITEM2 = "Alert \"message 2\"";

    private static final String ITEM3 = "Alert \"message 3\"";

    private UiSdkDropDownViewModel view;

    @Before
    public void setUp() {
        super.setUp();
        view = tab.getView(UiSdkDropDownViewModel.class);
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItemsAndClick() {

        // checking items
        List<String> items = view.getMyMenuItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3);

        // checking items repeatedly
        items = view.getMyMenuItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Test()
    public void clickItem() {

        // clicking item
        view.clickMyMenuItem(ITEM3);

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("message 3", alert.getText());
        alert.clickOk();
    }

    @Test
    public void clickVisibleItem() {

        // clicking item
        view.openMyMenuItems();
        view.clickMyMenuVisibleItem(ITEM3);

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("message 3", alert.getText());
        alert.clickOk();
    }

    @Test
    public void getVisibleItems() {

        // clicking item
        view.openMyMenuItems();
        List<String> items = view.getMyMenuVisibleItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Test
    public void clickHiddenItem() {
        try {
            view.clickMyMenuVisibleItem(ITEM3);
            fail();
        } catch (UiComponentNotFoundException e) {
            String expected = "Item with name '" + ITEM3
                    + "' is not found within [] (please make sure you enhanced / opened items)";
            assertThat(e).hasMessage(expected);
        }
    }

    @Test
    public void clickingNonExistingItem() {
        try {
            view.clickMyMenuItem("NonExistingItem");
            fail();
        } catch (UiComponentNotFoundException e) {
            assertThat(e)
                    .hasMessageContaining("Item with name 'NonExistingItem' is not found within")
                    .hasMessageContaining(ITEM1)
                    .hasMessageContaining(ITEM2)
                    .hasMessageContaining(ITEM3);
        }
    }

    @Override
    protected String getWidgetUrl() {
        return URL;
    }
}
