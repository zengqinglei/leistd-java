package com.mysoft.permission.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "permission.mail")
public class PermissionEmailProps {

    private String passwordSubject;

    private String initPasswordEmailTemp;
}
