package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.UiSdkMultiSelectBox;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

import java.util.List;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

public class UiSdkMultiSelectBoxViewModel extends GenericViewModel {

    private static final String CONTAINER = "//div[contains(text(),'The MultiSelectBox widget')]/..";

    @UiComponentMapping(selectorType = XPATH, value = CONTAINER + "/*/*/div[@class=\"ebSelect\"]")
    private UiSdkMultiSelectBox multiSelectBox;

    @UiComponentMapping(selectorType = XPATH, value = CONTAINER + "/*/*/div[@class=\"ebSelect\"]")
    private List<UiSdkMultiSelectBox> multiSelectBoxes;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = CONTAINER + "//span[@e-id=\"edit\"]")
    private Button editButton;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = CONTAINER + "//span[@e-id=\"apply\"]")
    private Button applyButton;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = CONTAINER + "//code[@e-id=\"code\"]")
    private TextBox codeTextBox;

    private static final String JS = "var temp = new MultiSelectBox({\n" +
            "    items: [{\n" +
            "            name: 'Temp 1',\n" +
            "            value: 'Temp 1',\n" +
            "            checked: true\n" +
            "        },\n" +
            "        {\n" +
            "            name: 'Temp 2',\n" +
            "            value: 'Temp 2'\n" +
            "        },\n" +
            "        {\n" +
            "            name: 'Temp 3',\n" +
            "            value: 'Temp 3'\n" +
            "        }\n" +
            "    ],\n" +
            "    selectDeselectAll: {\n" +
            "        selectAllLabel: \"Select All\",\n" +
            "        deselectAllLabel: \"Deselect All\"\n" +
            "    }\n" +
            "});\n" +
            "\n" +
            "temp.attachTo(this.getElement());\n" +
            "var selectBox = new MultiSelectBox({\n" +
            "    items: [{\n" +
            "            name: 'Value 1',\n" +
            "            value: 'Value 1'\n" +
            "        },\n" +
            "        {\n" +
            "            name: 'Value 2',\n" +
            "            value: 'Value 2'\n" +
            "        }\n" +
            "    ]\n" +
            "});\n" +
            "\n" +
            "selectBox.addEventHandler(\"change\", function() {\n" +
            "    console.log(\"onChange\");\n" +
            "    temp.detach();\n" +
            "});\n" +
            "selectBox.attachTo(this.getElement());";

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(multiSelectBox, tab, this);
    }

    public UiSdkMultiSelectBox getMultiSelectBox() {
        return multiSelectBox;
    }

    public UiSdkMultiSelectBox getLastMultiSelectBox(BrowserTab tab) {
        ensurePageIsLoaded(tab);
        return multiSelectBoxes.get(1);
    }

    /**
     * Generates simple widget dynamically
     */
    public void generateWidget() {

        // edit mode
        editButton.click();

        // update code
        codeTextBox.clear();
        codeTextBox.setText(JS);

        // apply changes
        applyButton.click();

        UI.pause(1000); // mandatory, wait for widget to refresh
        waitUntilComponentIsDisplayed(multiSelectBox, 30000);
    }

}
