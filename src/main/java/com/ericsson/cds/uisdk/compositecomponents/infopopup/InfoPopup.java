package com.ericsson.cds.uisdk.compositecomponents.infopopup;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.sdk.Button;

/**
 * Representation of UI SDK InfoPopup.
 * 
 * @since 1.0.33
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/info-popup">
 *       Info Popup</a>
 */
public class InfoPopup extends AbstractUiComponent {

    @GlobalScope
    @UiComponentMapping(".ebInfoPopup-closeButton")
    private Button closeButton;

    @GlobalScope
    @UiComponentMapping(".ebInfoPopup-content")
    private UiComponent content;

    @UiComponentMapping(".ebInfoPopup-infoIcon")
    private Button infoIcon;

    public void close() {
        if (!closeButton.isDisplayed()) {
            throw new UiComponentNotFoundException("Can't close Info Popup- close button is not enabled or visible!");
        }
        closeButton.click();
        waitUntil(content, UiComponentPredicates.HIDDEN);
    }

    @Override
    public String getText() {
        // close contextMenu if already visible
        if (content.isDisplayed()) {
            clickInfoIcon();
            waitUntil(content, UiComponentPredicates.HIDDEN);
        }

        // show contextMenu
        clickInfoIcon();
        waitUntil(content, UiComponentPredicates.DISPLAYED);
        String text = content.getText();

        // close after getText (can prevent overlaying other UI components)
        clickInfoIcon();
        waitUntil(content, UiComponentPredicates.HIDDEN);

        return text;
    }

    protected void clickInfoIcon() {
        infoIcon.mouseDown();
        infoIcon.mouseUp();
    }

}
