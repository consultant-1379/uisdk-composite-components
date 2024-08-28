package com.ericsson.cds.uisdk.compositecomponents.wizard;

import java.util.List;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.sdk.Button;

/**
 * Representation of UI SDK Wizard component.
 * 
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#wizard-example">
 *       Wizard</a>
 */
public class Wizard extends AbstractUiComponent {

    /**
     * Selector for accessing wizard component.
     */
    public static final String WIZARD = ".ebWizard";

    protected static final String SEPARATOR = " ";

    protected static final String WIZARD_STEP_CURRENT_TITLE = WizardStep.WIZARD_STEP + ".ebWizard-step_current"
            + SEPARATOR + WizardStep.STEP_TITLE;
    protected static final String WIZARD_BODY_CONTAINER = ".ebWizard-bodyContainer";
    protected static final String WIZARD_FOOTER = ".ebWizard-footer";
    protected static final String WIZARD_CMD_LIST = ".ebWizard-footerCommandList";
    protected static final String WIZARD_CMD_LIST_ITEM = ".ebWizard-footerCommandListItem";
    protected static final String WIZARD_BUTTON_CANCEL = ".ebWizard-footerBtnCancel";
    protected static final String WIZARD_BUTTON_PREVIOUS = ".ebWizard-footerBtnPrevious";
    protected static final String WIZARD_BUTTON_NEXT = ".ebWizard-footerBtnNext";
    protected static final String WIZARD_BUTTON_FINISH = ".ebWizard-footerBtnFinish";

    @UiComponentMapping(selector = WizardStep.WIZARD_STEP)
    private List<WizardStep> steps;

    @UiComponentMapping(selector = WIZARD_BODY_CONTAINER)
    private UiComponent bodyContainer;

    @UiComponentMapping(selector = WIZARD_FOOTER + SEPARATOR + WIZARD_CMD_LIST + SEPARATOR + WIZARD_CMD_LIST_ITEM
            + SEPARATOR + WIZARD_BUTTON_CANCEL)
    private Button buttonCancel;

    @UiComponentMapping(selector = WIZARD_FOOTER + SEPARATOR + WIZARD_CMD_LIST + SEPARATOR + WIZARD_CMD_LIST_ITEM
            + SEPARATOR + WIZARD_BUTTON_PREVIOUS)
    private Button buttonPrevious;

    @UiComponentMapping(selector = WIZARD_FOOTER + SEPARATOR + WIZARD_CMD_LIST + SEPARATOR + WIZARD_CMD_LIST_ITEM
            + SEPARATOR + WIZARD_BUTTON_NEXT)
    private Button buttonNext;

    @UiComponentMapping(selector = WIZARD_FOOTER + SEPARATOR + WIZARD_CMD_LIST + SEPARATOR + WIZARD_CMD_LIST_ITEM
            + SEPARATOR + WIZARD_BUTTON_FINISH)
    private Button buttonFinish;

    /**
     * Returns current step name.
     * 
     * @return current step name
     */
    public String getCurrentStepName() {
        List<UiComponent> titleComponents = getDescendantsBySelector(WIZARD_STEP_CURRENT_TITLE);
        if (titleComponents.isEmpty()) {
            throw new UiComponentNotFoundException("Title component not found.");
        }
        return titleComponents.get(0).getText();
    }

    /**
     * Returns the list of all steps in wizard.
     * 
     * @return the list of all steps
     */
    public List<WizardStep> getSteps() {
        return this.steps;
    }

    /**
     * Returns current step body element.
     * 
     * @param elementClass the class of step body element to be returned.
     * @return current step body element
     */
    public <T extends UiComponent> T getCurrentStepBodyElement(Class<T> elementClass) {
        if (bodyContainer.getChildren().isEmpty()) {
            throw new UiComponentNotFoundException("Wizard step body not found.");
        }
        return bodyContainer.getChildren().get(0).as(elementClass);
    }

    /**
     * Returns information if next button is displayed and enabled.
     * 
     * @return true if next button is displayed and enabled, false otherwise
     */
    public boolean isNextAvailable() {
        return buttonNext.isDisplayed() && buttonNext.isEnabled();
    }

    /**
     * Switches to next step.
     */
    public void goToNextStep() {
        buttonNext.click();
        waitUntil(bodyContainer, UiComponentPredicates.DISPLAYED);
    }

    /**
     * Returns information if previous button is displayed and enabled.
     * 
     * @return true if previous button is displayed and enabled, false otherwise
     */
    public boolean isPreviousAvailable() {
        return buttonPrevious.isDisplayed() && buttonPrevious.isEnabled();
    }

    /**
     * Switches to previous step.
     */
    public void goToPreviousStep() {
        buttonPrevious.click();
        waitUntil(bodyContainer, UiComponentPredicates.DISPLAYED);
    }

    /**
     * Returns information if cancel button is displayed and enabled.
     * 
     * @return true if cancel button is displayed and enabled, false otherwise
     */
    public boolean isCancelAvailable() {
        return buttonCancel.isDisplayed() && buttonCancel.isEnabled();
    }

    /**
     * Clicks cancel button.
     */
    public void cancel() {
        buttonCancel.click();
    }

    /**
     * Returns information if finish button is displayed and enabled.
     * 
     * @return true if finish button is displayed and enabled, false otherwise
     */
    public boolean isFinishAvailable() {
        return buttonFinish.isDisplayed() && buttonFinish.isEnabled();
    }

    /**
     * Clicks finish button.
     */
    public void finish() {
        buttonFinish.click();
    }

}
