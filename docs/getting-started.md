# 快速开始

## 环境要求

| 项目 | 版本 |
|------|------|
| JDK | 21+ |
| Spring Boot | 3.2.x |
| Maven | 3.8+ |
| Redis | 5.0+（使用分布式锁时） |

## 创建项目

### 1. 初始化 Maven 项目

创建一个标准的多模块 Maven 项目，推荐结构如下：

```
my-project/
├── pom.xml                    # 父 POM
├── my-project-domain/         # 领域层
├── my-project-app/            # 应用层
├── my-project-adapter/        # 适配层
├── my-project-infrastructure/ # 基础设施层
└── my-project-start/          # 启动模块
```

### 2. 引入 LeiStd BOM

在父 POM 中添加依赖管理：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.mysoft.leistd</groupId>
            <artifactId>leistd-framework-dependencies</artifactId>
            <version>2.0.62</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3. 按需引入组件

各模块按职责引入对应组件：

**启动模块 (start)**

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 统一响应 + 异常处理 -->
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-response-mvc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-exception-mvc</artifactId>
    </dependency>

    <!-- 请求日志 -->
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-logging-mvc</artifactId>
    </dependency>

    <!-- 链路追踪 -->
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-tracing-mvc</artifactId>
    </dependency>
</dependencies>
```

**领域层 (domain)**

```xml
<dependencies>
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-ddd-domain</artifactId>
    </dependency>
</dependencies>
```

**基础设施层 (infrastructure)**

```xml
<dependencies>
    <dependency>
        <groupId>com.mysoft.leistd</groupId>
        <artifactId>leistd-ddd-mybatis</artifactId>
    </dependency>
</dependencies>
```

### 4. 配置文件

`bootstrap.yml` 最小配置：

```yaml
server:
  port: 8080

spring:
  application:
    name: my-project-service
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 5. 启动类

```java
@SpringBootApplication
public class MyProjectApp {
    public static void main(String[] args) {
        SpringApplication.run(MyProjectApp.class, args);
    }
}
```

## 常用组件速配

### 开启统一响应包装

在 Controller 类或方法上添加 `@ResponseWrapper` 注解：

```java
@RestController
@ResponseWrapper
public class UserController {
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable String id) {
        // 返回值会自动包装为 {"code":0, "data": {...}}
        return userService.getUser(id);
    }
}
```

### 开启请求头用户读取

```yaml
leistd:
  current-user-header:
    enable: true
    user-id: X-User-Id
    username: X-Username
```

### 开启请求日志

```yaml
leistd:
  request-logging:
    include: uri,method,headers,body,response-status,response-body
    max-body-length: 2048
```

### 使用分布式锁

引入 Redis 实现：

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-dstblock-springredis</artifactId>
</dependency>
```

配置 Redis 连接后，直接使用注解：

```java
@DistributedLock(key = "'order:' + #orderId")
public void processOrder(String orderId) {
    // 自动加锁/解锁
}
```

## 下一步

- [架构总览](architecture.md) - 了解框架整体设计
- [DDD 分层指南](ddd-guide.md) - 学习领域驱动分层实践
- [组件文档](components/) - 各组件详细使用说明
