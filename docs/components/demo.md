# leistd-ddd-demo

示例项目，基于 LeiStd DDD 分层架构实现的权限管理系统，展示框架的完整使用方式。

## 项目结构

```
leistd-ddd-demo/
├── permission-domain/              # 领域层
│   └── com.mysoft.permission
│       ├── user/
│       │   ├── entity/User.java              # 用户实体
│       │   ├── domainservice/                 # 领域服务
│       │   ├── repository/                    # 仓储接口
│       │   ├── event/UserCreatedEvent.java   # 领域事件
│       │   └── valueobject/                   # 值对象
│       ├── role/
│       │   ├── entity/EnterpriseRole.java    # 角色实体
│       │   ├── domainservice/                 # 领域服务
│       │   └── specifications/                # 规约
│       └── permission/
│           └── entity/Permission.java        # 权限实体
│
├── permission-app/                 # 应用层
│   └── com.mysoft.permission
│       ├── user/
│       │   ├── appservice/                   # 应用服务
│       │   ├── dto/                          # DTO
│       │   ├── convert/                      # DTO 转换
│       │   └── listener/                     # 事件监听
│       ├── role/
│       │   ├── appservice/
│       │   ├── dto/
│       │   └── convert/
│       └── permission/
│           ├── appservice/
│           ├── dto/
│           └── convert/
│
├── permission-adapter/             # 适配层
│   └── com.mysoft.permission
│       └── controller/
│           ├── user/                         # 用户 Controller
│           ├── role/                         # 角色 Controller
│           ├── permission/                   # 权限 Controller
│           └── common/BaseController.java    # 基础 Controller
│
├── permission-infrastructure/      # 基础设施层
│   └── com.mysoft.permission
│       ├── mybatis/
│       │   ├── mapper/                       # MyBatis Mapper
│       │   └── service/                      # MyBatis Service
│       └── repository/                       # 仓储实现
│
└── permission-start/               # 启动模块
    └── com.mysoft.permission
        └── PermissionApp.java               # 启动类
```

## 各层示例

### 领域层 - 实体定义

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "user")
public class User extends ModificationAuditedHasKeyEntity<String> {

    @TableId(type = IdType.INPUT)
    private String id;

    @Override
    protected void setId(String id) {
        this.id = id;
        super.setId(id);
    }

    private String username;
    private String name;
    private String email;
    private String phone;
    private Boolean isOuter;
}
```

### 领域层 - 领域事件

```java
public class UserCreatedEvent {
    private final String userId;
    private final String username;

    public UserCreatedEvent(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
```

### 应用层 - 应用服务

```java
@Service
@RequiredArgsConstructor
public class EnterpriseUserAppServiceImpl implements EnterpriseUserAppService {

    private final EnterpriseUserDomainService enterpriseUserDomainService;

    @Override
    public EnterpriseUserRoleResDTO createEnterpriseUser(CreateEnterpriseUserReqDTO reqDTO) {
        User user = enterpriseUserDomainService.createEnterpriseUser(
            reqDTO.getUsername(), reqDTO.getName(), reqDTO.getEmail()
        );
        return EnterpriseUserRoleConvert.INSTANCE.toResDTO(user);
    }
}
```

### 应用层 - 事件监听

```java
@Component
@RequiredArgsConstructor
public class UserChangedListener {

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        // 处理用户创建事件，如发送邮件等
    }
}
```

### 适配层 - Controller

```java
@RestController
@RequestMapping("/api/enterprise-users")
@ResponseWrapper
@RequiredArgsConstructor
public class EnterpriseUserController {

    private final EnterpriseUserAppService enterpriseUserAppService;

    @PostMapping
    public EnterpriseUserRoleResDTO create(@RequestBody @Validated CreateEnterpriseUserReqDTO reqDTO) {
        return enterpriseUserAppService.createEnterpriseUser(reqDTO);
    }

    @GetMapping
    public PageResDTO<EnterpriseUserRoleResDTO> page(EnterpriseUserPageReqDTO reqDTO) {
        return enterpriseUserAppService.getEnterpriseUsers(reqDTO);
    }
}
```

### 基础设施层 - 仓储实现

```java
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl extends MybatisHasKeyRepositoryImpl<UserMapper, User, String>
        implements HasKeyRepository<User, String> {
}
```

## 数据库初始化

SQL 脚本位于 `leistd-ddd-demo/docs/sql/` 目录，包含权限服务的表结构和初始数据。
