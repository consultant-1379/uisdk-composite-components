package com.ericsson.cds.uisdk.compositecomponents.wizard;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * Representation of UI SDK wizard item (step) component.
 * 
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#wizard-example">
 *       Wizard</a>
 */
public class WizardStep extends AbstractUiComponent {

    /**
     * Selector for accessing wizard step component.
     */
    public static final String WIZARD_STEP = ".ebWizard-stepListItem.ebWizard-step";

    static final String STEP_TITLE = ".ebWizard-stepTitle";

    @UiComponentMapping(selector = STEP_TITLE)
    private UiComponent stepTitleComponent;

    /**
     * Returns wizard step name (title).
     * 
     * @return step name
     */
    public String getName() {
        return stepTitleComponent.getText();
    }

    /**
     * Returns step validity flag.
     * 
     * @return step validity flag
     */
    public boolean isValid() {
        return getProperty("class").contains("ebWizard-step_valid");
    }

    /**
     * Returns info if step is disabled.
     * 
     * @return boolean indicating if step is disabled
     */
    public boolean isDisabled() {
        return getProperty("class").contains("ebWizard-step_disabled");
    }

}
