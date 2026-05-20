# leistd-logging

请求日志模块，自动记录 HTTP 请求和响应信息，支持灵活的字段配置和 MDC 集成。

## Maven 坐标

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-logging-mvc</artifactId>
</dependency>
```

## 功能特性

- 自动记录请求和响应信息
- 可配置记录字段（URI、方法、请求头、请求体、响应状态、响应体等）
- 支持路径排除/包含模式
- 请求体/响应体大小限制
- MDC 上下文集成

## 使用方式

引入依赖后自动生效，默认记录请求 URI 和方法。

### 配置项

```yaml
leistd:
  request-logging:
    # 包含的路径模式（默认所有路径）
    include-patterns: /api/**
    # 排除的路径模式
    exclude-patterns: /actuator/**,/health
    # 记录字段（逗号分隔）
    include: uri,method,headers,body,response-status,response-body
    # 请求体最大记录长度（字节）
    max-body-length: 2048
    # 响应体最大记录长度（字节）
    max-response-length: 2048
```

### 可记录字段

| 字段 | 说明 | MDC Key |
|------|------|---------|
| uri | 请求 URI | `reqUri` |
| method | HTTP 方法 | `reqMethod` |
| headers | 请求头 | `reqHeaders` |
| body | 请求体 | `reqBody` |
| response-status | 响应状态码 | `respStatus` |
| response-body | 响应体 | `respBody` |

### 日志输出示例

```
2024-01-15 INFO  RequestLoggingFilter - Request: POST /api/users Headers={Content-Type=application/json} Body={"name":"张三"}
2024-01-15 INFO  RequestLoggingFilter - Response: 200 Body={"code":0,"data":{"id":"1","name":"张三"}}
```

### 在日志格式中使用

```xml
<pattern>%d [%X{correlationId}] [%X{reqMethod} %X{reqUri}] %-5level %logger - %msg%n</pattern>
```

## leistd-logging-mysoft

扩展模块，预留与公司内部日志平台集成。当前无额外 Java 代码，仅作为依赖占位。
