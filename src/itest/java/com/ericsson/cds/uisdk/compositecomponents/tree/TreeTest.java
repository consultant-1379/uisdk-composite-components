package com.ericsson.cds.uisdk.compositecomponents.tree;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkTreeViewModel;
import com.ericsson.cifwk.taf.ui.UI;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class TreeTest extends AbstractWidgetLibraryTest {

    private Tree tree;

    @Before
    public void initializeView() {
        UiSdkTreeViewModel view = tab.getView(UiSdkTreeViewModel.class);
        tree = view.getMyTree();
        view.ensurePageIsLoaded(tab);
    }

    @Test
    public void findItem() {

        // happy path
        String expectedResult = "Item 1.2";
        TreeItem component = tree.findItem(expectedResult);
        assertEquals(component.getLabel(), expectedResult);

        // item not found
        component = tree.findItem("badValue");
        assertNull(component);
    }

    @Test
    public void check() {

        // partially checked parent item
        TreeItem item11 = tree.findItem("Item 1.1");
        assertThat(item11.isChecked()).isFalse();
        assertThat(item11.isPartiallyChecked()).isTrue();

        // checked child
        TreeItem item111 = tree.findItem("Item 1.1.1");
        assertThat(item111.isChecked()).isTrue();
        assertThat(item111.isPartiallyChecked()).isFalse();

        // unchecked child
        TreeItem item112 = tree.findItem("Item 1.1.2");
        assertThat(item112.isChecked()).isFalse();
        assertThat(item112.isPartiallyChecked()).isFalse();

        // checking all children
        item112.check();
        assertThat(item112.isChecked()).isTrue();

        // parent item becomes checked
        assertThat(item11.isChecked()).isTrue();
        assertThat(item11.isPartiallyChecked()).isFalse();
    }

    @Test
    public void doubleCheck() {

        TreeItem item = tree.findItem("Item 1.1.2");
        assertThat(item.isChecked()).isFalse();

        // checking
        item.check();
        assertThat(item.isChecked()).isTrue();

        // double checking has no effect
        item.check();
        assertThat(item.isChecked()).isTrue();

        // un-checking
        item.uncheck();
        assertThat(item.isChecked()).isFalse();

        // double un-checking has no effect
        item.uncheck();
        assertThat(item.isChecked()).isFalse();
    }

    @Test
    public void select() {
        TreeItem item1 = tree.findItem("Item 1.1");
        TreeItem item2 = tree.findItem("Item 1.2");

        // selecting via click
        assertThat(item1.isSelected()).isFalse();
        item1.click();
        assertThat(item1.isSelected()).isTrue();

        // selecting via api
        assertThat(item2.isSelected()).isFalse();
        item2.select();
        assertThat(item2.isSelected()).isTrue();
    }

    @Test
    public void expand() {

        TreeItem item1 = tree.findItem("Item 1");
        assertThat(item1.isExpandable()).isEqualTo(true);
        assertThat(item1.isChecked()).isEqualTo(false);

        // double expand
        assertThat(item1.isExpanded()).isEqualTo(false);
        item1.expand();
        assertThat(item1.isExpanded()).isEqualTo(true);
        item1.expand();
        assertThat(item1.isExpanded()).isEqualTo(true);

        // double collapse
        item1.collapse();
        assertThat(item1.isExpanded()).isEqualTo(false);
        item1.collapse();
        assertThat(item1.isExpanded()).isEqualTo(false);

        // second level items
        assertThat(tree.findItem("Item 1.1").isExpandable()).isEqualTo(true);
        assertThat(tree.findItem("Item 1.2").isExpandable()).isEqualTo(false);
    }

    @Test
    public void firstLevelItems() {

        TreeItem item1 = tree.findItem("Item 1");

        List<TreeItem> firstLevelItems = item1.getFirstLevelItems();

        assertTrue(firstLevelItems.size() == 2);
        assertTrue(firstLevelItems.get(0).getLabel().equals("Item 1.1"));
        assertTrue(firstLevelItems.get(1).getLabel().equals("Item 1.2"));
    }

    @Test
    public void firstTreeLevelItems() {
        List<TreeItem> firstLevelItems = tree.getFirstLevelItems();

        assertTrue(firstLevelItems.size() == 1);
        assertTrue(firstLevelItems.get(0).getLabel().equals("Item 1"));
    }

    @Test
    public void getChildItem() {
        assertNotNull(tree.findItem("Item 1").getChildItem("Item 1.2"));

        assertNull(tree.findItem("Item 1").getChildItem("Item 1.1.1"));
    }

    @Test
    public void findItemWithPath() {

        // Item 1   -> Item 1.1   -> Item 1.1.1
        TreeItem treeItem = tree.findItemWithPath("Item 1", "Item 1.1", "Item 1.1.1");
        assertTrue(treeItem.getLabel().equals("Item 1.1.1"));

        // Item 1   -> Item 1.1   -> Item 1.1.2
        treeItem = tree.findItemWithPath("Item 1", "Item 1.1", "Item 1.1.2");
        assertTrue(treeItem.getLabel().equals("Item 1.1.2"));
    }

    @Test
    public void findItemWithRegexPath() {

        // Item 1   -> Item 1.1   -> Item 1.1.2
        // "Item 1" -> "Item 1.*" -> ".*2$"
        TreeItem treeItem = tree.findItemWithPath("Item 1", Tree.REGEX_PREFIX + "Item 1.*",
                Tree.REGEX_PREFIX + ".*2$");

        assertTrue(treeItem.getLabel().equals("Item 1.1.2"));
    }

    @Test
    public void expandCollapseAllItems() {
        TreeItem item1 = tree.findItem("Item 1.1.2");
        assertTrue(item1.isDisplayed());

        tree.collapseAllItems();
        assertFalse(item1.isDisplayed());

        tree.expandAllItems();
        assertTrue(item1.isDisplayed());
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html";
    }
}
