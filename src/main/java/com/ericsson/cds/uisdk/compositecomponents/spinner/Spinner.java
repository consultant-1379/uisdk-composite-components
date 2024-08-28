package com.ericsson.cds.uisdk.compositecomponents.spinner;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;
import org.openqa.selenium.Keys;

/**
 * Representation of UI SDK Spinner
 * 
 * @since 1.0.10
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/spinner">
 *       Spinner</a>
 */
public class Spinner extends AbstractUiComponent {

    @UiComponentMapping(selector = ".ebSpinner-inputHolder input")
    private TextBox input;

    @UiComponentMapping(selector = ".ebSpinner-label")
    private UiComponent label;

    @UiComponentMapping(selector = ".ebSpinner")
    private UiComponent rootComponent;

    @UiComponentMapping(selector = ".ebSpinner-value")
    private UiComponent valueLabel;

    /**
     * Gets the prefix label.
     *
     * @return the prefix label
     */
    public String getLabel() {
        return label.getText();
    }

    public int getValue() {
        return Integer.parseInt(input.getText());
    }

    /**
     * Gets the value/postfix label.
     *
     * @return the value/postfix label
     */
    public String getValueLabel() {
        return valueLabel.getText();
    }

    /**
     * Checks if spinner value is valid (not valid if input contains red border).
     *
     * @return true, if is valid
     */
    public boolean isValid() {

        UiComponent componentToCheck = null;
        if (this.getProperty("class").startsWith("ebSpinner")) {
            componentToCheck = this;
        } else {
            componentToCheck = rootComponent;
        }

        return !componentToCheck.getProperty("class").contains("ebSpinner_status_error");
    }

    public Spinner setValue(int value) {
        input.sendKeys(Keys.CONTROL + "a",Keys.DELETE);
        input.sendKeys(Integer.toString(value));
        return this;
    }

    /**
     * Set the value without clearing previous value in the spinner text box.<br>
     * <b>This option is working on latest chrome version.<br>
     * {@link #setValue(int)} is not working on Firefox version.</b>
     * 
     * @param value
     * @return
     */
    public Spinner setValueDirectly(int value) {

        input.evaluate("element.value='" + value + "'");
        return this;
    }

}
