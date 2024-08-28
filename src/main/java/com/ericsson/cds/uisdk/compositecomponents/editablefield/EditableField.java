package com.ericsson.cds.uisdk.compositecomponents.editablefield;

import org.openqa.selenium.Keys;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.TextBox;

/**
 * Representation of UI SDK EditableField.
 * 
 * @since 1.0.12
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/form-elements/editable-field">
 *       Editable Field</a>
 */
public class EditableField extends AbstractUiComponent {

    @UiComponentMapping(".elWidgets-EditableField-Input-Textbox.ebInput")
    private TextBox input;

    @UiComponentMapping(".elWidgets-EditableField-textHolderText")
    private UiComponent textHolder;

    public void clear() {
        textHolder.click();
        input.clear();
    }

    @Override
    public String getText() {
        return input.getText();
    }

    public void setText(String text) {
        // make editable
        textHolder.click();

        input.sendKeys(text);

        input.sendKeys(Keys.ENTER);
    }

}
