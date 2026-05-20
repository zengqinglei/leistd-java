package com.mysoft.leistd.domain.entities.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PageResult<T> {
    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 数据集合
     */
    private Collection<T> items;

    public PageResult(long totalCount, Collection<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<T> getItems() {
        if (null == items) {
            return Collections.emptyList();
        }
        if (items instanceof List) {
            return (List<T>) items;
        }
        return new ArrayList<T>(items);
    }
}
