package com.ericsson.cds.uisdk.compositecomponents.modaldialog;

import java.util.List;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;

/**
 * Representation of UI SDK ModalDialog.
 *
 * @since 1.0.19
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/modal-dialog">
 *       Modal Dialog</a>
 */
public class ModalDialog extends AbstractUiComponent {

    protected final String ACTION_BLOCK_SELECTOR = "ebDialogBox-actionBlock"; // without dot

    @UiComponentMapping(selector = "." + ACTION_BLOCK_SELECTOR + " .ebBtn")
    protected List<Button> buttons;

    @UiComponentMapping(selector = ".ebDialogBox .ebDialogBox-contentBlock")
    protected UiComponent dialogContent;

    public void clickButton(String buttonText) {

        // example        //div[@class='ebDialogBox-actionBlock']/button[translate(*/text(), 'ACTION', 'action')='action']
        final String getButtonByNameXPATHSelector = String.format(
                "//div[@class='%s']/button[translate(*/text(), '%s', '%s')='%s']",
                ACTION_BLOCK_SELECTOR, buttonText.toUpperCase(), buttonText.toLowerCase(),
                buttonText.toLowerCase());

        List<UiComponent> buttonsMatchesText = getDescendantsBySelector(SelectorType.XPATH, getButtonByNameXPATHSelector);
        if (buttonsMatchesText.isEmpty()) {
            throw new UiComponentNotFoundException(
                    "Modal dialog button '" + buttonText + "' not found!");
        }

        buttonsMatchesText.get(0).as(Button.class).click();
    }

    /**
     * Gets the Modal Dialog body element as provided elementClass.
     *
     * @param <T> the generic type
     * @param elementClass the element class
     * @return the body element
     */
    public <T extends UiComponent> T getBodyElement(Class<T> elementClass) {
        return dialogContent.as(elementClass);
    }

}
