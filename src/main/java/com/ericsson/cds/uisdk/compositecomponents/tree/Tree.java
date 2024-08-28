package com.ericsson.cds.uisdk.compositecomponents.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * UI SDK Tree component
 *
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/tree">
 *      Tree</a>
 */
public class Tree extends AbstractUiComponent implements HasItems<TreeItem> {

    public static final String REGEX_PREFIX = "#REGEX#";

    private static boolean treeItemSatisfiesLabel(TreeItem treeNode, String label, boolean useRegex) {
        if (useRegex) {
            // if label value is regex, check if current item matches regex
            String newLabel = label.replace(REGEX_PREFIX, "");
            Matcher matcher = Pattern.compile(newLabel).matcher(treeNode.getLabel());
            if (matcher.find()) {
                return true;
            }
        } else {
            if (label.equals(treeNode.getLabel())) {
                return true;
            }
        }
        return false;
    }

    @UiComponentMapping(selectorType = SelectorType.XPATH, selector = "/*")
    private List<TreeItem> firstLevelItems;

    @UiComponentMapping("ul.ebTree > li.ebTreeItem")
    private List<TreeItem> items;

    public void collapseAllItems() {
        final String javaScript = "var expanders = element.getElementsByClassName('ebTreeItem_expandable');\n"
                + "\n" + "for (arrowIndex = 0; arrowIndex < expanders.length; ++arrowIndex) {\n"
                + "    var arrows = expanders[arrowIndex].getElementsByClassName('ebIcon_downArrow');\n"
                + "    for(i=0; i < arrows.length; i++){\n" + "        arrows[i].click();\n" + "    }\n" + "}";

        evaluate(javaScript);
        UI.pause(700); // wait for collapse animation to finish
    }

    public void expandAllItems() {
        String javaScript = "var expanders = element.getElementsByClassName('ebTreeItem_expandable');\n" + "\n"
                + "for (arrowIndex = 0; arrowIndex < expanders.length; ++arrowIndex) {\n"
                + "    var arrows = expanders[arrowIndex].getElementsByClassName('ebIcon_rightArrow');\n"
                + "    for(i=0; i < arrows.length; i++) {\n"
                + "        if (window.getComputedStyle(arrows[i]).display !== 'none') {\n"
                + "            arrows[i].click();\n"
                + "        }\n"
                + "    }\n"
                + "}";

        evaluate(javaScript);
        UI.pause(700); // wait for expand animation to finish
    }

    /**
     * Find item anywhere in the tree
     */
    public TreeItem findItem(final String label) {

        final boolean useRegex = label.startsWith(REGEX_PREFIX);

        return traverse(new Predicate<TreeItem>() {

            @Override
            public boolean apply(TreeItem treeNode) {
                return treeItemSatisfiesLabel(treeNode, label, useRegex);
            }
        });
    }

    /**
     * Find item with path.
     *
     * @param itemPathNames the exact path to tree item (items must be set for every parent level), for
     *        regex expression, set prefix {@value #REGEX_PREFIX}
     * @return the tree item
     */
    public TreeItem findItemWithPath(String... itemPathNames) {

        Preconditions.checkNotNull(itemPathNames);
        Preconditions.checkArgument(itemPathNames.length > 0);

        if (itemPathNames.length == 1) {
            return findItem(itemPathNames[0]);
        }

        List<TreeItem> nextTreeItems = new ArrayList<>();
        nextTreeItems.addAll(this.getFirstLevelItems());

        TreeItem result = findTreeItemByPath(nextTreeItems, itemPathNames);
        return result;
    }

    public List<TreeItem> getFirstLevelItems() {
        return firstLevelItems;
    }

    @Override
    public List<TreeItem> getItems() {
        return items;
    }

    /**
     * Traverse recursively the tree in pre-order and returns first tree item satisfying given
     * predicate.
     *
     * @return tree item found. Will return null if nothing it found.
     */
    public TreeItem traverse(Predicate<TreeItem> predicate) {
        return traverse(items, predicate);
    }

    private TreeItem findTreeItemByPath(List<TreeItem> nextTreeItems, String... remainingPath) {
        if (remainingPath.length == 0) {
            return null;
        }

        final String currentPath = remainingPath[0];
        remainingPath = (String[]) ArrayUtils.removeElement(remainingPath, remainingPath[0]);

        for (TreeItem currentTreeItem : nextTreeItems) {
            if (treeItemSatisfiesLabel(currentTreeItem, currentPath, true)) {

                if (remainingPath.length == 0) {
                    // item found
                    return currentTreeItem;
                }

                String[] remainingPathNew = new String[remainingPath.length];
                //                remainingPathNew = (String[]) ArrayUtils.removeElement(remainingPath, remainingPath[0]);
                System.arraycopy(remainingPath, 0, remainingPathNew, 0, remainingPath.length);

                List<TreeItem> nextTreeItemsNew = currentTreeItem.getFirstLevelItems();
                if (nextTreeItemsNew.size() > 0) {
                    // go level deeper
                    return findTreeItemByPath(nextTreeItemsNew, remainingPath);
                }
            }
        }

        return null;
    }

    private TreeItem traverse(List<TreeItem> treeItems, Predicate<TreeItem> predicate) {
        for (TreeItem treeItem : treeItems) {

            // predicate is satisfied - return
            if (predicate.apply(treeItem)) {
                return treeItem;
            }

            // expanding child and searching there
            if (treeItem.isExpandable() && !treeItem.isExpanded()) {
                treeItem.expand();
            }

            // potentially nothing is found - taking next child
            TreeItem candidate = traverse(treeItem.getItems(), predicate);
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
    }

}
