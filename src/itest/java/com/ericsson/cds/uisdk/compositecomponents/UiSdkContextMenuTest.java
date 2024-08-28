package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkContextMenuViewModel;
import com.ericsson.cifwk.taf.ui.sdk.MessageBox;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UiSdkContextMenuTest extends AbstractWidgetLibraryTest {

    private UiSdkContextMenu contextMenu;

    @Before
    public void initializeView() {
        UiSdkContextMenuViewModel view = tab.getView(UiSdkContextMenuViewModel.class);
        view.ensurePageIsLoaded(tab);
        contextMenu = view.getContextMenu();
    }

    @Test()
    public void clickItem() {
        contextMenu.clickItem("Alert \"message 1\"");

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("message 1", alert.getText());
        alert.clickOk();
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/controls/context-menu";
    }
}
