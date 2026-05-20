# leistd-mdc

MDC（Mapped Diagnostic Context）日志上下文管理模块，提供线程级别的日志变量管理和自动清理。

## Maven 坐标

```xml
<!-- MDC Provider -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-mdc-core</artifactId>
</dependency>

<!-- MVC Filter（请求结束自动清理 MDC） -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-mdc-mvc</artifactId>
</dependency>
```

## 核心类

### MdcProvider

线程级 MDC 上下文管理器，跟踪添加的 Key 并支持批量清理。

```java
@Service
@RequiredArgsConstructor
public class MyService {
    final MdcProvider mdcProvider;

    public void process() {
        // 添加 MDC 变量
        mdcProvider.put("userId", "12345");
        mdcProvider.put("action", "createOrder");

        // 日志自动包含这些变量
        log.info("处理订单");  // MDC 中有 userId 和 action

        // 清理当前添加的所有变量
        mdcProvider.clear();
    }
}
```

### CoreFilter

Servlet Filter，在每个 HTTP 请求结束后自动清理 MDC，防止线程池复用导致的 MDC 残留。

引入 `leistd-mdc-mvc` 后自动注册，无需手动配置。

## 与其他组件协作

MdcProvider 是链路追踪、安全等组件的基础设施：

| 组件 | 写入的 MDC Key |
|------|---------------|
| leistd-tracing | `correlationId` |
| leistd-security | `userId`、`username` |
| leistd-logging | `reqUri`、`reqMethod`、`reqBody` 等 |

CoreFilter 确保这些 Key 在请求结束后被正确清理。

## 日志格式配置

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d [%X{correlationId}] [%X{userId}] %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>
</configuration>
```
