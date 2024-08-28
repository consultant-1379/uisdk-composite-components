package com.ericsson.cds.uisdk.compositecomponents.pagination;

import java.util.Iterator;

import com.ericsson.cds.uisdk.compositecomponents.pagination.Pagination;

/**
 * The Class PaginationIterator that is used to iterate over table pages.
 */
public class PaginationIterator implements Iterator<Integer> {

    /** The initial state. */
    private boolean initialState;

    /** The pagination. */
    private Pagination pagination;

    /** The pagination displayed. */
    private boolean paginationDisplayed;

    /**
     * Instantiates a new pagination iterator.
     *
     * @param pagination the pagination
     */
    public PaginationIterator(Pagination pagination) {
        this.pagination = pagination;

        paginationDisplayed = pagination.isDisplayed();
        if (paginationDisplayed) {
            pagination.goTo(1, 1);
        }
        initialState = true;
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        if (initialState) {
            return true;
        }
        if (!paginationDisplayed) {
            return false;
        }
        return !pagination.getNext().getProperty("class").contains("_disabled");
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {

        if (initialState) {
            // in initial state, pagination is on the 1st page 
            initialState = false;

            if (!paginationDisplayed) {
                return 1;
            } else {
                return pagination.getCurrentPage();
            }
        }
        pagination.getNext().click();
        return pagination.getCurrentPage();
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
