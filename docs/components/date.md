# leistd-date

日期处理模块，支持多种日期格式的自动解析，并集成 Spring MVC。

## Maven 坐标

```xml
<!-- 日期解析工具 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-date-core</artifactId>
</dependency>

<!-- MVC 自动集成（Web 应用引入） -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-date-mvc</artifactId>
</dependency>
```

## DateHelper

支持以下格式的自动识别和解析：

| 格式 | 示例 |
|------|------|
| ISO 8601 | `2024-01-15T10:30:00.000+08:00` |
| 标准日期时间 | `2024-01-15 10:30:00` |
| 标准日期 | `2024-01-15` |
| 斜杠日期时间 | `2024/01/15 10:30:00` |
| 斜杠日期 | `2024/01/15` |
| RFC 1123 | `Mon, 15 Jan 2024 10:30:00 GMT` |

```java
// 自动识别格式
Date date = DateHelper.parse("2024-01-15");
Date date = DateHelper.parse("2024/01/15 10:30:00");
Date date = DateHelper.parse("2024-01-15T10:30:00.000+08:00");
```

## MVC 集成

引入 `leistd-date-mvc` 后自动生效，无需额外配置。

### JSON 日期解析

Jackson 反序列化时自动支持多种日期格式：

```java
@PostMapping("/events")
public void create(@RequestBody EventDTO dto) {
    // 以下格式均能正确解析：
    // {"date": "2024-01-15"}
    // {"date": "2024-01-15 10:30:00"}
    // {"date": "2024-01-15T10:30:00.000+08:00"}
}
```

### 表单日期解析

表单提交（`@RequestParam` / `@ModelAttribute`）同样支持多格式解析：

```java
@GetMapping("/events")
public List<EventDTO> list(@RequestParam Date startDate) {
    // /events?startDate=2024-01-15
    // /events?startDate=2024-01-15 10:30:00
}
```

## 配置项

`leistd-date-mvc` 默认使用 `Asia/Shanghai` 时区，Jackson 序列化关闭时间戳模式（输出格式化字符串）。如需自定义，通过 Spring Boot 标准 Jackson 配置覆盖：

```yaml
spring:
  jackson:
    time-zone: Asia/Shanghai
    serialization:
      write-dates-as-timestamps: false
```
