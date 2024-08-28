package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkComboMultiSelectBoxViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class UiSdkComboMultiSelectBoxTest extends AbstractWidgetLibraryTest {

    private UiSdkComboMultiSelectBox multiSelectBox;

    private static final String ITEM0 = "Value";

    private static final String ITEM1 = "Value 1";

    private static final String ITEM2 = "Value 2 long";

    private static final String ITEM3 = "Value 3 longer";

    private static final String ITEM4 = "Value 4 very long";

    private static final String ITEM5 = "Value 5";

    private UiSdkComboMultiSelectBoxViewModel view;

    @Before
    public void initializeView() {
        view = tab.getView(UiSdkComboMultiSelectBoxViewModel.class);
        multiSelectBox = view.getComboMultiSelect();
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItems() {
        List<String> items = multiSelectBox.getItems();
        assertThat(items).containsExactly(ITEM0, ITEM1, ITEM2, ITEM3, ITEM4, ITEM5);
    }

    @Test
    public void getValues() {
        List<String> selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM1, ITEM2);
    }

    @Test
    public void setValues() {

        // checking initial state
        List<String> selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM1, ITEM2);

        // setting multiple values
        multiSelectBox.setValues(asList(ITEM3.toLowerCase(), ITEM2.toUpperCase()));

        // checking final state
        selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM3, ITEM2);
    }

    @Test
    public void clickItem() {

        // checking initial state
        assertThat(multiSelectBox.getValues()).containsExactly(ITEM1, ITEM2);

        // selecting by click
        multiSelectBox.clickItem(ITEM3);

        // checking final state
        assertThat(multiSelectBox.getValues()).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Test
    public void setNonExistingValues() {
        try {
            multiSelectBox.setValues(asList(ITEM2, "Non existing 1", ITEM3, "Non existing 2"));
            fail();
        } catch (UiComponentNotFoundException e) {
            assertThat(e.getMessage())
                    .contains("Item with name '")
                    .contains("Non existing 1")
                    .contains(format("' is not found within [%s, %s, %s, %s, %s, %s]",
                            ITEM0, ITEM1, ITEM2, ITEM3, ITEM4, ITEM5));
        }
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/combo-multi-select-box";
    }
}