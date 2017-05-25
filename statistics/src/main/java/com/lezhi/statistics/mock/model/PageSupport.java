package com.lezhi.statistics.mock.model;

/**
 * Created by Colin Yan on 2017/5/25.
 */
public abstract class PageSupport {

    private int pageNo;
    private int pageSize;
    private int realPageSize;
    private int totalPageCount;
    private int totalCount;
    private boolean isFirstPage;
    private boolean isLastPage;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRealPageSize() {
        return realPageSize;
    }

    public void setRealPageSize(int realPageSize) {
        this.realPageSize = realPageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
