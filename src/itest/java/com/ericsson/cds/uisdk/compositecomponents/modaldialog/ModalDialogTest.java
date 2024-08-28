package com.ericsson.cds.uisdk.compositecomponents.modaldialog;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkModalDialogViewModel;
import com.ericsson.cifwk.taf.ui.sdk.MessageBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModalDialogTest extends AbstractWidgetLibraryTest {

    private ModalDialog modalDialog;
    private UiSdkModalDialogViewModel modalDialogView;

    @Before
    public void initializeView() {
        modalDialogView = tab.getView(UiSdkModalDialogViewModel.class);
        modalDialogView.ensurePageIsLoaded(tab);
        modalDialog = modalDialogView.getModalDialog();

        modalDialogView.getShowDialogButton().click(); // initially click to show dialog
    }

    @Test()
    public void clickModalDialogConfirmButton() {
        modalDialog.clickButton("action");

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("Action", alert.getText());
        alert.clickOk();
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/modal-dialog";
    }
}
