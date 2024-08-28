package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkMultiSelectBoxViewModel;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class UiSdkMultiSelectBoxTest extends AbstractWidgetLibraryTest {

    private UiSdkMultiSelectBox multiSelectBox;

    private static final String ITEM1 = "Value 1";
    private static final String ITEM2 = "Value 2";
    private static final String ITEM3 = "Value 3";
    private static final String ITEM4 = "Value 4";
    private static final String TEMP1 = "Temp 1";
    private static final String TEMP2 = "Temp 2";
    private static final String TEMP3 = "Temp 3";

    private UiSdkMultiSelectBoxViewModel view;

    @Before
    public void initializeView() {
        view = tab.getView(UiSdkMultiSelectBoxViewModel.class);
        multiSelectBox = view.getMultiSelectBox();
    }

    @Test
    public void getItems() {
        List<String> items = multiSelectBox.getItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3, ITEM4);
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
        multiSelectBox.setValues(asList(ITEM2.toLowerCase(), ITEM3.toUpperCase()));

        // checking final state
        selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(ITEM2, ITEM3);
    }

    @Test
    public void selectAll() {
        view.generateWidget();

        // checking initial state
        List<String> selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(TEMP1);

        // setting multiple values
        multiSelectBox.selectAll();

        // checking final state
        selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(TEMP1, TEMP2, TEMP3);
    }

    @Test
    public void deselectAll() {
        view.generateWidget();

        // checking initial state
        List<String> selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly(TEMP1);

        // setting multiple values
        multiSelectBox.deselectAll();

        // checking final state
        selectedItems = multiSelectBox.getValues();
        assertThat(selectedItems).containsExactly();
    }

    @Test
    public void setValuesWithSideEffects() {
        view.generateWidget();

        UiSdkMultiSelectBox selectBox = view.getLastMultiSelectBox(tab);
        assertThat(selectBox.getValues()).isEmpty();

        selectBox.setValues(asList(ITEM1, ITEM2));
        assertThat(selectBox.getValues()).containsExactly(ITEM1, ITEM2);
    }

    @Test
    public void setNonExistingValues() {
        try {
            multiSelectBox.setValues(asList(ITEM2, "Non existing 1", ITEM3, "Non existing 2"));
            fail();
        } catch (UiComponentNotFoundException e) {
            assertThat(e.getMessage())
                    .contains("Item name(-s) '")
                    .contains("Non existing 1")
                    .contains("Non existing 2")
                    .contains("' not found within ")
                    .contains(String.format("[%s, %s, %s, %s]", ITEM1, ITEM2, ITEM3, ITEM4));
        }
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/#ui-showcase/selectors/multi-select-box";
    }
}
