package com.mysoft.leistd.domain.entities.page;

public class PageQuery {
    /**
     * 默认页大小：10
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 页码
     */
    private int pageIndex = 1;

    /**
     * 排序（示例：created_time desc）
     */
    private String order;

    public PageQuery(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        if (pageIndex < 1) {
            return 0;
        }
        return pageIndex;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public String getOrder() {
        return order;
    }

    public PageQuery setOrder(String order) {
        this.order = order;
        return this;
    }
}
