/*
 * COPYRIGHT Ericsson (c) 2015.
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */

package com.ericsson.cds.uisdk.compositecomponents.flyout;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;

public class FlyoutPanel extends AbstractUiComponent {

    @UiComponentMapping(".eaFlyout-panelHeader")
    private UiComponent flyoutPanelHeader;

    @UiComponentMapping(".eaFlyout-panelCloseIcon")
    private UiComponent flyoutPanelClose;

    public void close() {
        if (flyoutPanelHeader.isDisplayed()) {
            flyoutPanelClose.click();
            waitUntil(flyoutPanelClose, UiComponentPredicates.HIDDEN);
        }
    }
}
