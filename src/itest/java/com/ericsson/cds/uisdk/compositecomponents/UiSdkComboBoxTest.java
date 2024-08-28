package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkComboBoxViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UiSdkComboBoxTest extends AbstractWidgetLibraryTest {

    private static final String ITEM1 = "Name 1";

    private static final String ITEM2 = "Name 2";

    private static final String ITEM3 = "Name 3";

    private UiSdkComboBox comboBox;

    @Before
    public void initializeView() {
        UiSdkComboBoxViewModel view = tab.getView(UiSdkComboBoxViewModel.class);
        comboBox = view.getComboBox();
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void getItems() {
        List<String> items = comboBox.getItems();
        assertThat(items).containsExactly(ITEM1, ITEM2, ITEM3);
    }

    @Test
    public void getEmptyValue() {
        String initialValue = comboBox.getValue();
        assertThat(initialValue).isEqualTo("");
    }

    @Test
    public void setPredefinedValue() {
        comboBox.clickItem(ITEM2.toUpperCase());
        assertThat(comboBox.getValue()).isEqualTo(ITEM2);
    }

    @Test
    public void setCustomValue() {
        comboBox.setValue(ITEM2.toUpperCase());
        assertThat(comboBox.getValue()).isEqualTo(ITEM2.toUpperCase());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/combo-box";
    }
}