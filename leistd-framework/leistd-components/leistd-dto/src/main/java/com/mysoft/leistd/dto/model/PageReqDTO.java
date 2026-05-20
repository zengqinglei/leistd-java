package com.mysoft.leistd.dto.model;

public abstract class PageReqDTO extends ReqDTO {
    /**
     * 默认页大小：10
     */
    protected static final int DEFAULT_PAGE_SIZE = 10;

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

    public int getPageIndex() {
        if (pageIndex < 1) {
            return 0;
        }
        return pageIndex;
    }

    public PageReqDTO setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public PageReqDTO setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public String getOrder() {
        return order;
    }

    public PageReqDTO setOrder(String order) {
        this.order = order;
        return this;
    }
}
