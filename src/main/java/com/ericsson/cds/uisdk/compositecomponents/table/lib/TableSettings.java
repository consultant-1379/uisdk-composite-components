package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkCheckBox;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Link;

/**
 * Composite Component for handling UI SDK Table Settings
 *
 * @since 1.0.55
 * @author ehrvkla
 */
public class TableSettings extends AbstractUiComponent {

    public static List<String> listToLowercase(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String string : list) {
            newList.add(string.toLowerCase());
        }
        return newList;
    }

    @UiComponentMapping(".elTablelib-TableSettingsItem")
    protected List<TableSettingsItem> items;

    @UiComponentMapping(".elTablelib-TableSettings-selectAll")
    protected Link selectAllLink;

    @UiComponentMapping(".elTablelib-TableSettings-selectNone")
    protected Link selectNoneLink;

    public void dragAndDrop(UiComponent componentToDrag, UiComponent dropComponentLocation) {
        componentToDrag.mouseDown();
        UI.pause(500); // pause is required
        dropComponentLocation.mouseOver();
        UI.pause(500); // pause is required
        dropComponentLocation.mouseUp();
    }

    /**
     * Gets only selected item values.
     *
     * @return the values
     */
    public List<String> getSelectedItems() {

        // TODO performance improvements- use XPATH for component list and then foreach + gettext
        List<String> itemsInString = new ArrayList<>();
        for (TableSettingsItem item : getItems()) {
            if (item.getCheckBox().isSelected()) {
                itemsInString.add(item.getLabel().trim());
            }
        }
        return itemsInString;
    }

    /**
     * Gets the all (selected and deselected) item values.
     *
     * @return the values
     */
    public List<String> getValues() {
        List<String> itemsInString = new ArrayList<>();
        for (TableSettingsItem item : getItems()) {
            itemsInString.add(item.getLabel().trim());
        }
        return itemsInString;
    }

    public void hideAllColumns() {
        // handle table settings without select all/none link
        if (getSelectNoneLink().isDisplayed()) {
            getSelectNoneLink().click();
        } else {
            for (TableSettingsItem tableSettingsItem : getItems()) {
                tableSettingsItem.getCheckBox().deselect();
            }
        }
    }

    public void hideColumns(String... columnNames) {
        for (TableSettingsItem item : getItems()) {
            if (listToLowercase(Arrays.asList(columnNames)).contains(item.getLabel().trim().toLowerCase())) {
                item.getCheckBox().deselect();
            }
        }
    }

    public void reorderColumns(String firstColumnName, String secondColumnName) {
        dragAndDrop(getItem(firstColumnName), getItem(secondColumnName));
    }

    public void showAllColumns() {
        // handle table settings without select all/none link
        if (getSelectAllLink().isDisplayed()) {
            getSelectAllLink().click();
            // if first checkbox is not selected, try once more
            if (getItems().size() > 0 && !getItems().get(0).getCheckBox().isSelected()) {
                getSelectAllLink().click();
                // if it doesn't work, try to select each checkbox manually
                if (!getItems().get(0).getCheckBox().isSelected()) {
                    for (TableSettingsItem tableSettingsItem : getItems()) {
                        if (!tableSettingsItem.getCheckBox().isSelected()) {
                            tableSettingsItem.getCheckBox().select();
                        }
                    }
                }
            }
        } else {
            for (TableSettingsItem tableSettingsItem : getItems()) {
                tableSettingsItem.getCheckBox().select();
            }
        }
    }

    public void showColumns(String... columnNames) {
        for (TableSettingsItem item : getItems()) {
            if (listToLowercase(Arrays.asList(columnNames)).contains(item.getLabel().trim().toLowerCase())) {
                item.getCheckBox().select();
            }
        }
    }

    protected UiSdkCheckBox getItem(String itemName) {
        return getDescendantsBySelector(SelectorType.XPATH, String.format(
                "//li[contains(concat(' ', @class, ' '), ' elTablelib-TableSettingsItem ') and contains(translate(., '%s', '%s'), '%s')]",
                itemName.toUpperCase(), itemName.toLowerCase(), itemName.toLowerCase())).get(0)
                        .as(UiSdkCheckBox.class);
    }

    protected List<TableSettingsItem> getItems() {
        return items;
    }

    protected Link getSelectAllLink() {
        return selectAllLink;
    }

    protected Link getSelectNoneLink() {
        return selectNoneLink;
    }

    /**
     * Show columns in the argument and hide other columns.
     *
     * @param columnNames
     */
    public void showHideColumns(String... columnNames) {
        for (TableSettingsItem item : getItems()) {
            if (listToLowercase(Arrays.asList(columnNames)).contains(item.getLabel().trim().toLowerCase())) {
                item.getCheckBox().select();
            } else {
                item.getCheckBox().deselect();
            }
        }
    }

}
