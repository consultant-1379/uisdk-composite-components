package com.ericsson.cds.uisdk;

import com.ericsson.cifwk.taf.ui.Browser;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.BrowserType;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractWidgetLibraryTest extends AbstractSeleniumTest {

    private final static int PAGE_LOAD_TIMEOUT = 30000;
    
    protected Browser browser;
    protected BrowserTab tab;

    @Before
    public void setUp() {
        browser = UI.newBrowser(getBrowserType());
        browser.setSize(1920, 1080); //prevent "element not visible exception"
        tab = browser.open(getWidgetUrl());

        // clear local and session storage (some widgets can preserve settings in local storage)
        tab.evaluate("window.localStorage.clear();");
        tab.evaluate("window.sessionStorage.clear();");
    }

    @After
    public void tearDown() {
        if (browser != null && !browser.isClosed()) {
            browser.close();
        }
    }

    protected abstract String getWidgetUrl();

    protected BrowserType getBrowserType() {
        return BrowserType.FIREFOX;
    }

    protected void waitForBrowserToRedirect(final String initialURL) {

        tab.waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return !browser.getCurrentWindow().getCurrentUrl().equals(initialURL);
            }
        });
    }

    public static void ensurePageIsLoaded(UiComponent componentToDisplay, BrowserTab tab, GenericViewModel viewModel) {
        try {
            viewModel.waitUntilComponentIsDisplayed(componentToDisplay, PAGE_LOAD_TIMEOUT);
        } catch (WaitTimedOutException e) {
            // try again if failed due to slow page loading
            tab.refreshPage();
            viewModel.waitUntilComponentIsDisplayed(componentToDisplay, PAGE_LOAD_TIMEOUT);
        }
    }
}
