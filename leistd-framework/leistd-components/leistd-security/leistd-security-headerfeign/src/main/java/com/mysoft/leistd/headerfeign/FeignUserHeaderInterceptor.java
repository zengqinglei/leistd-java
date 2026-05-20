package com.mysoft.leistd.headerfeign;

import com.mysoft.leistd.context.ApplicationContextHelper;
import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.leistd.security.CurrentUserHeaderProps;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignUserHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前用户信息
        CurrentUser currentUser = ApplicationContextHelper.getRequiredBean(CurrentUser.class);
        CurrentUserHeaderProps currentUserHeaderProps = ApplicationContextHelper.getRequiredBean(CurrentUserHeaderProps.class);
        if (currentUser.isAuthenticated()) {
            setHeaderValue(requestTemplate, currentUserHeaderProps.getUserId(), currentUser.getId());
            setHeaderValue(requestTemplate, currentUserHeaderProps.getUsername(), currentUser.getUsername());
            setHeaderValue(requestTemplate, currentUserHeaderProps.getName(), currentUser.getName());
            setHeaderValue(requestTemplate, currentUserHeaderProps.getPhoneNumber(), currentUser.getPhoneNumber());
            setHeaderValue(requestTemplate, currentUserHeaderProps.getEmail(), currentUser.getEmail());
        }
    }

    private void setHeaderValue(RequestTemplate requestTemplate, String headerName, String headerValue) {
        if (StringUtils.isBlank(headerValue)) {
            return;
        }
        String value = URLEncoder.encode(headerValue, StandardCharsets.UTF_8);
        requestTemplate.header(headerName, value);
    }
}
