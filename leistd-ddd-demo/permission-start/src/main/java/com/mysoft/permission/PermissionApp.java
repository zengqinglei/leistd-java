package com.mysoft.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot Starter
 */
@SpringBootApplication(scanBasePackages = {"com.mysoft"})
@EnableFeignClients(basePackages = {"com.mysoft"})
@EnableAsync
@Slf4j
public class PermissionApp
{
    public static void main( String[] args )
    {
        log.info("Begin to start Spring Boot Application");
        long startTime = System.currentTimeMillis();

        SpringApplication.run(PermissionApp.class, args);

        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
