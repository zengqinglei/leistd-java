package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.ReqDTO;
import com.mysoft.permission.user.constants.RegexStringConst;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateAdminUserReqDTO extends ReqDTO {

    @NotNull(message = "用户Id不能为空")
    @Pattern(regexp = RegexStringConst.USER_ID, message = "客户Id不符合规范")
    private String userId;

    @NotNull(message = "用户姓名不能为空")
    @Size(max = 64, message = "用户姓名不能超过64位")
    private String name;

    @NotNull(message = "用户名不能为空")
    @Size(max = 64, message = "用户名不能超过64位")
    private String username;

    @NotNull(message = "邮箱地址不能为空")
    @Email(message = "请填写正确的邮箱地址")
    private String email;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "请填写11位手机号")
    private String phone;

    @NotNull(message = "是否外部用户不能为空")
    private Boolean isOuter;

}
