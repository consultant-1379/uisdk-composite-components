package com.ericsson.cds.uisdk.compositecomponents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.tree.HasItems;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasValues;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * Sample control for dealing with CheckBoxGroup
 * 
 * @since 1.0.53
 * @author ehrvkla
 */
public class UiSdkCheckBoxGroup extends AbstractUiComponent implements HasValues<String>, HasItems<String> {

    @UiComponentMapping("label")
    private List<UiSdkCheckBox> checkBoxes;

    public void deselectAll() {
        for (UiSdkCheckBox checkBox : checkBoxes) {
            checkBox.setValue(false);
        }
    }

    @Override
    public List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (UiSdkCheckBox checkBox : checkBoxes) {
            items.add(checkBox.getText());
        }
        return items;
    }

    @Override
    public List<String> getValues() {
        List<String> items = new ArrayList<>();
        for (UiSdkCheckBox checkBox : checkBoxes) {
            if (checkBox.getValue()) {
                items.add(checkBox.getText());
            }
        }
        return items;
    }

    public void selectAll() {
        for (UiSdkCheckBox checkBox : checkBoxes) {
            checkBox.setValue(true);
        }
    }

    @Override
    public void setValues(Collection<String> itemsToEnable) {
        itemsToEnable = listToLowercase(itemsToEnable);
        for (UiSdkCheckBox checkBox : checkBoxes) {
            checkBox.setValue(itemsToEnable.contains(checkBox.getText().toLowerCase()));
        }
    }

    protected List<String> listToLowercase(Collection<String> list) {
        List<String> newList = new ArrayList<String>();
        for (String string : list) {
            newList.add(string.toLowerCase());
        }
        return newList;
    }

}
