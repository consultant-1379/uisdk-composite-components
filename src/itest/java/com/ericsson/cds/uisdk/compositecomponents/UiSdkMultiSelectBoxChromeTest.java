package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkMultiSelectBoxViewModel;
import com.ericsson.cifwk.taf.ui.BrowserType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UiSdkMultiSelectBoxChromeTest extends AbstractWidgetLibraryTest {

    private UiSdkMultiSelectBox multiSelectBox;

    private static final String ITEM1 = "Value 1";

    private static final String ITEM2 = "Value 2";

    private static final String ITEM3 = "Value 3";

    @Before
    public void initializeView() {
        UiSdkMultiSelectBoxViewModel view = tab.getView(UiSdkMultiSelectBoxViewModel.class);
        multiSelectBox = view.getMultiSelectBox();
        view.ensurePageIsLoaded(tab);
    }

    @Test
    @Ignore
    public void clickItem() {

        // checking initial state
        List<String> selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM1, ITEM2);

        // selecting by click
        multiSelectBox.clickItem(ITEM3);

        // checking final state
        selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/#ui-showcase/selectors/select-box";
    }

    // Old FireFox browser / driver fails clickItem() test. To be removed on driver update.
    protected BrowserType getBrowserType() {
        return BrowserType.CHROME;
    }
}
