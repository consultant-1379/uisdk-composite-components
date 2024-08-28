package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasValue;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

import java.util.List;

/**
 * Sample control for dealing with UI SDK ComboBox
 *
 * @since 1.0.13
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/combo-box">
 *       Combo Box</a>
 */
public class UiSdkComboBox extends AbstractItemSelector implements HasValue<String> {

    @UiComponentMapping(".ebCombobox > button.ebCombobox-helper")
    private Button componentOpener;

    @UiComponentMapping(".ebCombobox > input.ebInput")
    private TextBox valueHolder;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items")
    private UiComponent itemsHolder;

    @GlobalScope
    @UiComponentMapping(selector = ".ebComponentList-items > .ebComponentList-item")
    private List<Label> items;

    @Override
    public String getValue() {
        return valueHolder.getText();
    }

    @Override
    public void setValue(String value) {
        valueHolder.setText(value);
        collapse();
    }

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
