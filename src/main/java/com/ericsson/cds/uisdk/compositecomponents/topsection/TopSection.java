package com.ericsson.cds.uisdk.compositecomponents.topsection;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cds.uisdk.compositecomponents.UiSdkDropDownMenu;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.core.UiComponentPredicates;
import com.ericsson.cifwk.taf.ui.core.WaitTimedOutException;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.ericsson.cifwk.taf.ui.sdk.Link;

/**
 * Representation of UI SDK Top Section.
 * 
 * @since 1.0.37
 * @author ehrvkla
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#top-section-example">
 *      Top Section example</a>
 */
public class TopSection extends AbstractUiComponent {

    private static UiComponent tempComponentWithAction;

    @UiComponentMapping(selector = ".elLayouts-QuickActionBar-items .ebBtn:not(.ebDropdown-button)")
    protected List<Button> centerButtons;
    @UiComponentMapping(selector = ".elLayouts-QuickActionBar-left .ebBtn:not(.ebDropdown-button)")
    protected List<Button> leftButtons;
    @UiComponentMapping(selector = ".elLayouts-QuickActionBar-right .ebBtn:not(.ebDropdown-button)")
    protected List<Button> rightButtons;

    @UiComponentMapping(selector = ".elLayouts-QuickActionBar-items .elLayouts-ActionBarDropdown .ebDropdown")
    protected List<UiSdkDropDownMenu> dropdowns;

    @UiComponentMapping(selector = "a.elLayouts-ActionBarItem")
    protected List<Link> hyperlinks;

    @UiComponentMapping(selector = ".elLayouts-QuickActionBar_context")
    protected UiComponent topSectionContext;

    public void clickAction(String actionName) {
        clickAction(actionName, false);
    }

    public void clickButton(String buttonText) {
        getVisibleButton(buttonText).click();
    }

    public void clickContextualAction(String actionName) {
        clickAction(actionName, true);
    }

    public void clickDropdownItem(String itemName) {
        waitForLoaderToHide();

        for (UiSdkDropDownMenu dropdown : getDropdowns()) {
            if (listToLowercase(dropdown.getItems()).contains(itemName.toLowerCase())) {
                dropdown.clickItem(itemName);
                return;
            }
        }

        throw new UiComponentNotFoundException("TopSection dropdown item '" + itemName + "' not found.");
    }

    public void clickHyperLink(String hyperlinkName) {
        getVisibleHyperlink(hyperlinkName).click();
    }

    public List<Button> getButtons() {
        waitForLoaderToHide();

        List<Button> visibleButtons = new ArrayList<>();
        for (Button button : leftButtons) {
            if (button.isDisplayed()) {
                visibleButtons.add(button);
            }
        }
        for (Button button : centerButtons) {
            if (button.isDisplayed()) {
                visibleButtons.add(button);
            }
        }
        for (Button button : rightButtons) {
            if (button.isDisplayed()) {
                visibleButtons.add(button);
            }
        }
        return visibleButtons;
    }

    public List<UiSdkDropDownMenu> getDropdowns() {
        waitForLoaderToHide();

        List<UiSdkDropDownMenu> visibleDropdowns = new ArrayList<>();
        for (UiSdkDropDownMenu dropdown : dropdowns) {
            if (dropdown.isDisplayed()) {
                visibleDropdowns.add(dropdown);
            }
        }
        return visibleDropdowns;
    }

    public List<Link> getHyperlinks() {
        waitForLoaderToHide();

        List<Link> visibleHyperlinks = new ArrayList<>();
        for (Link hyperlink : hyperlinks) {
            if (hyperlink.isDisplayed()) {
                visibleHyperlinks.add(hyperlink);
            }
        }
        return visibleHyperlinks;
    }

    protected void clickAction(String actionName, boolean waitForContextualActions) {
        if (waitForContextualActions) {
            waitUntil(topSectionContext, UiComponentPredicates.DISPLAYED);
        }

        UiComponent component;
        try {
            // wait for action to appear
            component = waitForActionToAppear(actionName);
        } catch (WaitTimedOutException e) {
            throw new UiComponentNotFoundException("Top Section actionName '" + actionName + "' not found!");
        }

        // click action
        if (component instanceof Button) {
            ((Button) component).click();
        } else if (component instanceof UiSdkDropDownMenu) {
            ((UiSdkDropDownMenu) component).clickItem(actionName);
        } else if (component instanceof Link) {
            ((Link) component).click();
        } else {
            throw new UiComponentNotFoundException("Top Section actionName '" + actionName + "' not found!");
        }
    }

    protected List<String> getAvailableActions() {
        waitForLoaderToHide();

        List<String> actions = new ArrayList<String>();
        for (Button button : getButtons()) {
            final String buttonText = button.getText().trim();
            if (!buttonText.isEmpty() && button.isEnabled()) {
                actions.add(buttonText);
            }
        }

        for (UiSdkDropDownMenu dropDown : getDropdowns()) {
            if (dropDown.isEnabled()) {
                actions.addAll(dropDown.getItems());
            }
        }
        for (Link link : getHyperlinks()) {
            actions.add(link.getText());
        }

        return actions;
    }

    protected Button getVisibleButton(String buttonText) {
        waitForLoaderToHide();

        for (Button normalButton : getButtons()) {
            if (normalButton.getText().equalsIgnoreCase(buttonText)) {
                return normalButton;
            }
        }
        throw new UiComponentNotFoundException("TopSection button with text '" + buttonText + "' not found.");
    }

    protected Link getVisibleHyperlink(String hyperlinkText) {
        waitForLoaderToHide();
        for (Link hyperLink : getHyperlinks()) {
            if (hyperLink.getText().equalsIgnoreCase(hyperlinkText)) {
                return hyperLink;
            }
        }
        throw new UiComponentNotFoundException("TopSection hyperlink with text '" + hyperlinkText + "' not found.");
    }

    protected List<String> listToLowercase(List<String> list) {
        List<String> newList = new ArrayList<String>();
        for (String string : list) {
            newList.add(string.toLowerCase());
        }
        return newList;
    }

    protected UiComponent waitForActionToAppear(final String actionName) {

        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                for (Button button : getButtons()) {
                    final String buttonText = button.getText().trim();
                    if (!buttonText.isEmpty() && buttonText.equalsIgnoreCase(actionName)) {
                        tempComponentWithAction = button; // store component in temp cariable
                        return true;
                    }
                }

                for (UiSdkDropDownMenu dropDown : getDropdowns()) {
                    if (!dropDown.isEnabled()) { // skip disabled dropdowns
                        continue;
                    }
                    if (listToLowercase(dropDown.getItems()).contains(actionName.toLowerCase())) {
                        tempComponentWithAction = dropDown;// store component in temp cariable
                        return true;
                    }
                }

                for (Link link : getHyperlinks()) {
                    if (link.getText().trim().equalsIgnoreCase(actionName)) {
                        tempComponentWithAction = link;// store component in temp cariable
                        return true;
                    }
                }
                return false;
            }
        });

        return tempComponentWithAction;
    }

    protected void waitForLoaderToHide() {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                return !getProperty("class").contains("elLayouts-QuickActionBar_loading");
            }
        });
    }

}
