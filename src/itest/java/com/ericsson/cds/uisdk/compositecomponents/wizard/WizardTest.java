package com.ericsson.cds.uisdk.compositecomponents.wizard;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkWizardViewModel;
import com.ericsson.cds.uisdk.compositecomponents.wizard.Wizard;
import com.ericsson.cds.uisdk.compositecomponents.wizard.WizardStep;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.sdk.MessageBox;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WizardTest extends AbstractWidgetLibraryTest {

    private Wizard wizard;
    private UiSdkWizardViewModel wizardView;

    @Before
    public void initializeView() {
        wizardView = tab.getView(UiSdkWizardViewModel.class);
        wizard = wizardView.getWizard();
        wizardView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testWizardBaseContentAndCancel() {
        List<WizardStep> wizardSteps = wizard.getSteps();

        assertEquals(4, wizardSteps.size());
        checkStep(wizardSteps.get(1), "Enter Your Details", false, true);

        wizard.cancel();

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("cancel clicked", alert.getText());
        alert.clickOk();        
    }

    @Test
    public void testWizardNormalBehaviour() {
        //This section checks that current step name and availability of action buttons changes when user switches between steps
        assertEquals("Accept License Agreement", wizard.getCurrentStepName());
        assertTrue(wizard.isCancelAvailable());
        assertFalse(wizard.isPreviousAvailable());
        assertFalse(wizard.isNextAvailable());
        assertFalse(wizard.isFinishAvailable());

        wizardView.toggleConfirmationCheckBox();
        assertTrue(wizard.isNextAvailable());
        wizard.goToNextStep();

        assertEquals("Enter Your Details", wizard.getCurrentStepName());
        assertTrue(wizard.isPreviousAvailable());
        assertFalse(wizard.isNextAvailable());

        //This section checks that "disabled" and "valid" flags for wizard steps are dynamically changed when user switches between steps
        List<WizardStep> wizardSteps = wizard.getSteps();

        checkStep(wizardSteps.get(1), false, false);
        checkStep(wizardSteps.get(2), false, true);

        wizardView.setUserDetails("Name", "name@ericsson.com", "123456789");
        wizard.goToNextStep();

        checkStep(wizardSteps.get(1), true, false);
        checkStep(wizardSteps.get(2), false, false);

        //This section checks finish
        wizard.goToNextStep();
        assertTrue(wizard.isFinishAvailable());
        wizard.finish();

        // closing message box
        MessageBox alert = tab.getMessageBox();
        assertEquals("finish clicked", alert.getText());
        alert.clickOk();        
    }

    @Test
    public void testGoToPreviousStep() {
        wizardView.toggleConfirmationCheckBox();
        wizard.goToNextStep();
        assertEquals("Enter Your Details", wizard.getCurrentStepName());
        wizard.goToPreviousStep();
        assertEquals("Accept License Agreement", wizard.getCurrentStepName());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#wizard-example";
    }

    private void checkStep(WizardStep step, String expectedName, boolean expectedValid, boolean expectedDisabled) {
        assertEquals(expectedName, step.getName());
        checkStep(step, expectedValid, expectedDisabled);
    }

    private void checkStep(WizardStep step, boolean expectedValid, boolean expectedDisabled) {
        assertEquals(expectedValid, step.isValid());
        assertEquals(expectedDisabled, step.isDisabled());
    }

}
