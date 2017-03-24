package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/13.
 */
public class PageResult {
    private Integer pageNo; // 分页参数，当前页码

    private Integer pageSize; // 分页参数，页面分页大小

    private Integer realPageSize; // 分页参数，页面实际大小

    private Integer totalPageCount; // 分页参数，总页码

    private boolean isFirstPage; // 分页参数，是否第一页

    private boolean isLastPage;// 分页参数，是否最后页

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRealPageSize() {
        return realPageSize;
    }

    public void setRealPageSize(Integer realPageSize) {
        this.realPageSize = realPageSize;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
