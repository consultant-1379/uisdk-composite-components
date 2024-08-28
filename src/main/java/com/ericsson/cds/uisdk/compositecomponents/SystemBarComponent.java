package com.ericsson.cds.uisdk.compositecomponents;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;

/**
 * Representation of UI SDK SystemBar component.
 * 
 * @since 1.0.35
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/uisdkcontainer/latest/examples/systembarcomponents/#myapp">
 *       SystemBar</a>
 */
public class SystemBarComponent extends AbstractItemSelector {

    @UiComponentMapping(selector = ".eaContainer-SystemBarComponent-body")
    private UiComponent body;

    @UiComponentMapping(selector = ".eaContainer-SystemBarComponent-arrow")
    private Button componentOpener;

    @UiComponentMapping(selector = ".eaContainer-SystemBarComponent-listItem")
    private List<UiComponent> items;

    @UiComponentMapping(selector = ".eaContainer-SystemBarComponent-list")
    private UiComponent itemsHolder;

    @UiComponentMapping(selector = ".eaContainer-SystemBarComponent-caption")
    private UiComponent label;

    @Override
    public void click() {
        body.click();
    }

    public String getLabel() {
        return label.getText();
    }

    @Override
    protected void clickItemInternally(String itemName) {
        List<String> items = newArrayList();
        for (UiComponent label : this.getItemComponents()) {
            final String item = label.getText();
            items.add(item);
            if (StringUtils.equalsIgnoreCase(item, itemName)) {
                label.mouseDown(); // fix for the label.click() problem
                label.mouseUp();
                return;
            }
        }
        String details = items.isEmpty() ? " (please make sure you enhanced / opened items)" : "";
        String message = String.format("Item with name '%s' is not found within %s%s", itemName, items, details);
        throw new UiComponentNotFoundException(message);
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
