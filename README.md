# LeiStd

LeiStd 是 "Lei Standard" 的缩写，意为精简、标准统一的开发架构。基于领域驱动设计原则，致力于统一团队开发规范，提升研发效率及质量。

## 特性

- **DDD 分层架构** - 提供完整的 Domain / App / Adapter / Infrastructure 四层结构
- **开箱即用的组件** - 认证、链路追踪、日志、异常处理、分布式锁等横切关注点一站集成
- **统一响应格式** - API 返回值自动包装，异常自动转换为标准响应
- **枚举规范** - 自定义 Code 枚举 + Jackson/MVC 自动转换
- **审计字段自动填充** - 创建人、修改人、软删除等字段无需手动赋值
- **最小侵入** - 尽可能保持 Spring Boot 及第三方组件的默认约定

## 环境要求

| 项目 | 版本 |
|------|------|
| JDK | 21+ |
| Spring Boot | 3.2.x |
| Maven | 3.8+ |

## 快速开始

### 1. 引入 BOM

在父 POM 中引入 LeiStd 依赖管理：

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

### 2. 按需引入组件

```xml
<!-- 统一响应 + 异常处理 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-response-mvc</artifactId>
</dependency>
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-exception-mvc</artifactId>
</dependency>

<!-- 认证与用户上下文 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-security-core</artifactId>
</dependency>

<!-- DDD 分层结构 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-ddd-domain</artifactId>
</dependency>
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-ddd-mybatis</artifactId>
</dependency>
```

### 3. 编写业务代码

参见 [DDD 分层指南](docs/ddd-guide.md) 和 [示例项目](docs/components/demo.md)。

## 模块总览

```
leistd-java/
├── leistd-framework/                    # 框架核心
│   ├── leistd-components/               # 组件库
│   │   ├── leistd-core                  # 上下文工具、异常基类、环境判断
│   │   ├── leistd-dto                   # 数据传输对象基类与转换接口
│   │   ├── leistd-enum                  # 枚举规范（Jackson + MVC 转换）
│   │   ├── leistd-exception             # 业务异常体系 + 全局异常处理
│   │   ├── leistd-json                  # JSON 序列化工具
│   │   ├── leistd-date                  # 日期多格式解析 + MVC 集成
│   │   ├── leistd-response              # 统一 API 响应包装
│   │   ├── leistd-security              # 用户上下文、JWT 认证、请求头传播
│   │   ├── leistd-tracing               # 链路追踪（Correlation ID）
│   │   ├── leistd-logging               # 请求/响应日志
│   │   ├── leistd-mdc                   # MDC 日志上下文管理
│   │   ├── leistd-dstblock              # 分布式锁（Redis 实现）
│   │   └── leistd-locallock             # 本地锁（内存信号量）
│   ├── leistd-ddd-struct/               # DDD 分层结构
│   │   ├── leistd-ddd-domain            # 领域层（实体、仓储、规约）
│   │   ├── leistd-ddd-app               # 应用层
│   │   ├── leistd-ddd-adapter           # 适配层
│   │   └── leistd-ddd-mybatis           # 基础设施层（MyBatis-Plus 实现）
│   └── leistd-framework-dependencies    # BOM 依赖管理
│
└── leistd-ddd-demo/                     # 示例项目（权限管理）
    ├── permission-domain                # 领域层
    ├── permission-app                   # 应用层
    ├── permission-adapter               # 适配层
    ├── permission-infrastructure        # 基础设施层
    └── permission-start                 # 启动模块
```

## 文档

- [快速开始](docs/getting-started.md)
- [架构总览](docs/architecture.md)
- [DDD 分层指南](docs/ddd-guide.md)
- [组件文档](docs/components/)

## 版本分支

| 分支 | Spring Boot | JDK |
|------|-------------|-----|
| `0.x(spring-boot-2.5.15)` | 2.5.15 | 8 |
| `2.x(spring-boot-3.2x)` | 3.2.x | 21 |
| `2.5.x(spring-boot-3.4.x)` | 3.4.x | 21 |

## License

[MIT](LICENSE)
