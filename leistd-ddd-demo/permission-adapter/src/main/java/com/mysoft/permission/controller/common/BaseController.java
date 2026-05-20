package com.mysoft.permission.controller.common;

import com.mysoft.leistd.response.ResponseWrapper;
import org.springframework.web.bind.annotation.RequestMapping;

@ResponseWrapper(wrapperSuccessRes = false)
@RequestMapping("/api/v1")
//@PreAuthorize("hasAuthority('defaultApi')")
public abstract class BaseController {
}
