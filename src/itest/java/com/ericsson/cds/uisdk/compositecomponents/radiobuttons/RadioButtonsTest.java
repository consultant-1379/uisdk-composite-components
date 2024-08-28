package com.ericsson.cds.uisdk.compositecomponents.radiobuttons;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkRadioButtonsViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RadioButtonsTest extends AbstractWidgetLibraryTest {

    private RadioButtonGroup radioButtons;
    private UiSdkRadioButtonsViewModel radioButtonsView;

    @Before
    public void initializeView() {
        radioButtonsView = tab.getView(UiSdkRadioButtonsViewModel.class);
        radioButtons = radioButtonsView.getRadioButtons();
        radioButtonsView.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItems() {
        List<String> buttonNames = radioButtons.getItems();

        assertEquals(2, buttonNames.size());
    }

    @Test
    public void getValue() {
        String buttonText = radioButtons.getValue();

        assertEquals(null, buttonText);
    }

    @Test
    public void clickRadioButtonItem() {
        final String buttonName = "Label 2";
        radioButtons.clickItem(buttonName);

        String buttonText = radioButtons.getValue();
        assertEquals(buttonName, buttonText);
    }

    @Test(expected = UiComponentNotFoundException.class)
    public void clickRadioButtonItemWrongName() {
        final String buttonName = "wrong button name";
        radioButtons.clickItem(buttonName);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/assets/latest/showcase/#ui-showcase/controls/radio-button";
    }
}
