package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkDropDownMenu;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import java.util.List;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkDropDownViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'The Button Dropdown widget')]/..")
    private UiSdkDropDownMenu dropDown;

    public void clickMyMenuItem(String itemName) {
        dropDown.clickItem(itemName);
    }

    public void clickMyMenuVisibleItem(String itemName) {
        dropDown.clickVisibleItem(itemName);
    }

    public void openMyMenuItems() {
        dropDown.click();
    }

    public List<String> getMyMenuItems() {
        return dropDown.getItems();
    }

    public List<String> getMyMenuVisibleItems() {
        return dropDown.getVisibleItems();
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(dropDown, tab, this);
    }

}
