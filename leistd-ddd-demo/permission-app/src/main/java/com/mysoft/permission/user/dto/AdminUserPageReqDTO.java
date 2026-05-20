package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.PageReqDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserPageReqDTO extends PageReqDTO {

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 关键词
     */
    private String keyword;

}
