package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasValues;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Label;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Component for dealing with UI SDK Multi Select Box
 *
 * @since 1.0.13
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/multi-select-box">
 *      Multi Select Box</a>
 */
public class UiSdkMultiSelectBox extends AbstractItemSelector implements HasValues<String> {

    private static final Logger LOG = LoggerFactory.getLogger(UiSdkMultiSelectBox.class);

    @GlobalScope
    @UiComponentMapping(selector = ".elWidgets-ComponentList .elWidgets-MultiSelectBox-selectAll")
    private UiComponent selectAllLink;

    @GlobalScope
    @UiComponentMapping(selector = ".elWidgets-ComponentList .elWidgets-MultiSelectBox-deselectAll")
    private UiComponent deselectAllLink;

    @UiComponentMapping("button.ebSelect-header")
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

        expand();

        List<String> values = Lists.newArrayList();
        for (Label item : items) {
            if (item.as(UiSdkCheckBox.class).getValue()) {
                values.add(item.getText());
            }
        }

        collapse();

        return values;
    }

    public void selectAll() {
        expand();
        if (selectAllLink.isDisplayed()) {
            selectAllLink.click();
            collapse();
        } else {
            // fallback if "Select all" link does not exist
            setValues(getItems());
        }
    }

    public void deselectAll() {
        expand();
        if (deselectAllLink.isDisplayed()) {
            deselectAllLink.click();
        } else {
            // fallback if "Deselect all" link does not exist
            setValues(new ArrayList<String>());
        }
    }

    /**
     * Selects multiple items in the items list.
     * If any item is not in the list throws a UiComponentNotFoundException.
     */
    @Override
    public void setValues(Collection<String> itemNames) throws UiComponentNotFoundException {

        // collections with case insensitive contains()
        Set<String> itemsToSelect = Sets.newTreeSet(String.CASE_INSENSITIVE_ORDER);
        itemsToSelect.addAll(itemNames);

        // selecting and deselecting values on demand
        List<String> allItemNames = Lists.newArrayList();

        expand(); // expand to get all items
        boolean firstCheck = true;
        for (Label item : items) {

            // DOM changes (triggered by previous selections) could collapse items list

            // no need to expand if first iteration (already expanded)
            if (!firstCheck) {
                expand();
            }
            firstCheck = false;

            /*
             * Still no guarantee that select box wasn't collapsed asynchronously right before UI
             * actions with item.
             * Expanding and retrying latest actions.
             * However exception never caught in tests - looks like explicit click waits for DOM
             * update to finish.
             * Will be removed after some period of observation of Jenkins job.
             */
            try {
                processItem(item, itemsToSelect, allItemNames);
            } catch (UiComponentNotFoundException e) {
                // select box was collapsed for some reason - expanding it and repeating last UI actions
                expand();
                processItem(item, itemsToSelect, allItemNames);
                LOG.warn("Explicit click didn't wait for DOM update to finish");
            }
        }

        collapse(); // close items list

        // checking method arguments
        if (!itemsToSelect.isEmpty()) {
            final String message = String.format("Item name(-s) '%s' not found within %s", itemsToSelect, allItemNames);
            throw new UiComponentNotFoundException(message);
        }
    }

    private void processItem(Label item, Set<String> itemsToSelect, List<String> allItemNamesCollector) {
        final String itemName = item.getText();
        UiSdkCheckBox tickBox = item.as(UiSdkCheckBox.class);

        if (itemsToSelect.contains(itemName)) {
            tickBox.select();
            itemsToSelect.remove(itemName);
        } else {
            tickBox.deselect();
        }
        allItemNamesCollector.add(itemName);
    }

}
