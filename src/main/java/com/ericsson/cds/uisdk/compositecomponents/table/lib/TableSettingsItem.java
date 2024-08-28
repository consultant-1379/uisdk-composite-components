package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cds.uisdk.compositecomponents.flyout.FlyoutPanel;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;
import com.ericsson.cifwk.taf.ui.sdk.Label;

/**
 * Composite Component for handling UI SDK Table Settings Item
 * 
 * @since 1.0.55
 * @author ehrvkla
 */
public class TableSettingsItem extends FlyoutPanel {

    @UiComponentMapping(".ebCheckbox")
    protected CheckBox checkBox;

    @UiComponentMapping(".elTablelib-TableSettingsItem-labelText")
    protected Label label;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String getLabel() {
        return label.getText();
    }
}
