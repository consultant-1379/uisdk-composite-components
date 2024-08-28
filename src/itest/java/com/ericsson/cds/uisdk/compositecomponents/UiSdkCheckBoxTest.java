package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkCheckBoxViewModel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UiSdkCheckBoxTest extends AbstractWidgetLibraryTest {

    private UiSdkCheckBox checkBox;

    @Before
    public void initializeView() {
        UiSdkCheckBoxViewModel view = tab.getView(UiSdkCheckBoxViewModel.class);
        view.ensurePageIsLoaded(tab);
        checkBox = view.getCheckBox();
    }

    @Test
    public void setValue() {
        assertTrue(checkBox.getValue());

        checkBox.setValue(false);
        assertFalse(checkBox.getValue());

        checkBox.setValue(true);
        assertTrue(checkBox.getValue());
    }

    @Test
    public void selectDeselectCheckbox() {
        checkBox.select();
        assertTrue(checkBox.getValue());

        checkBox.deselect();
        assertFalse(checkBox.getValue());

        checkBox.select();
        assertTrue(checkBox.getValue());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/tree";
    }
}
