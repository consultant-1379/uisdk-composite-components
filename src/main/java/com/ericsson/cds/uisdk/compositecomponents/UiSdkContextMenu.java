package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;

import java.util.List;

/**
 * Composite Component for handling UI SDK Context menu
 *
 * @since 1.0.26
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/controls/context-menu">
 *       Context Menu</a>
 */
public class UiSdkContextMenu extends AbstractItemSelector {

    @UiComponentMapping(selector = ".ebContextMenu-expandBtn")
    private Button componentOpener;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items")
    private UiComponent itemsHolder;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items > .ebComponentList-item")
    private List<Label> items;

    @Override
    protected UiComponent getItemsHolder() {
        return itemsHolder;
    }

    @Override
    protected List<? extends UiComponent> getItemComponents() {
        return items;
    }

    @Override
    protected Button getComponentOpener() {
        return componentOpener;
    }
}
