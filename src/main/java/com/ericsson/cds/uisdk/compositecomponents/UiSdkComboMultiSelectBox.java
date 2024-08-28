package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasValues;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Component for dealing with UI SDK Combo Multi Select Box
 *
 * @since 1.0.13
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/combo-multi-select-box">
 *       Combo Multi Select Box</a>
 */
public class UiSdkComboMultiSelectBox extends AbstractItemSelector implements HasValues<String> {

    @UiComponentMapping("ul.ebComboMultiSelectList > li.ebComboMultiSelectList-item > span.ebComboMultiSelectList-itemTitle")
    private List<Label> values;

    @UiComponentMapping("ul.ebComboMultiSelectList > li.ebComboMultiSelectList-item > i.ebComboMultiSelectList-itemClose")
    private List<Button> valueCloseButtons;

    @UiComponentMapping("button.ebComboMultiSelect-helper")
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

    /**
     * Returns selected item names.
     * It corresponds to visible item names (NOT item property 'value').
     */
    @Override
    public List<String> getValues() {
        return newArrayList(transform(values, new Function<Label, String>() {

            @Override
            public String apply(Label label) {
                return label.getText();
            }
        }));
    }

    /**
     * Selects multiple items in the items list.
     * If any item is not in the list throws a UiComponentNotFoundException.
     */
    @Override
    public void setValues(Collection<String> values) {
        clearValues();

        for (String value : values) {
            clickItem(value);
        }
    }

    private void clearValues() {
        expand();
        for (Button closeButton : Lists.reverse(valueCloseButtons)) {
            closeButton.click();
        }
        collapse();
    }

}
