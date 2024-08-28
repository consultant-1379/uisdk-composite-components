package com.ericsson.cds.uisdk.compositecomponents.progressbar;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.progressbar.ProgressBar;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkProgressBarViewModel;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgressBarTest extends AbstractWidgetLibraryTest {

    private ProgressBar progressBar;
    private UiSdkProgressBarViewModel progressBarView;

    @Before
    public void initializeView() {
        progressBarView = tab.getView(UiSdkProgressBarViewModel.class);
        progressBar = progressBarView.getProgressBar();
        progressBarView.ensurePageIsLoaded(tab);
    }

    @Test(expected = WaitTimedOutException.class)
    public void testProgressBarWaitUntilPercentageHigher() {
        progressBar.waitUntilPercentage(60);
    }

    @Test
    public void testProgressBarWaitUntilPercentageLower() {
        progressBar.waitUntilPercentage(30);
    }

    @Test
    public void testProgressBarGetValue() {
        int value = progressBar.getValue();
        assertEquals(50, value);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/information-display/progress-bar";
    }
}
