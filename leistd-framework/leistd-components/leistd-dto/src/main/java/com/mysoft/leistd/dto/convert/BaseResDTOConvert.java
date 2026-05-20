package com.mysoft.leistd.dto.convert;

import com.mysoft.leistd.dto.model.ResDTO;

import java.util.List;

/**
 * 源对象转输出对象基类
 *
 * @param <TSource> 源对象
 * @param <TResDTO> 目标对象（继承自ResDTO）
 */
public interface BaseResDTOConvert<TSource, TResDTO extends ResDTO> {
    TResDTO toResDTO(TSource source);

    List<TResDTO> toResDTOList(List<TSource> sources);
}
