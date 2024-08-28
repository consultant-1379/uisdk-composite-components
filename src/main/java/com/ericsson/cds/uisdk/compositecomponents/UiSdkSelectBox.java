package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasValue;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;

import java.util.List;

/**
 * Component for dealing with UI SDK Select Box
 *
 * @since 1.0.13
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/select-box">
 *       Select Box</a>
 */
public class UiSdkSelectBox extends AbstractItemSelector implements HasValue<String> {

    public static final String REGEX_PREFIX = "#REGEX#";
    
    @UiComponentMapping("button.ebSelect-header")
    private Button componentOpener;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items > .ebComponentList-item")
    private List<Label> items;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items")
    private UiComponent itemsHolder;

    @UiComponentMapping("span.ebSelect-value")
    private Label selectedValue;

    /**
     * Returns the selected value.
     * It corresponds to visible item name (NOT item property 'value').
     */
    @Override
    public String getValue() {
        return selectedValue.getText();
    }

    /**
     * Selects the item in the items list.
     * If the item is not in the list throws a UiComponentNotFoundException.
     */
    @Override
    public void setValue(String itemName) throws UiComponentNotFoundException {
        clickItem(itemName);
    }

    @Override
    protected Button getComponentOpener() {
        return componentOpener;
    }

    @Override
    protected List<? extends UiComponent> getItemComponents() {
        return items;
    }

    @Override
    protected UiComponent getItemsHolder() {
        return itemsHolder;
    }
}
