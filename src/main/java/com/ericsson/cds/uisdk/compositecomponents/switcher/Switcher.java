package com.ericsson.cds.uisdk.compositecomponents.switcher;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;

/**
 * Representation of UI SDK Switcher.
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/controls/switcher">
 *       Switcher</a>
 */
public class Switcher extends AbstractUiComponent {

    @UiComponentMapping(selector = ".ebSwitcher-checkbox")
    private CheckBox checkBox;

    @UiComponentMapping(selector = ".ebSwitcher-offLabel")
    private UiComponent offLabel;

    @UiComponentMapping(selector = ".ebSwitcher-onLabel")
    private UiComponent onLabel;

    @UiComponentMapping(selector = ".ebSwitcher-switch")
    private UiComponent switcher;

    @Override
    public void click() {
        switcher.click();
    }

    public void activate() {
        if (!isActivated()) {
            click();
        }
    }

    public void deactivate() {
        if (isActivated()) {
            click();
        }
    }

    public boolean isActivated() {
        return !(checkBox.getProperty("checked") == null);
    }

    public void setValue(boolean value) {
        if (value) {
            activate();
        } else {
            deactivate();
        }
    }

    public Switcher toggle() {
        click();
        return this;
    }

}
