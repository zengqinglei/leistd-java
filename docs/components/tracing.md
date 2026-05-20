# leistd-tracing

链路追踪模块，基于 Correlation ID 实现跨服务、跨线程的请求追踪。

## Maven 坐标

```xml
<!-- 核心追踪接口 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-tracing-core</artifactId>
</dependency>

<!-- MVC Filter 集成 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-tracing-mvc</artifactId>
</dependency>

<!-- Feign 调用传播 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-tracing-headerfeign</artifactId>
</dependency>
```

## 核心概念

### Correlation ID

每个请求分配唯一标识（UUID），在整个调用链中传播，用于关联同一请求的日志。

### 传播路径

```
HTTP 请求 → CorrelationIdFilter（生成/读取 ID）
                ↓
        MDC（日志上下文）+ CorrelationIdProvider
                ↓
    Feign / RestTemplate 调用（自动添加请求头）
                ↓
        下游服务 CorrelationIdFilter（读取 ID）
```

## 使用方式

### HTTP 请求追踪

引入 `leistd-tracing-mvc` 后自动生效。每个 HTTP 请求：

1. 检查请求头 `X-Correlation-Id`，若存在则使用，否则生成新的 UUID
2. 将 Correlation ID 写入 MDC（key: `correlationId`），日志自动输出
3. 响应头中返回 `X-Correlation-Id`

### 日志输出

在日志格式中引用 MDC 变量：

```xml
<pattern>%d [%X{correlationId}] %-5level %logger - %msg%n</pattern>
```

输出示例：

```
2024-01-15 [a1b2c3d4-e5f6-7890] INFO  c.m.demo.UserService - 处理用户请求
```

### @CorrelationId 注解

在后台任务、消息消费等非 HTTP 场景中，自动传播 Correlation ID：

```java
@Component
@RequiredArgsConstructor
public class JobService {
    final CorrelationIdProvider correlationIdProvider;

    @CorrelationId
    @Scheduled(fixedRate = 60000)
    public void execute() {
        // 方法执行期间，MDC 中包含 correlationId
        String id = correlationIdProvider.get();
    }
}
```

### 配置项

```yaml
leistd:
  correlation-id:
    # 请求头名称（默认 X-Correlation-Id）
    header-name: X-Correlation-Id
```

### Feign 传播

引入 `leistd-tracing-headerfeign`，Feign 调用自动传播 Correlation ID：

```java
@FeignClient(name = "other-service")
public interface OtherServiceClient {
    @GetMapping("/api/data")
    String getData();  // 自动携带 X-Correlation-Id 请求头
}
```

### RestTemplate 传播

引入 `leistd-tracing-mvc`，注册 `CorrelationIdHeaderRequestInterceptor`：

```java
@Bean
public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(new CorrelationIdHeaderRequestInterceptor());
    return restTemplate;
}
```

## 核心类

| 类 | 说明 |
|----|------|
| `CorrelationIdProvider` | Correlation ID 存取接口 |
| `CorrelationIdProviderImpl` | UUID 实现 |
| `CorrelationIdFilter` | HTTP 请求 Filter，管理 Correlation ID 生命周期 |
| `CorrelationIdAspect` | `@CorrelationId` 注解切面 |
| `CorrelationIdLogMdc` | 将 Correlation ID 写入 MDC |
| `FeignCorrelationIdInterceptor` | Feign 请求头传播 |
| `CorrelationIdHeaderRequestInterceptor` | RestTemplate 请求头传播 |
