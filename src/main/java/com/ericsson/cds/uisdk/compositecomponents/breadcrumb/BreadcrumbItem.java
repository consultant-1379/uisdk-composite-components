package com.ericsson.cds.uisdk.compositecomponents.breadcrumb;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.sdk.Link;

/**
 * Representation of UI SDK Breadcrumb item
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/app-structure/breadcrumb">
 *       Breadcrumb</a>
 */
public class BreadcrumbItem extends AbstractUiComponent {

    @UiComponentMapping(selector = ".ebBreadcrumbs-list .ebComponentList a")
    private List<Link> childrenLinks;

    @UiComponentMapping(selector = ".ebBreadcrumbs-arrow")
    private UiComponent downArrow;

    @UiComponentMapping(selector = ".ebBreadcrumbs-list")
    private UiComponent dropdownContainer;

    @UiComponentMapping(selector = "a.ebBreadcrumbs-link")
    private Link mainLink;

    @Override
    public void click() {
        mainLink.click();
    }

    UiComponent expandChildrenItems() {
        if (downArrow.isDisplayed()) {
            downArrow.click();
        }

        waitUntil(dropdownContainer, UiComponentPredicates.DISPLAYED);

        return dropdownContainer;
    }

    public List<String> getChildrenNames() {
        expandChildrenItems();

        List<String> childrenNames = new ArrayList<String>();
        for (Link link : childrenLinks) {
            childrenNames.add(link.getText());
        }

        return childrenNames;
    }

}
