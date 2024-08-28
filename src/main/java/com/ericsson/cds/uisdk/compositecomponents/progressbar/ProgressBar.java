package com.ericsson.cds.uisdk.compositecomponents.progressbar;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;

/**
 * Representation of UI SDK Progress Bar.
 * 
 * @since 1.0.12
 * @author ehrvkla
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/progress-bar">
 *       Progress Bar</a>
 */
public class ProgressBar extends AbstractUiComponent {

    @UiComponentMapping(".ebProgressBar-label")
    private UiComponent label;

    @UiComponentMapping(".ebProgressBar-value")
    private UiComponent progressValue;

    public String getLabel() {
        return label.getText();
    }

    public int getValue() {
        String valueString = progressValue.getText().replace("%", "");
        return Integer.parseInt(valueString);
    }

    public void waitUntilPercentage(final int percentage) {

        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return getValue() >= percentage;
            }
        });
    }

}
