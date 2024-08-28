package com.ericsson.cds.uisdk.compositecomponents.accordion;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

/**
 * Sample control for dealing with UI SDK Accordion.
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/accordion">
 *       Accordion</a>
 */
public class Accordion extends AbstractUiComponent {

    @UiComponentMapping(selector = ".ebAccordion-body")
    private UiComponent bodyContainer;

    @UiComponentMapping(selector = ".ebAccordion-title")
    private UiComponent title;

    @UiComponentMapping(selector = ".ebAccordion-button i")
    private UiComponent upDownArrowIcon;

    public Accordion collapse() {
        if (isExpanded()) {
            upDownArrowIcon.click();
            waitUntil(bodyContainer, UiComponentPredicates.HIDDEN);
        }
        return this;
    }

    public Accordion expand() {
        if (!isExpanded()) {
            upDownArrowIcon.click();
            waitUntil(bodyContainer, UiComponentPredicates.DISPLAYED);
        }
        return this;
    }

    /**
     * Gets the accordion body element as provided elementClass.
     *
     * @param <T> the generic type
     * @param elementClass the element class
     * @return the body element
     */
    public <T extends UiComponent> T getBodyElement(Class<T> elementClass) {

        expand();
        if (bodyContainer.getChildren().isEmpty()) {
            throw new UiComponentNotFoundException("Accordion element not found");
        }

        return bodyContainer.getChildren().get(0).as(elementClass);
    }

    public String getTitle() {
        return title.getText();
    }

    public boolean isExpanded() {
        return !upDownArrowIcon.getProperty("class").contains("ebIcon_downArrow");
    }
}
