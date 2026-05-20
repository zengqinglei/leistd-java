# DDD 分层指南

LeiStd 采用领域驱动设计的四层架构，将业务逻辑与技术实现分离。本文档说明各层的职责、编码规范和协作方式。

## 四层结构

```
┌──────────────────────────────────────────────────┐
│  Adapter（适配层）- 接收外部输入                     │
│  Controller / EventListener / JobHandler          │
├──────────────────────────────────────────────────┤
│  App（应用层）- 编排与协调                           │
│  AppService / DTO / Convert                       │
├──────────────────────────────────────────────────┤
│  Domain（领域层）- 核心业务逻辑                      │
│  Entity / ValueObject / DomainService / Repository│
├──────────────────────────────────────────────────┤
│  Infrastructure（基础设施层）- 技术实现              │
│  RepositoryImpl / Mapper / RedisClient            │
└──────────────────────────────────────────────────┘
```

## Domain（领域层）

领域层是系统的核心，包含业务实体、值对象、领域服务和仓储接口。

### 实体基类

LeiStd 提供了分层递进的实体基类，按需继承：

| 基类 | 字段 | 适用场景 |
|------|------|---------|
| `Entity` | 无额外字段 | 最基础实体，支持本地事件 |
| `HasKeyEntity<K>` | id | 需要主键的实体 |
| `CreationAuditedEntity` | creatorId, creatorName, createdTime | 需要创建审计 |
| `CreationAuditedHasKeyEntity<K>` | id + 创建审计 | 最常用的带主键实体 |
| `ModificationAuditedEntity` | 创建审计 + lastModifierId, lastModifierName, lastModifiedTime | 需要修改审计 |
| `ModificationAuditedHasKeyEntity<K>` | id + 创建/修改审计 | 完整审计实体 |
| `DeletionAuditedEntity` | 创建/修改审计 + deleterId, deleterName, deletionTime, isDeleted | 需要软删除 |
| `DeletionAuditedHasKeyEntity<K>` | id + 全部审计 + 软删除 | 最完整实体 |

#### 示例：定义用户实体

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "user")
public class User extends CreationAuditedHasKeyEntity<String> {

    @TableId(type = IdType.INPUT)
    private String id;

    @Override
    protected void setId(String id) {
        this.id = id;
        super.setId(id);
    }

    private String name;
    private String email;

    // 业务方法
    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }
}
```

### 仓储接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `Repository<T>` | insert, delete, update, getList, getPage | 基础 CRUD |
| `HasKeyRepository<T, K>` | getById, deleteById, updateById | 按主键操作 |

在 Domain 层只定义接口：

```java
public interface UserRepository extends HasKeyRepository<User, String> {
    User getByUsername(String username);
}
```

### 规约（Specification）

用于封装查询条件：

```java
public class ActiveUserSpecification implements Specification<User> {
    @Override
    public Wrapper<User> toWrapper() {
        return Wrappers.<User>lambdaQuery()
                .eq(User::getIsActive, true);
    }
}
```

### 领域事件

实体继承 `Entity` 即可发布本地事件：

```java
public class User extends CreationAuditedHasKeyEntity<String> {

    public void activate() {
        // 业务逻辑...
        registerEvent(new UserActivatedEvent(this.id));
    }
}
```

事件在 Infrastructure 层的 `EntityLocalEventInterceptor` 中自动发布到 Spring ApplicationContext。

## App（应用层）

应用层负责编排领域服务、DTO 转换和事务控制。

### 应用服务

```java
@Service
@RequiredArgsConstructor
public class UserAppService {

    private final UserRepository userRepository;

    @Transactional
    public UserResDTO createUser(CreateUserReqDTO reqDTO) {
        User user = new User();
        user.setName(reqDTO.getName());
        user.setEmail(reqDTO.getEmail());
        userRepository.insert(user);
        return UserConvert.INSTANCE.toResDTO(user);
    }

    public PageResDTO<UserResDTO> getUsers(UserPageReqDTO reqDTO) {
        PageResult<User> result = userRepository.getPage(
            reqDTO.toPageQuery(),
            new ActiveUserSpecification()
        );
        return new PageResDTO<>(
            result.getTotalCount(),
            UserConvert.INSTANCE.toResDTOList(result.getItems())
        );
    }
}
```

### DTO 规范

| 基类 | 用途 |
|------|------|
| `ReqDTO` | 请求输入基类 |
| `ResDTO` | 响应输出基类 |
| `PageReqDTO` | 分页请求（含 pageIndex、pageSize、order） |
| `PageResDTO<T>` | 分页响应（含 totalCount、items） |

### DTO 转换

实现 `BaseReqDTOConvert` 或 `BaseResDTOConvert` 接口：

```java
@Component
public class UserConvert implements BaseResDTOConvert<User, UserResDTO> {

    @Override
    public UserResDTO toResDTO(User source) {
        UserResDTO dto = new UserResDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setEmail(source.getEmail());
        return dto;
    }

    @Override
    public List<UserResDTO> toResDTOList(List<User> sources) {
        return sources.stream().map(this::toResDTO).collect(Collectors.toList());
    }
}
```

## Adapter（适配层）

适配层接收外部请求，做参数校验后调用应用服务。

```java
@RestController
@RequestMapping("/api/users")
@ResponseWrapper
@RequiredArgsConstructor
public class UserController {

    private final UserAppService userAppService;

    @PostMapping
    public UserResDTO create(@RequestBody @Validated CreateUserReqDTO reqDTO) {
        return userAppService.createUser(reqDTO);
    }

    @GetMapping
    public PageResDTO<UserResDTO> list(UserPageReqDTO reqDTO) {
        return userAppService.getUsers(reqDTO);
    }

    @GetMapping("/{id}")
    public UserResDTO get(@PathVariable String id) {
        return userAppService.getUser(id);
    }
}
```

注意：
- Controller 不包含业务逻辑
- 使用 `@ResponseWrapper` 自动包装统一响应
- 异常由 `leistd-exception-mvc` 全局处理

## Infrastructure（基础设施层）

实现 Domain 层定义的仓储接口，封装技术细节。

### MyBatis-Plus 仓储实现

继承 `MybatisHasKeyRepositoryImpl` 可获得通用 CRUD 实现：

```java
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl extends MybatisHasKeyRepositoryImpl<UserMapper, User, String>
        implements UserRepository {

    @Override
    public User getByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).one();
    }
}
```

### 审计字段自动填充

引入 `leistd-ddd-mybatis` 后，以下字段自动填充，无需手动赋值：

| 操作 | 填充字段 |
|------|---------|
| 新增 | creatorId, creatorName, createdTime |
| 修改 | lastModifierId, lastModifierName, lastModifiedTime |
| 删除 | deleterId, deleterName, deletionTime, isDeleted |

前提：需要配置 `CurrentUser` 以获取当前用户信息。

## 分层协作流程

以"创建用户"为例，展示完整的调用链：

```
1. Adapter
   UserController.create(CreateUserReqDTO)
   │── 参数校验（@Validated）
   └── 调用 AppService

2. App
   UserAppService.createUser(CreateUserReqDTO)
   │── DTO → Entity 转换
   │── 调用 Repository
   └── 返回 ResDTO

3. Domain
   User（实体，业务规则）
   UserRepository.insert(User)

4. Infrastructure
   UserRepositoryImpl.insert(User)
   └── MyBatis-Plus → 数据库
       └── CustomMetaObjectHandler 自动填充审计字段
       └── EntityLocalEventInterceptor 发布领域事件
```
