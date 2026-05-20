package com.mysoft.leistd.dto.convert;

import com.mysoft.leistd.dto.model.ReqDTO;

import java.util.List;

/**
 * 输入转源对象基类
 *
 * @param <TSource> 源对象
 * @param <TReqDTO> 目标对象（继承自ReqDTO）
 */
public interface BaseReqDTOConvert<TSource, TReqDTO extends ReqDTO> {
    TSource fromReqDTO(TReqDTO reqDTO);

    List<TSource> fromReqDTOList(List<TReqDTO> reqDTOList);
}
