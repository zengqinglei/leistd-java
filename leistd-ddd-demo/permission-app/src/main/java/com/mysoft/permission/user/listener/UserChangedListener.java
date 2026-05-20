package com.mysoft.permission.user.listener;

import com.mysoft.emailclient.EmailClient;
import com.mysoft.leistd.tracing.interceptor.CorrelationId;
import com.mysoft.permission.common.properties.IdentityServerProps;
import com.mysoft.permission.common.properties.PermissionEmailProps;
import com.mysoft.permission.common.utils.AuthUtils;
import com.mysoft.permission.user.appservice.EnterpriseUserAppService;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserChangedListener {
    final EnterpriseUserAppService enterpriseUserAppService;
    final PermissionEmailProps permissionEmailProps;
    final IdentityServerProps identityServerProps;
    final EmailClient emailClient;

    @Async
    @TransactionalEventListener
    @CorrelationId
    public void handleEvent(UserCreatedEvent userCreatedEvent) {
        User user = userCreatedEvent.getSource();
        if (!user.getIsOuter() || StringUtils.isBlank(user.getPassword())) {
            return;
        }
        log.info("开始发送外部用户【{}】创建成功邮件【{}】...", user.getUsername(), user.getEmail());
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("name", user.getName());
            paramMap.put("password", AuthUtils.decryptDES(identityServerProps.getDesKey(), user.getPassword()));
            paramMap.put("username", user.getUsername());
            paramMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            emailClient.sendTemplateMail(
                    user.getEmail(),
                    permissionEmailProps.getPasswordSubject(),
                    permissionEmailProps.getInitPasswordEmailTemp(),
                    paramMap);
            log.info("发送外部用户【{}】创建成功邮件【{}】完成", user.getUsername(), user.getEmail());
        } catch (Exception e) {
            String error = MessageFormat.format(
                    "发送外部用户【{0}】创建成功邮件【{1}】失败：{2}",
                    user.getUsername(),
                    user.getEmail(),
                    e.getMessage());
            log.error(error, e);
        }
    }
}
