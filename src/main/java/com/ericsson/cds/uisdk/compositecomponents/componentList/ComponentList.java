package com.ericsson.cds.uisdk.compositecomponents.componentList;

import com.ericsson.cifwk.meta.API;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ericsson.cifwk.meta.API.Quality.Deprecated;


/**
 * Sample control for dealing with UI SDK Component
 *
 * @deprecated  please use UiSdkDropDownMenu, UiSdkSelectBox, UiSdkComboBox,
 *              UiSdkComboMultiSelectBox or UiSdkMultiSelectBox instead.
 */
@Deprecated
@API(Deprecated)
public class ComponentList extends AbstractUiComponent {

    public static final String COMPONENT_LIST_SELECTOR = ".elWidgets-ComponentList";

    @UiComponentMapping(ComponentItem.COMPONENT_ITEM_SELECTOR)
    private List<ComponentItem> componentListItems;

    public List<ComponentItem> getComponentListItems() {
        return componentListItems;
    }

    /**
     * Clicks the component in the list of components in the ui-sdk component List div
     * @param value to be clicked
     */
    public void setComponentItem(String value) {
        for (ComponentItem componentItem : componentListItems) {
            if (StringUtils.equals(value, componentItem.getValue())) {
                componentItem.click();
                break;
            }
        }
    }

    /**
     * Returns the list of components of the ui-sdk component List
     */
    public List<String> getComponentItemValues() {
        List<String> values = new ArrayList<>();
        for (ComponentItem componentItem : componentListItems) {
            values.add(componentItem.getValue());
        }
        return values;
    }
}
