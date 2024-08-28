package com.ericsson.cds.uisdk.compositecomponents;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;

/**
 * Composite Component for handling UI SDK Dropdown
 *
 * @since 1.0.13
 * @see <a href=
 *      "https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/dropdown">
 *      Dropdown</a>
 *      since 1.0.32 of <a href=
 *      "https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/revisions.html">
 *      Widget Library</a>
 */
public class UiSdkDropDownMenu extends AbstractItemSelector {

    @UiComponentMapping(selector = ".ebDropdown-button")
    private Button componentOpener;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items > .ebComponentList-item")
    private List<Label> items;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items > .ebComponentList-group > .ebComponentList-inner > .ebComponentList-item")
    private List<Label> groupItems;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items")
    private UiComponent itemsHolder;

    @Override
    protected Button getComponentOpener() {
        return componentOpener;
    }

    /**
     * Gets concatenated items and groupItems.<br>
     * Group items are used in Actions drop-down which is shown when there is not enough space for
     * actions in the original categories.
     */
    @Override
    protected List<? extends UiComponent> getItemComponents() {
        List<Label> itemComponents = new ArrayList<>();
        for (Label item : items) {
            itemComponents.add(item);
        }

        if (groupItems != null) {
            for (Label item : groupItems) {
                itemComponents.add(item);
            }
        }

        return itemComponents;
    }

    @Override
    protected UiComponent getItemsHolder() {
        return itemsHolder;
    }
}
