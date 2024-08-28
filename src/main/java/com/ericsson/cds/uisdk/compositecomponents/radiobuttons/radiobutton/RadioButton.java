package com.ericsson.cds.uisdk.compositecomponents.radiobuttons.radiobutton;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;

/**
 * Representation of UI SDK Radio Button.
 * 
 * @since 1.0.19
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/assets/latest/showcase/#ui-showcase/controls/radio-button">
 *       Radio Button</a>
 */
public class RadioButton extends AbstractUiComponent {

    @UiComponentMapping(".ebRadioBtn")
    private CheckBox checkBox;

    @Override
    public boolean isSelected() {
        String property = checkBox.getProperty("selected");
        return !(property == null);
    }

    public void select() {
        checkBox.click();
    }
}
