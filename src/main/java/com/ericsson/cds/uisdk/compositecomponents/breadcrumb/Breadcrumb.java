package com.ericsson.cds.uisdk.compositecomponents.breadcrumb;

import java.util.List;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

/**
 * Representation of UI SDK Breadcrumb container.
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/app-structure/breadcrumb">
 *       Breadcrumb</a>
 */
public class Breadcrumb extends AbstractUiComponent {

    final String BREADCRUMB_ITEM_SELECTOR = "ebBreadcrumbs-item"; // NOTE- without dot

    @UiComponentMapping(selector = "." + BREADCRUMB_ITEM_SELECTOR)
    private List<BreadcrumbItem> breadcrumbItems;

    @UiComponentMapping(selector = ".ebBreadcrumbs-list")
    private UiComponent dropdownContainer;

    public void clickBreadcrumb(String breadcrumbName) {
        getBreadcrumbByName(breadcrumbName).click();
    }

    /**
     * Gets the breadcrumb by name.
     *
     * @param breadcrumbName the breadcrumb name
     * @return the breadcrumb item
     */
    private BreadcrumbItem getBreadcrumbByName(String breadcrumbName) {

        // example  //div[@class='ebBreadcrumbs-item' and translate(*/text(), 'LEVEL 1', 'level 1')='level 1']
        final String getBreadcrumbByNameXPATHSelector = String.format(
                "//div[@class='%s' and translate(*/text(), '%s', '%s')='%s']",
                BREADCRUMB_ITEM_SELECTOR, breadcrumbName.toUpperCase(), breadcrumbName.toLowerCase(),
                breadcrumbName.toLowerCase());

        List<UiComponent> items = getDescendantsBySelector(SelectorType.XPATH, getBreadcrumbByNameXPATHSelector);
        if (items.isEmpty()) {
            throw new UiComponentNotFoundException("Breadcrumb '" + breadcrumbName + "' does not exist");
        }

        return items.get(0).as(BreadcrumbItem.class);
    }

    public List<BreadcrumbItem> getBreadcrumbItems() {
        return breadcrumbItems;
    }

    public BreadcrumbItem getLastBreadcrumbItem() {
        if (!breadcrumbItems.isEmpty()) {
            return breadcrumbItems.get(breadcrumbItems.size() - 1);
        }
        return null;
    }
}
