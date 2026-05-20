package com.mysoft.leistd.security.httpclient;

import com.mysoft.leistd.context.ApplicationContextHelper;
import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.leistd.security.CurrentUserHeaderProps;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserHeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 获取当前用户信息
        CurrentUser currentUser = ApplicationContextHelper.getRequiredBean(CurrentUser.class);
        CurrentUserHeaderProps currentUserHeaderProps = ApplicationContextHelper.getRequiredBean(CurrentUserHeaderProps.class);
        if (currentUser.isAuthenticated()) {
            setHeaderValue(request, currentUserHeaderProps.getUserId(), currentUser.getId());
            setHeaderValue(request, currentUserHeaderProps.getUsername(), currentUser.getUsername());
            setHeaderValue(request, currentUserHeaderProps.getName(), currentUser.getName());
            setHeaderValue(request, currentUserHeaderProps.getPhoneNumber(), currentUser.getPhoneNumber());
            setHeaderValue(request, currentUserHeaderProps.getEmail(), currentUser.getEmail());
        }
        return execution.execute(request, body);
    }

    @SneakyThrows
    private void setHeaderValue(HttpRequest request, String headerName, String headerValue) {
        if (StringUtils.isBlank(headerValue)) {
            return;
        }
        String value = URLEncoder.encode(headerValue, StandardCharsets.UTF_8.name());
        request.getHeaders().set(headerName, value);
    }
}
