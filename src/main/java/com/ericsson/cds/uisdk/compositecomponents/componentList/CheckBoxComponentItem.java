package com.ericsson.cds.uisdk.compositecomponents.componentList;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBox;
import com.ericsson.cifwk.meta.API;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;

import static com.ericsson.cifwk.meta.API.Quality.Deprecated;


/**
 * Sample control for dealing with UI SDK Component checkbox list item
 *
 * @deprecated  please use UiSdkDropDownMenu, UiSdkSelectBox, UiSdkComboBox,
 *              UiSdkComboMultiSelectBox or UiSdkMultiSelectBox instead.
 */
@Deprecated
@API(Deprecated)
public class CheckBoxComponentItem extends ComponentItem {

    @UiComponentMapping("label")
    private UiSdkCheckBox checkbox;

    public CheckBox getCheckbox() {
        return checkbox.getTickBox();
    }

    @Override
    public String getValue() {
        return checkbox.getLabel().getText();
    }
}
