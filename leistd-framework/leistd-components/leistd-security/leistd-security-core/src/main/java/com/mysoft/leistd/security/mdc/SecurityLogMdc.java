package com.mysoft.leistd.security.mdc;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.CurrentClient;
import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.leistd.security.constant.SecurityLogConst;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 记录认证信息到Mdc
 */
@Component
@RequiredArgsConstructor
public final class SecurityLogMdc {
    final CurrentClient currentClient;
    final CurrentUser currentUser;

    /**
     * 添加客户端信息
     */
    public void addClient(MdcProvider mdcProvider) {
        if (!currentClient.isAuthenticated()) {
            return;
        }
        mdcProvider.add(SecurityLogConst.CLIENT_ID, currentClient.getClientId());
        mdcProvider.add(SecurityLogConst.SCOPE, StringUtils.join(currentClient.getScope(), ','));
    }

    /**
     * 添加用户信息
     */
    public void addUser(MdcProvider mdcProvider) {
        if (!currentUser.isAuthenticated()) {
            return;
        }
        mdcProvider.add(SecurityLogConst.USER_ID, currentUser.getId());
        mdcProvider.add(SecurityLogConst.USERNAME, currentUser.getUsername());
        mdcProvider.add(SecurityLogConst.NAME, currentUser.getName());
        mdcProvider.add(SecurityLogConst.PHONE_NUMBER, currentUser.getPhoneNumber());
        mdcProvider.add(SecurityLogConst.EMAIL, currentUser.getEmail());
    }
}
