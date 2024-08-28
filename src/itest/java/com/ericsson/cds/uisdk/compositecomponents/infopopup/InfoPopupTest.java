package com.ericsson.cds.uisdk.compositecomponents.infopopup;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkInfoPopupViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InfoPopupTest extends AbstractWidgetLibraryTest {

    private InfoPopup infoPopup;
    private UiSdkInfoPopupViewModel infoPopupView;

    @Before
    public void initializeView() {
        infoPopupView = tab.getView(UiSdkInfoPopupViewModel.class);
        infoPopup = infoPopupView.getInfoPopup();
        infoPopupView.ensurePageIsLoaded(tab);
    }

    @Test
    public void getText() {
        String text = infoPopup.getText();
        assertEquals("200px wide InfoPopup. Lorem ipsum dolor sit amet, conse adipiscing elit.", text);
    }

    @Test
    public void close() {
        infoPopup.clickInfoIcon();
        infoPopupView.waitUntil(infoPopupView.getPopupContent(), UiComponentPredicates.DISPLAYED);

        infoPopup.close();

        infoPopupView.waitUntil(infoPopupView.getPopupContent(), UiComponentPredicates.HIDDEN);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/info-popup";
    }
}
