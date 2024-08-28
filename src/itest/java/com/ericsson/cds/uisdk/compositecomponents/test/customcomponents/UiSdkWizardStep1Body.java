package com.ericsson.cds.uisdk.compositecomponents.test.customcomponents;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.CSS;

public class UiSdkWizardStep1Body extends AbstractUiComponent  {

    @UiComponentMapping(selectorType = CSS, value = ".eaWizardExample-step1-checkbox")
    private CheckBox checkBox;

    public void toggleConfirmationCheckBox() {
        checkBox.click();
    }

}

