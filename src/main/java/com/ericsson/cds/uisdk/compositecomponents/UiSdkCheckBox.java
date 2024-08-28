package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasValue;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;

/**
 * Sample control for dealing with UI SDK UiSdkCheckBox
 * 
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/assets/latest/showcase/#ui-showcase/controls/checkbox">
 *       Checkbox</a>
 */
public class UiSdkCheckBox extends AbstractUiComponent implements HasValue<Boolean> {

    public static final String CHECKBOX_LABEL_SELECTOR = ".ebCheckbox-label";
    public static final String CHECKBOX_SELECTOR = ".ebCheckbox";

    @UiComponentMapping(CHECKBOX_LABEL_SELECTOR)
    private UiComponent label;

    @UiComponentMapping(CHECKBOX_SELECTOR)
    private CheckBox tickBox;

    public void deselect() {
        tickBox.deselect();
    }

    public UiComponent getLabel() {
        return label;
    }

    @Override
    public String getText() {
        return getLabel().getText();
    }

    /**
     * @deprecated please use direct methods select, deselect, ... instead.
     */
    @Deprecated
    public CheckBox getTickBox() {
        return tickBox;
    }

    public Boolean getValue() {
        return tickBox.isSelected();
    }

    public void select() {
        tickBox.select();
    }

    public void setValue(Boolean value) {
        if (value) {
            tickBox.select();
        } else {
            tickBox.deselect();
        }
    }

}
