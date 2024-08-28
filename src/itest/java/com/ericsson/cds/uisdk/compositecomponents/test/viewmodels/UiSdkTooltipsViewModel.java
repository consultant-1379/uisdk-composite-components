package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.XPATH;

import com.ericsson.cds.uisdk.compositecomponents.tooltip.Tooltips;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

public class UiSdkTooltipsViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = XPATH, value = "//div[contains(text(),'Mouse over the')]/../div/div/p")
    private UiComponent tooltipOwner;

    public String getTooltipText() {
        return getTooltips().getTooltipFor(tooltipOwner);
    }

    private Tooltips getTooltips() {
        return new Tooltips(this);
    }
}
