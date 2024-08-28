package com.ericsson.cds.uisdk.compositecomponents.tooltip;

import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

/**
 * The Class Tooltips.
 * 
 * @since 1.0.24
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/tooltip">
 *       Tooltip</a>
 */
public class Tooltips {

    GenericViewModel genericViewModel;

    public Tooltips(GenericViewModel genericViewModel) {
        this.genericViewModel = genericViewModel;
    }

    public String getTooltipFor(UiComponent component) {
        component.mouseOver();

        UiComponent contentTextComponent = genericViewModel.getViewComponent(".ebTooltip-contentText");
        genericViewModel.waitUntil(contentTextComponent, UiComponentPredicates.DISPLAYED);
        return contentTextComponent.getText();
    }
}
