package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkSelectBoxViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class UiSdkSelectBoxTest extends AbstractWidgetLibraryTest {

    private static final String ITEM1 = "Name 1";
    private static final String ITEM2 = "Name 2";
    private static final String ITEM3 = "Name 3";

    private UiSdkSelectBox selectBox;

    @Before
    public void initializeView() {
        UiSdkSelectBoxViewModel view = tab.getView(UiSdkSelectBoxViewModel.class);
        selectBox = view.getSelectBox();
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItems() {
        List<String> items = selectBox.getItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Test
    public void getSetValue() {
        assertThat(selectBox.getValue()).isEqualTo(ITEM1);

        selectBox.setValue(ITEM2.toUpperCase());
        assertThat(selectBox.getValue()).isEqualTo(ITEM2);

        selectBox.clickItem(ITEM3.toLowerCase());
        assertThat(selectBox.getValue()).isEqualTo(ITEM3);
    }

    @Test
    public void setNonExistingValue() {
        try {
            selectBox.setValue("Non existing item");
            fail();
        } catch (UiComponentNotFoundException e) {
            String expected = String.format("Item with name 'Non existing item' is not found within [%s, %s, %s]", ITEM1, ITEM2, ITEM3);
            assertThat(e.getMessage()).isEqualTo(expected);
        }
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/#ui-showcase/selectors/select-box";
    }
}