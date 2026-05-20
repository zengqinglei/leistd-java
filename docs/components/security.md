# leistd-security

认证与用户上下文模块，提供当前用户信息获取、JWT 认证集成和跨服务用户信息传播。

## Maven 坐标

```xml
<!-- 核心用户上下文接口 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-security-core</artifactId>
</dependency>

<!-- MVC 请求头读取用户信息 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-security-mvc</artifactId>
</dependency>

<!-- Spring Security + OAuth2 集成 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-security-mysoft</artifactId>
</dependency>

<!-- Feign 调用传播用户头 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-security-headerfeign</artifactId>
</dependency>
```

## 核心接口

### CurrentUser

获取当前登录用户信息：

```java
@Service
@RequiredArgsConstructor
public class MyService {
    final CurrentUser currentUser;

    public void foo() {
        String userId = currentUser.getId();
        String username = currentUser.getUsername();
        String email = currentUser.getEmail();
        String phone = currentUser.getPhone();
        boolean authenticated = currentUser.isAuthenticated();
    }
}
```

### CurrentClient

获取当前客户端（应用）信息：

```java
@Service
@RequiredArgsConstructor
public class MyService {
    final CurrentClient currentClient;

    public void foo() {
        String clientId = currentClient.getId();
        boolean authenticated = currentClient.isAuthenticated();
    }
}
```

### @SetCurrentUser

在后台任务、消息消费等非 HTTP 请求场景中手动设置用户上下文：

```java
@Component
@RequiredArgsConstructor
public class JobDemoService {
    final CurrentUser currentUser;

    @SetCurrentUser(username = "#{user.username}")
    public void execute(String userId, User user) {
        // 方法执行期间，currentUser 可正常获取用户信息
        String id = currentUser.getId();
        String name = currentUser.getUsername();
    }
}
```

支持 SpEL 表达式，从方法参数中提取用户信息。

## MVC 请求头模式

引入 `leistd-security-mvc`，从 HTTP 请求头读取用户信息，适用于网关转发场景。

### 配置

```yaml
leistd:
  current-user-header:
    enable: true
    user-id: X-User-Id
    username: X-Username
```

### 工作流程

1. `CurrentUserHeaderFilter` 从请求头提取用户信息
2. 封装为 `ClaimsPrincipal` 存入 `CurrentPrincipalAccessor`
3. `CurrentUser` 从 `CurrentPrincipalAccessor` 读取

### RestTemplate 传播

`UserHeaderRequestInterceptor` 自动将用户头添加到发出的 RestTemplate 请求中。

## Spring Security 集成

引入 `leistd-security-mysoft`，集成 Spring Security OAuth2 Resource Server。

### 自动配置

- `CustomSecurityConfig` - 配置 OAuth2 资源服务器
- `HttpContextCurrentPrincipalAccessor` - 从 SecurityContext 提取用户信息
- `CustomSetUserFilter` - 将安全上下文写入 MDC 日志
- `CustomAuthenticationEntryPoint` - 认证失败返回标准响应
- `CustomAuthenticationException` - 处理认证和访问拒绝异常

## Feign 用户头传播

引入 `leistd-security-headerfeign`，Feign 调用时自动传播用户信息头。

```java
@FeignClient(name = "other-service")
public interface OtherServiceClient {
    @GetMapping("/api/data")
    String getData();  // 请求头自动携带 X-User-Id、X-Username 等
}
```

`FeignUserHeaderInterceptor` 从当前 `CurrentPrincipalAccessor` 读取用户信息并添加到 Feign 请求头。

## 架构说明

```
请求 → SecurityFilter / CurrentUserHeaderFilter
              ↓
    CurrentPrincipalAccessor（线程级存储）
              ↓
    CurrentUser / CurrentClient（只读接口）
              ↓
    FeignUserHeaderInterceptor / UserHeaderRequestInterceptor（向下游传播）
```

`CurrentPrincipalAccessor` 是用户信息的存取核心：
- HTTP 请求场景：由 Security Filter 或 Header Filter 自动填充
- 非 HTTP 场景：由 `@SetCurrentUser` 切面填充
- 默认实现：`DefaultCurrentPrincipalAccessorImpl`（无操作，适用于无需认证的场景）
