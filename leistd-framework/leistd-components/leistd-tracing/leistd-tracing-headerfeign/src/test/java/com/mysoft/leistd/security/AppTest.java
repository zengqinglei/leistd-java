package com.mysoft.leistd.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.mysoft"})
@EnableFeignClients
public class AppTest {

}
