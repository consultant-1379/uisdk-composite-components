package com.ericsson.cds.uisdk.compositecomponents.test.viewmodels;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.customcomponents.UiSdkWizardStep1Body;
import com.ericsson.cds.uisdk.compositecomponents.test.customcomponents.UiSdkWizardStep2Body;
import com.ericsson.cds.uisdk.compositecomponents.wizard.Wizard;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import static com.ericsson.cifwk.taf.ui.core.SelectorType.CSS;

public class UiSdkWizardViewModel extends GenericViewModel {

    @UiComponentMapping(selectorType = CSS, value = Wizard.WIZARD)
    private Wizard wizard;

    public Wizard getWizard() {
        return wizard;
    }

    public void ensurePageIsLoaded(BrowserTab tab) {
        AbstractWidgetLibraryTest.ensurePageIsLoaded(wizard, tab, this);
    }

    public void setUserDetails(String name, String email, String phone) {
        UiSdkWizardStep2Body step2Body = getWizard().getCurrentStepBodyElement(UiSdkWizardStep2Body.class);
        step2Body.setUserDetails(name, email, phone);
    }

    public void toggleConfirmationCheckBox() {
        UiSdkWizardStep1Body step1Body = getWizard().getCurrentStepBodyElement(UiSdkWizardStep1Body.class);
        step1Body.toggleConfirmationCheckBox();
    }
}

