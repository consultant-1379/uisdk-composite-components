package com.ericsson.cds.uisdk.compositecomponents.radiobuttons;

import java.util.ArrayList;
import java.util.List;
import com.ericsson.cds.uisdk.compositecomponents.radiobuttons.radiobutton.RadioButton;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasClickableItems;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasItems;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasValue;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;

/**
 * Representation of UI SDK Radio Buttons.
 * 
 * @since 1.0.19
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/assets/latest/showcase/#ui-showcase/controls/radio-button">
 *       Radio Button</a>
 */
public class RadioButtonGroup extends AbstractUiComponent
        implements HasValue<String>, HasItems<String>, HasClickableItems {

    protected final String RADIO_BUTTON_ELEMENT = "label";

    @UiComponentMapping(RADIO_BUTTON_ELEMENT)
    private List<RadioButton> radioButtons;

    @Override
    public void clickItem(String buttonName) {

        // example (translate is used to ignore case)   //span[translate(text(), 'LABEL 1', 'label 1')='label 1']/ancestor::label
        final String getItemNameXPATHSelector = String.format(
                "//span[translate(text(), '%s', '%s')='%s']/ancestor::%s",
                buttonName.toUpperCase(), buttonName.toLowerCase(),
                buttonName.toLowerCase(), RADIO_BUTTON_ELEMENT);

        List<UiComponent> itemsMatchesText = getDescendantsBySelector(SelectorType.XPATH, getItemNameXPATHSelector);
        if (itemsMatchesText.isEmpty()) {
            throw new UiComponentNotFoundException("Radio button '" + buttonName + "' not found!");
        }

        itemsMatchesText.get(0).as(RadioButton.class).select();
    }

    @Override
    public List<String> getItems() {

        List<String> names = new ArrayList<String>();
        for (RadioButton button : radioButtons) {
            names.add(button.getText());
        }

        return names;
    }

    @Override
    public String getValue() {

        for (RadioButton button : radioButtons) {
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    @Override
    public void setValue(String value) {
        clickItem(value);
    }

}
