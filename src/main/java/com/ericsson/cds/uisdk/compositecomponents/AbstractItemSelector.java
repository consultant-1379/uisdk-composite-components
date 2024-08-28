package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cds.uisdk.compositecomponents.modaldialog.ModalDialog;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasClickableItems;
import com.ericsson.cds.uisdk.compositecomponents.tree.HasItems;
import com.ericsson.cifwk.meta.API;
import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.GenericPredicate;
import com.ericsson.cifwk.taf.ui.core.GlobalScope;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.ericsson.cifwk.taf.ui.sdk.Button;
import com.google.common.base.Function;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ericsson.cifwk.meta.API.Quality.Experimental;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Default implementation of drop-down-like components.
 *
 * @author Mihails Volkovs mihails.volkovs@ericsson.com
 *         Date: 07.07.2016
 */
abstract class AbstractItemSelector extends AbstractUiComponent implements HasItems<String>, HasClickableItems {

    public static final String REGEX_PREFIX = "#REGEX#";

    @GlobalScope
    @UiComponentMapping(selector = ".ebDialogBox")
    private ModalDialog modalDialog;

    @Override
    public void click() {
        getComponentOpener().click();
    }

    /**
     * Clicks the item in the items list.
     * <p>
     * If the item is not in the list throws a UiComponentNotFoundException.
     */
    @Override
    public void clickItem(String itemName) throws UiComponentNotFoundException {
        expand();

        try {
            Thread.sleep(4000);
        }catch (InterruptedException e){
           e.printStackTrace();
        }


        clickItemInternally(itemName);
        if (collapseAfterClickFlag()) {
            collapse();
        }
    }

    /**
     * Clicks the item in the items list. Items list should be manually opened before calling this
     * method.
     * <p>
     * If the item is not in the list throws a UiComponentNotFoundException.
     * <p>
     * Experimental API. Is added to support older UI SDK library versions. Please use clickItem()
     * method instead.
     */
    @API(Experimental)
    public void clickVisibleItem(String itemName) throws UiComponentNotFoundException {
        clickItemInternally(itemName);
    }

    /**
     * Returns list of all item names.
     */
    @Override
    public List<String> getItems() {
        expand();
        List<String> data = getItemsInternally();
        collapse();

        return data;
    }

    /**
     * Returns list of all item names. Assumes that component was previously expanded or opened by
     * clicking according button.
     * <p>
     * Experimental API. Is added to support older UI SDK library versions. Please use getItems()
     * method instead.
     */
    @API(Experimental)
    public List<String> getVisibleItems() {
        return getItemsInternally();
    }

    @Override
    public boolean isEnabled() {
        return getComponentOpener().isEnabled();
    }

    /**
     * Click item internally.
     *
     * @param itemName the exact item value to select or regex (value must start with {@value #REGEX_PREFIX})
     */
    protected void clickItemInternally(String itemName) {

        // add ability to provide regex instead of exact value to select
        final boolean useRegex = itemName.startsWith(REGEX_PREFIX);
        if (useRegex) {
            itemName = itemName.replace(REGEX_PREFIX, "");
        }

        List<String> items = newArrayList();
        for (UiComponent label : getItemComponents()) {
            final String item = label.getText();
            items.add(item);

            if (useRegex) {
                // if itemName value is regex, check if current item matches regex 
                Matcher matcher = Pattern.compile(itemName).matcher(item);
                if (matcher.find()) {
                    label.click();
                    return;
                }
            } else {
                if (StringUtils.equalsIgnoreCase(item, itemName)) {
                    label.click();
                    return;
                }
            }

        }
        String details = items.isEmpty() ? " (please make sure you enhanced / opened items)" : "";
        String message = String.format("Item with name '%s' is not found within %s%s", itemName, items, details);
        throw new UiComponentNotFoundException(message);
    }

    protected void collapse() {
        // collapse only if needed
        if (isExpanded() && getComponentOpener().isDisplayed()) {
            try {
                getComponentOpener().click();
            } catch (Exception e) {
                if (modalDialog.isDisplayed()) {
                    // ignore click error if popup modal dialog was opened while collapsing
                } else {
                    throw e;
                }
            }
        }
    }

    protected boolean collapseAfterClickFlag() {
        return false; // default is false
    }

    protected void expand() {
        getComponentOpener().click(); // important- this will close items holder from other component (if any)
        expandIfNeeded();
    }

    protected void expandIfNeeded() {
        if (!isExpanded()) {
            waitUntilComponentOpenerIsEnabled();
            getComponentOpener().click();
        }
    }

    protected abstract Button getComponentOpener();

    protected abstract List<? extends UiComponent> getItemComponents();

    protected abstract UiComponent getItemsHolder();

    protected List<String> getItemsInternally() {
        expandIfNeeded();
        return newArrayList(transform(getItemComponents(), new Function<UiComponent, String>() {

            @Override
            public String apply(UiComponent item) {
                return item.getText();
            }
        }));
    }

    /**
     * Returns true if this OR ANY OTHER drop-down-like component is expanded
     * (limitation by UI SDK components non-modular HTML representation)
     */
    protected boolean isExpanded() {
        return getItemsHolder().isDisplayed();
    }

    /**
     * Wait until component opener element is displayed and enabled
     */
    protected void waitUntilComponentOpenerIsEnabled() {
        waitUntil(new GenericPredicate() {

            @Override
            public boolean apply() {
                Button componentOpener = getComponentOpener();
                return (componentOpener.isDisplayed()
                        && !componentOpener.getProperty("class").contains("_disabled")
                        && componentOpener.getProperty("disabled") == null);
            }
        });
    }

}
