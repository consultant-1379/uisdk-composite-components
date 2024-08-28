package com.ericsson.cds.uisdk.compositecomponents.test.customcomponents;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.CSS;

public class UiSdkWizardStep2Body extends AbstractUiComponent  {

    @UiComponentMapping(selectorType = CSS, value = ".eaWizardExample-step2-nameInput")
    private TextBox nameBox;

    @UiComponentMapping(selectorType = CSS, value = ".eaWizardExample-step2-emailInput")
    private TextBox emailBox;

    @UiComponentMapping(selectorType = CSS, value = ".eaWizardExample-step2-phoneInput")
    private TextBox phoneBox;

    public void setUserDetails(String name, String email, String phone) {
        nameBox.setText(name);
        emailBox.setText(email);
        phoneBox.setText(phone);
    }
}

