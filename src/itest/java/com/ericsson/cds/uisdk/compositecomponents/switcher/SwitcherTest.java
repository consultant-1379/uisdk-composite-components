package com.ericsson.cds.uisdk.compositecomponents.switcher;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkSwitcherViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SwitcherTest extends AbstractWidgetLibraryTest {

    private Switcher switcher;
    private UiSdkSwitcherViewModel switcherView;

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/controls/switcher";
    }

    @Before
    public void initializeView() {
        switcherView = tab.getView(UiSdkSwitcherViewModel.class);
        switcher = switcherView.getSwitcher();
        switcherView.ensurePageIsLoaded(tab);
    }

    @Test
    public void testSwitcherDeactivate() {
        switcher.deactivate();
        assertFalse(switcher.isActivated());
    }

    @Test
    public void testSwitcherEnable() {
        switcher.deactivate();
        assertFalse(switcher.isActivated());

        switcher.activate();
        assertTrue(switcher.isActivated());
    }

    @Test
    public void testSwitcherIsEnabled() {
        assertTrue(switcher.isActivated());
    }

    @Test
    public void testSwitcherToggle() {
        assertTrue(switcher.isActivated());

        switcher.toggle();
        assertFalse(switcher.isActivated());

        switcher.toggle();
        assertTrue(switcher.isActivated());
    }
}
