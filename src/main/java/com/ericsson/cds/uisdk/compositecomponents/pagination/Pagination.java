package com.ericsson.cds.uisdk.compositecomponents.pagination;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Link;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Sample control for dealing with UI SDK Pagination
 * 
 * @see <a href=
 *      "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/layout-components/pagination">
 *      Pagination</a>
 */
public class Pagination extends AbstractUiComponent implements Iterable<Integer> {

    public static final String CURRENT_PAGE_SELECTOR = ".ebPagination-entryAnchor_current";
    public static final String LAST_PAGE_SELECTOR = ".ebPagination-pages li:last-child a";

    private int maxAttemptsToFindTargetPage = 200;

    @UiComponentMapping(".ebPagination-next a")
    private Link next;

    @UiComponentMapping(".ebPagination-pages li a:not(.ebPagination-entryAnchor_current)")
    private List<Link> pageLinks;

    @UiComponentMapping(".ebPagination-pages")
    private Link pages;

    @UiComponentMapping(".ebPagination-previous a")
    private Link previous;

    /**
     * Returns the of current page selected
     *
     * @return int value of current page selected
     */
    public int getCurrentPage() {
        // not working if currentPage is mapped with @UiComponentMapping, use getDescendantsBySelector, see https://jira-nam.lmera.ericsson.se/browse/CIS-32853
        List<UiComponent> pages = this.pages.getDescendantsBySelector(CURRENT_PAGE_SELECTOR);
        UiComponent currentPage = pages.get(0);
        String current = currentPage.getText();
        return Integer.parseInt(current);
    }

    public Link getNext() {
        return next;
    }

    /**
     * Returns the total number of pages
     *
     * @return int value of total pages on screen
     */
    public int getPageCount() {
        if (getLastPageLinkSelector().exists()) {
            String linkText = getLastPageLinkSelector().getText();
            return Integer.parseInt(linkText);
        }
        return 1;
    }

    public List<Link> getPageLinks() {
        return pageLinks;
    }

    public Link getPrevious() {
        return previous;
    }

    /**
     * Navigates to a particular page based on the page value and how many times it takes to click
     * next/previous to find it.
     *
     * @param pageNumber - the page that is required
     * @param pageStep - click step size next/previous to find it i.e. move 3 places to find value
     */
    public void goTo(int pageNumber, int pageStep) {
        int pageCount = getPageCount();
        if (pageCount == 1) {
            return;
        }

        if (pageNumber > pageCount || pageNumber < 0) {
            return;
        }

        if (pageNumber == getCurrentPage()) {
            return;
        }
        String page = String.valueOf(pageNumber);

        int count = 0;
        while (this.maxAttemptsToFindTargetPage >= count) {
            for (Link link : getPageLinks()) {
                String text = link.getText();
                if (StringUtils.equals(page, text)) {
                    link.click();
                    return;
                }
            }
            stepPage(pageStep, pageNumber);
            count++;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new PaginationIterator(this);
    }

    /**
     * Set the max number of times to check for the correct page
     *
     * @param maxAttemptsToFindTargetPage default value = 200
     */
    public void setMaxAttemptsToFindTargetPage(int maxAttemptsToFindTargetPage) {
        this.maxAttemptsToFindTargetPage = maxAttemptsToFindTargetPage;
    }

    /**
     * Calculates if it needs to move forward and back
     *
     * @param pageStep int value to moves a certain number of times
     * @param pageNumber int value to specify the page number it requires.
     */
    public void stepPage(int pageStep, int pageNumber) {
        if (getCurrentPage() < pageNumber) {
            stepForward(pageStep);
        } else {
            stepBack(pageStep);
        }
    }

    protected Link getLastPageLinkSelector() {
        // not working if currentPage is mapped with @UiComponentMapping, use getDescendantsBySelector, see https://jira-nam.lmera.ericsson.se/browse/CIS-32853
        return this.pages.getDescendantsBySelector(LAST_PAGE_SELECTOR).get(0).as(Link.class);
    }

    private void stepBack(int pageStep) {
        for (int i = 0; i < pageStep; i++) {
            getPrevious().click();
        }
    }

    private void stepForward(int pageStep) {
        for (int i = 0; i < pageStep; i++) {
            getNext().click();
        }
    }
}
