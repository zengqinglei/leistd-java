package com.mysoft.leistd.logging;

import com.mysoft.leistd.mdc.MdcProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 3)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final RequestLoggingProps requestLoggingProps;
    private final MdcProvider mdcProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 确定是否开启日志记录
        if (!requestLoggingProps.isEnabled(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // log request
        logRequestProperties(request);
        logRequestHeader(request);

        HttpServletRequest currentRequest = request;
        HttpServletResponse currentResponse = response;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            if (requestLoggingProps.isLogRequestBody(request.getRequestURI())) {
                // 包装HttpServletRequest，把输入流缓存下来
                currentRequest = new ContentCachingRequestWrapper(request, requestLoggingProps.getRequestBodyLimit());
            }
            if (requestLoggingProps.isLogResponseBody(request.getRequestURI())) {
                // 包装HttpServletResponse，把输出流缓存下来
                currentResponse = new CustomContentCachingResponseWrapper(response, requestLoggingProps.getResponseBodyLimit());
            }
            filterChain.doFilter(currentRequest, currentResponse);
        } finally {
            stopWatch.stop();

            // log response
            int statusCode = response.getStatus();
            long elapsedTime = stopWatch.getTotalTimeMillis();
            logResponseProperties(statusCode, elapsedTime);
            if (requestLoggingProps.isLogRequestBody(request.getRequestURI())) {
                ContentCachingRequestWrapper wrappedRequest = (ContentCachingRequestWrapper) currentRequest;
                logRequestBody(request, wrappedRequest);
            }
            if (requestLoggingProps.isLogResponseBody(request.getRequestURI())) {
                CustomContentCachingResponseWrapper wrappedResponse = (CustomContentCachingResponseWrapper) currentResponse;
                logResponseBody(wrappedResponse);
                // 注意这一行代码一定要调用，不然无法返回响应体
                wrappedResponse.copyBodyToResponse(false);
            }

            // print http logging message
            String message = MessageFormat.format("{0} {1} responded {2} in {3} ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    statusCode,
                    String.valueOf(elapsedTime));
            if (statusCode >= 500) {
                log.error(message);
            } else if (statusCode >= 300) {
                log.warn(message);
            } else {
                log.info(message);
            }
        }
    }

    private void logRequestProperties(HttpServletRequest request) {
        if (!requestLoggingProps.getLoggingFields().contains(RequestLoggingField.REQUEST_PROPERTIES)) {
            return;
        }
        mdcProvider.add(RequestLoggingLogConst.IP_ADDRESS, request.getRemoteAddr());
        mdcProvider.add(RequestLoggingLogConst.METHOD, request.getMethod());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_SCHEME, request.getScheme());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_SERVER_NAME, request.getServerName());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_SERVER_PORT, String.valueOf(request.getServerPort()));
        mdcProvider.add(RequestLoggingLogConst.REQUEST_CONTEXT_PATH, request.getContextPath());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_URI, request.getRequestURI());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_QUERY_STRING, request.getQueryString());
        mdcProvider.add(RequestLoggingLogConst.REQUEST_URL, getFullRequestUrl(request));
    }

    private String getFullRequestUrl(HttpServletRequest request) {
        StringBuffer urlBuffer = request.getRequestURL();
        // 添加查询参数
        String queryString = request.getQueryString();
        if (queryString != null) {
            urlBuffer.append("?").append(queryString);
        }
        return urlBuffer.toString();
    }

    private void logRequestHeader(HttpServletRequest request) {
        if (!requestLoggingProps.getLoggingFields().contains(RequestLoggingField.REQUEST_HEADERS)) {
            return;
        }
        Set<String> excludeHeaders = requestLoggingProps.getExcludeHeaders();
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (excludeHeaders != null && excludeHeaders.stream().anyMatch(excludeHeader -> excludeHeader.equalsIgnoreCase(headerName))) {
                continue;
            }
            String headerValue = request.getHeader(headerName);
            requestHeaders.add(headerName, headerValue);
        }
        mdcProvider.add(RequestLoggingLogConst.REQUEST_HEADER, requestHeaders.toString());
    }

    private void logRequestBody(HttpServletRequest request, ContentCachingRequestWrapper wrappedRequest) {
        String requestBody = "- -";
        if (isAllowReadRequestBodyByMethod(request.getMethod()) && isAllowReadBodyByContentType(request.getContentType())) {
            requestBody = new String(wrappedRequest.getContentAsByteArray());
        }
        mdcProvider.add(RequestLoggingLogConst.REQUEST_BODY, requestBody);
    }

    private void logResponseProperties(int statusCode, long elapsedTime) {
        mdcProvider.add(RequestLoggingLogConst.STATUS_CODE, String.valueOf(statusCode));
        mdcProvider.add(RequestLoggingLogConst.ELAPSED, String.valueOf(elapsedTime));
    }

    private void logResponseBody(CustomContentCachingResponseWrapper wrappedResponse) {
        String responseBody = "- -";
        if (isAllowReadBodyByContentType(wrappedResponse.getContentType())) {
            responseBody = new String(wrappedResponse.getContentAsByteArray());
        }
        mdcProvider.add(RequestLoggingLogConst.RESPONSE_BODY, responseBody);
    }

    private boolean isAllowReadRequestBodyByMethod(String httpMethod) {
        if (StringUtils.isBlank(httpMethod)) {
            return false;
        }
        List<String> allowMethods = Arrays.asList(
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name());
        return allowMethods.stream().anyMatch(allowMethod -> allowMethod.equalsIgnoreCase(httpMethod));
    }

    private boolean isAllowReadBodyByContentType(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return false;
        }
        List<MediaType> allowContentTypes = Arrays.asList(
                MediaType.TEXT_PLAIN,
                MediaType.APPLICATION_XML,
                MediaType.APPLICATION_JSON);
        try {
            MediaType currentContentType = MediaType.parseMediaType(contentType);
            if (allowContentTypes.stream().anyMatch(allowContentType -> allowContentType.equalsTypeAndSubtype(currentContentType))) {
                return true;
            }
        } catch (Exception ex) {
            String error = MessageFormat.format(
                    "MediaType【{0}】转换失败：{1}",
                    contentType,
                    ex.getMessage());
            log.warn(error, ex);
        }
        return false;
    }
}
