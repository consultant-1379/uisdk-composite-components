package com.ericsson.cds.uisdk.compositecomponents.tooltip;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTooltipsViewModel;

public class TooltipsTest extends AbstractWidgetLibraryTest {

    private UiSdkTooltipsViewModel tooltipsView;

    @Before
    public void initializeView() {
        tooltipsView = tab.getView(UiSdkTooltipsViewModel.class);
    }

    @Test
    public void getTooltip() {
        String validTooltipText = "This is a tooltip";

        String tooltipText = tooltipsView.getTooltipText();
        assertTrue(tooltipText.equals(validTooltipText));
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/tooltip";
    }
}
