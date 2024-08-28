package com.ericsson.cds.uisdk.compositecomponents.pagination;

import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.sdk.Link;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PaginationUnitTest {

    private Pagination pagination = new Pagination();

    @Test
    public void testNavigationPrevious() throws Exception {
        int pageNumber = 5;
        int pageStep = 3;
        int currentPage = 10;

        Pagination p = spy(pagination);
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();
        doReturn(currentPage).when(p).getCurrentPage();

        p.stepPage(pageStep, pageNumber);
        verify(previous, times(pageStep)).click();
    }

    @Test
    public void testNavigationNext() throws Exception {
        int pageNumber = 10;
        int pageStep = 3;
        int currentPage = 1;

        Pagination p = spy(pagination);
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();
        doReturn(currentPage).when(p).getCurrentPage();

        p.stepPage(pageStep, pageNumber);
        verify(next, times(pageStep)).click();
    }

    @Test
    public void testNavigationFromCorrectPage() throws Exception {
        int pageNumber = 5;
        int pageStep = 3;
        int currentPage = 5;

        Pagination p = spy(pagination);
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();
        doReturn(currentPage).when(p).getCurrentPage();

        p.stepPage(pageStep, pageNumber);
        verify(next, times(0)).click();
    }

    @Test
    public void testGoToWithOnePageCount() throws Exception {
        int pageNumber = 5;
        int pageStep = 3;
        int currentPage = 2;
        int totalPages = 1;

        Pagination p = spy(pagination);
        doReturn(currentPage).when(p).getCurrentPage();
        doReturn(totalPages).when(p).getPageCount();
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();

        p.goTo(pageNumber, pageStep);
        verify(next, times(0)).click();
        verify(previous, times(0)).click();

    }

    @Test
    public void testGoToWithPageNoGreaterThanPageCount() throws Exception {
        int pageNumber = 20;
        int pageStep = 3;
        int currentPage = 2;
        int totalPages = 10;

        Pagination p = spy(pagination);
        doReturn(currentPage).when(p).getCurrentPage();
        doReturn(totalPages).when(p).getPageCount();
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();

        p.goTo(pageNumber, pageStep);
        verify(next, times(0)).click();
        verify(previous, times(0)).click();

    }

    @Test
    public void testGoToEqualSamePage() throws Exception {
        int pageNumber = 5;
        int pageStep = 3;
        int currentPage = 5;
        int totalPages = 10;

        Pagination p = spy(pagination);
        doReturn(currentPage).when(p).getCurrentPage();
        doReturn(totalPages).when(p).getPageCount();
        UiComponent next = mock(Link.class);
        UiComponent previous = mock(Link.class);
        doReturn(previous).when(p).getPrevious();
        doReturn(next).when(p).getNext();

        p.goTo(pageNumber, pageStep);
        verify(next, times(0)).click();
        verify(previous, times(0)).click();

    }

    @Test
    public void testGoToAndFindPage() throws Exception {
        int pageNumber = 3;
        int pageStep = 3;
        int currentPage = 1;
        int totalPages = 10;

        Pagination p = spy(pagination);
        doReturn(currentPage).when(p).getCurrentPage();
        doReturn(totalPages).when(p).getPageCount();
        doNothing().when(p).stepPage(pageNumber, pageStep);

        Link link1 = mock(Link.class);
        Link link2 = mock(Link.class);
        Link link3 = mock(Link.class);
        List<Link> pageSelectors = new ArrayList();

        doReturn("1").when(link1).getText();
        doReturn("2").when(link2).getText();
        doReturn("3").when(link3).getText();
        pageSelectors.add(link1);
        pageSelectors.add(link2);
        pageSelectors.add(link3);

        doReturn(pageSelectors).when(p).getPageLinks();

        p.goTo(pageNumber, pageStep);
        verify(link3, times(1)).click();

    }

}
