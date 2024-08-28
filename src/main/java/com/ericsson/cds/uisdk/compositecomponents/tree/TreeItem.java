package com.ericsson.cds.uisdk.compositecomponents.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.CheckBox;
import com.ericsson.cifwk.taf.ui.sdk.Label;

/**
 * Sample control for dealing with UI SDK TreeItem
 * 
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/tree">
 *      Tree</a>
 */
public class TreeItem extends AbstractUiComponent implements HasItems<TreeItem> {

    @UiComponentMapping(".ebCheckbox")
    private CheckBox checkBox;

    @UiComponentMapping(".ebTreeItem-expandButton .ebIcon_rightArrow")
    private UiComponent collapsedIcon;

    @UiComponentMapping(".ebTreeItem-content")
    private UiComponent content;

    @UiComponentMapping(".ebTreeItem-expandButton")
    private Button expandButton;

    @UiComponentMapping(".ebTreeItem-expandButton .ebIcon_downArrow")
    private UiComponent expandedIcon;

    @UiComponentMapping(".ebTreeItem-expandButton .ebIcon")
    private UiComponent expandIcon;

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "/ul/*")
    private List<TreeItem> firstLevelItems;

    @UiComponentMapping("ul.ebTree > li.ebTreeItem")
    private List<TreeItem> items;

    @UiComponentMapping(".ebTreeItem-content > .ebTreeItem-label")
    private Label label;

    @UiComponentMapping(".ebTreeItem-checkbox span")
    private UiComponent partialCheckMarker;

    public void check() {
        checkBox.select();
    }

    @Override
    public void click() {
        label.click();
    }

    public void collapse() {
        if (isExpandable() && isExpanded()) {
            expandButton.click();
            waitForComponent(collapsedIcon);
        }
    }

    public void deselect() {
        if (isSelected()) {
            content.click();
        }
    }

    public void expand() {
        if (isExpandable() && !isExpanded()) {
            expandButton.click();
            waitForComponent(expandedIcon);
        }
    }

    public TreeItem getChildItem(String itemName) {
        final boolean useRegex = itemName.startsWith(Tree.REGEX_PREFIX);
        if (useRegex) {
            itemName = itemName.replace(Tree.REGEX_PREFIX, "");
        }

        for (TreeItem treeItem : getFirstLevelItems()) {
            if (useRegex) {
                // if label value is regex, check if current item matches regex 
                Matcher matcher = Pattern.compile(itemName).matcher(treeItem.getLabel());
                if (matcher.find()) {
                    return treeItem;
                }
            } else {
                if (itemName.equals(treeItem.getLabel())) {
                    return treeItem;
                }
            }
        }

        return null;
    }

    public List<TreeItem> getFirstLevelItems() {
        List<TreeItem> itemsToReturn = new ArrayList<>();
        if (!isExpandable()) {
            return itemsToReturn;
        }

        expand();
        return firstLevelItems;
    }

    @Override
    public List<TreeItem> getItems() {
        return items;
    }

    public String getLabel() {
        return label.getText();
    }

    public boolean isChecked() {
        return checkBox.isSelected() && !isPartiallyChecked();
    }

    public boolean isExpandable() {
        return this.getProperty("class").contains("ebTreeItem_expandable");
    }

    public boolean isExpanded() {
        return expandIcon.getProperty("class").contains("ebIcon_downArrow");
    }

    public boolean isPartiallyChecked() {
        return partialCheckMarker.getProperty("class").contains("ebCheckbox-inputStatus_triple");
    }

    @Override
    public boolean isSelected() {
        return content.getProperty("class").contains("ebTreeItem-content_selected");
    }

    public void select() {
        if (!isSelected()) {
            content.click();
        }
    }

    public void uncheck() {
        checkBox.deselect();
    }

    private void waitForComponent(final UiComponent component) {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return component.isDisplayed();
            }
        });
    }
}
