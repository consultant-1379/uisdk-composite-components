package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import org.apache.commons.lang3.StringUtils;

/**
 * Component representing sort arrow in UI SDK table column heading
 */
public class SortArrow extends AbstractUiComponent {

    public boolean isActive() {
        String className = getProperty("class");
        return StringUtils.contains(className, "ebSort-arrow_active");
    }
}
