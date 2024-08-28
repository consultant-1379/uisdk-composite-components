package com.ericsson.cds.uisdk.compositecomponents.systembar;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.SystemBarComponent;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasClickableItems;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

/**
 * Representation of UI SDK SystemBar container.
 * 
 * @since 1.0.36
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/uisdkcontainer/latest/examples/systembarcomponents/#myapp">
 *       SystemBar</a>
 */
public class SystemBar extends AbstractUiComponent implements HasClickableItems {

    @UiComponentMapping(".eaContainer-SystemBarComponent")
    private List<SystemBarComponent> systemBarComponents;

    @Override
    public void clickItem(String componentName) {
        getComponent(componentName).click();
    }

    public SystemBarComponent getComponent(String title) {

        for (SystemBarComponent systemBarComponent : getComponents()) {
            if (systemBarComponent.getLabel().trim().equalsIgnoreCase(title)) {
                return systemBarComponent;
            }
        }

        throw new UiComponentNotFoundException("SystemBar component '" + title + "' not found.");
    }

    public List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (SystemBarComponent systemBarComponent : getComponents()) {
            if (systemBarComponent.isDisplayed()) {
                items.add(systemBarComponent.getLabel());
            }
        }
        return items;
    }

    protected List<SystemBarComponent> getComponents() {
        waitToLoad();
        return systemBarComponents;
    }

    private void waitToLoad() {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return !systemBarComponents.isEmpty();
            }
        });
    }

}