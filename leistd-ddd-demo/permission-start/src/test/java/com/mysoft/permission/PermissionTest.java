package com.mysoft.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.permission.common.properties.IdentityServerProps;
import com.mysoft.permission.common.properties.PermissionEmailProps;
import com.mysoft.permission.common.utils.AuthUtils;
import com.mysoft.permission.permission.entity.Permission;
import com.mysoft.permission.repository.UserRepositoryImpl;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.valueobject.RolePermission;
import com.mysoft.permission.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionTest {

    @Resource
    UserRepositoryImpl userRepository;
    @Resource
    PermissionEmailProps permissionEmailProps;
    @Resource
    IdentityServerProps identityServerProps;

    @Autowired
    private HasKeyRepository<Permission, Long> permissionRepository;

    @Autowired
    private HasKeyRepository<EnterpriseRole, Long> enterpriseRoleRepository;

    @Autowired
    private HasKeyRepository<RolePermission, Long> rolePermissionRepository;


    @Test
    public void testSendInitPasswordEmail() {
        User user = userRepository.getById("067af8c4-b0bc-473a-9b1a-310c4c20b1d7");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", user.getName());
        paramMap.put("password", AuthUtils.decryptDES(identityServerProps.getDesKey(), user.getPassword()));
        paramMap.put("username", user.getUsername());
        paramMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //emailClient.sendTemplateMail(user.getEmail(), permissionEmailProps.getPasswordSubject(), permissionEmailProps.getInitPasswordEmailTemp(), paramMap);
    }

    @Test
    public void testDecryptUserPassword() {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<User>()
                .in(User::getUsername, Arrays.asList("rdctyyh006", "rdctyyh005", "rdctyyh004", "rdctyyh003", "rdctyyh002", "rdctyyh001"));
        List<User> users = userRepository.list(userQuery);
        for (User user : users) {
            String password = AuthUtils.decryptDES(identityServerProps.getDesKey(), user.getPassword());
            System.out.println(MessageFormat.format("用户【{0}】密码为：{1}", user.getUsername(), password));
        }

    }

    /**
     * 初始化角色权限点
     */
    @Test
    public void initRolePermission() {
        List<String> permissionStrList = new ArrayList<>();
        permissionStrList.add("TenantManage_Public_ManualAdjustment");
        permissionStrList.add("TenantManage_Provide_ManualAdjustment");
        permissionStrList.add("TenantManage_Public_EditAuth");
        permissionStrList.add("TenantManage_Provide_EditAuth");
        permissionStrList.add("TenantManage_Public_DeliveryEdit");
        permissionStrList.add("TenantManage_Provide_DeliveryEdit");

        List<Permission> permissionList = permissionRepository.list(new LambdaQueryWrapper<Permission>()
                .in(Permission::getCode, permissionStrList)
        );

        List<EnterpriseRole> enterpriseRoleList = enterpriseRoleRepository.list();

    }

}
