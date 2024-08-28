package com.ericsson.cds.uisdk.compositecomponents.accordion;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkAccordionViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccordionTest extends AbstractWidgetLibraryTest {

    private Accordion accordion;
    private UiSdkAccordionViewModel accordionView;

    @Before
    public void initializeView() {
        accordionView = tab.getView(UiSdkAccordionViewModel.class);
        accordion = accordionView.getAccordion();
        accordionView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testAccordionGetTitle() {
        String title = accordion.getTitle();
        assertFalse(title.isEmpty());
    }

    @Test
    public void testAccordionExpand() {
        accordion.expand();
        assertTrue(accordion.isExpanded());
    }

    @Test
    public void testAccordionCollapse() {
        accordion.collapse();
        assertFalse(accordion.isExpanded());
    }

    @Test
    public void testAccordionBody() {
        UiComponent bodyElement = accordion.getBodyElement(UiComponent.class);
        assertTrue(bodyElement.exists());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/accordion";
    }
}
