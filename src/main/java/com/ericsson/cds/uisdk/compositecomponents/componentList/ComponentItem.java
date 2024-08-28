package com.ericsson.cds.uisdk.compositecomponents.componentList;

import com.ericsson.cifwk.meta.API;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;

import static com.ericsson.cifwk.meta.API.Quality.Deprecated;


/**
 * Sample control for dealing with UI SDK Component list item
 *
 * @deprecated  please use UiSdkDropDownMenu, UiSdkSelectBox, UiSdkComboBox,
 *              UiSdkComboMultiSelectBox or UiSdkMultiSelectBox instead.
 */
@Deprecated
@API(Deprecated)
public class ComponentItem extends AbstractUiComponent {

    public static final String COMPONENT_ITEM_SELECTOR = ".ebComponentList-item";

    public String getValue() {
        return this.getText();
    }
}
