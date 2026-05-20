package com.mysoft.leistd.dto.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PageResDTO<T extends ResDTO> {

    /**
     * 总条数
     */
    @Getter
    private final long totalCount;

    /**
     * 数据集合
     */
    private final Collection<T> items;

    public PageResDTO(long totalCount, Collection<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    public List<T> getItems() {
        if (null == items) {
            return Collections.emptyList();
        }
        if (items instanceof List) {
            return (List<T>) items;
        }
        return new ArrayList<>(items);
    }
}
