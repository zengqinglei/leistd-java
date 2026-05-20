package com.mysoft.leistd.security.client;

import com.mysoft.leistd.tracing.FeignCorrelationIdInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "DemoClient",
        url = "https://www.baidu.com",
        configuration = {
                FeignCorrelationIdInterceptor.class
        }
)
public interface DemoClient {
    @GetMapping
    ResponseEntity<String> get();
}
