package com.ericsson.cds.uisdk.compositecomponents.table.lib.plugins.quickfilter;

import org.openqa.selenium.Keys;

import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

public class TextFilterField extends FilterField {

    @UiComponentMapping("input")
    protected TextBox input;

    public void filter(String filterValue) {
        input.setText(filterValue);
        input.sendKeys(Keys.ENTER);
    }

    /**
     * Send the "escape" key in order to clear the filter
     */
    public void clear() {
        input.sendKeys(Keys.ESCAPE);
    }

}
