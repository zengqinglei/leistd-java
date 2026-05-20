# 架构总览

## 设计理念

LeiStd 遵循以下核心设计原则：

1. **领域优先** - 以业务为核心，技术实现服务于领域逻辑
2. **最小侵入** - 尽可能保持 Spring Boot 及第三方组件的默认约定
3. **低耦合** - 组件之间相互独立，按需引入，不产生不必要的依赖
4. **约定优于配置** - 提供合理的默认值，减少样板代码

## 分层架构

LeiStd 采用领域驱动设计（DDD）的四层架构：

```
┌─────────────────────────────────────────────┐
│                Adapter（适配层）              │
│          Controller / MessageListener         │
├─────────────────────────────────────────────┤
│                 App（应用层）                 │
│         AppService / DTO / Convert            │
├─────────────────────────────────────────────┤
│               Domain（领域层）                │
│      Entity / DomainService / Repository      │
├─────────────────────────────────────────────┤
│           Infrastructure（基础设施层）         │
│       MyBatis Mapper / Redis / MQ Client      │
└─────────────────────────────────────────────┘
```

### 各层职责

| 层 | 模块 | 职责 |
|----|------|------|
| Adapter | leistd-ddd-adapter | 接收外部请求，参数校验，调用应用服务 |
| App | leistd-ddd-app | 编排领域服务，DTO 与领域对象转换，事务控制 |
| Domain | leistd-ddd-domain | 核心业务逻辑，定义实体、值对象、领域服务、仓储接口 |
| Infrastructure | leistd-ddd-mybatis | 实现仓储接口，数据库访问，外部服务集成 |

### 依赖规则

- Adapter 依赖 App
- App 依赖 Domain
- Infrastructure 依赖 Domain（实现 Domain 定义的仓储接口）
- Domain 不依赖任何上层模块

## 组件架构

LeiStd 的组件按功能域组织，每个组件包含 core 和集成模块：

```
组件结构：
  leistd-xxx/
  ├── leistd-xxx-core          # 核心接口与实现（无 Web 依赖）
  └── leistd-xxx-mvc           # Spring MVC 集成（Filter、Interceptor）
```

### 组件依赖关系

```
leistd-core ────────────────────────────────────── 基础工具
    │
leistd-mdc-core ──── MDC 上下文管理
    │
leistd-tracing-core ─ 链路追踪接口
    │
leistd-security-core ─ 用户上下文接口
    │
leistd-exception-core ─ 业务异常定义
    │
leistd-response-mvc ─── 统一响应包装（依赖 exception-core）
    │
leistd-logging-mvc ──── 请求日志（依赖 mdc-core）
    │
leistd-dstblock ──────── 分布式锁
leistd-locallock ─────── 本地锁
```

## 组件分类

### 基础设施组件

| 组件 | 说明 | 文档 |
|------|------|------|
| leistd-core | 上下文工具、公共异常、环境判断 | [core.md](components/core.md) |
| leistd-dto | DTO 基类、分页、转换接口 | [dto.md](components/dto.md) |
| leistd-enum | 枚举规范 + 自动转换 | [enum.md](components/enum.md) |
| leistd-json | JSON 序列化工具 | [json.md](components/json.md) |
| leistd-date | 日期多格式解析 | [date.md](components/date.md) |

### Web 层组件

| 组件 | 说明 | 文档 |
|------|------|------|
| leistd-exception | 业务异常体系 + 全局处理 | [exception.md](components/exception.md) |
| leistd-response | 统一 API 响应包装 | [response.md](components/response.md) |
| leistd-logging | 请求/响应日志 | [logging.md](components/logging.md) |
| leistd-mdc | MDC 日志上下文 | [mdc.md](components/mdc.md) |

### 安全与追踪组件

| 组件 | 说明 | 文档 |
|------|------|------|
| leistd-security | 用户上下文、认证、请求头传播 | [security.md](components/security.md) |
| leistd-tracing | 链路追踪（Correlation ID） | [tracing.md](components/tracing.md) |

### 并发控制组件

| 组件 | 说明 | 文档 |
|------|------|------|
| leistd-dstblock | 分布式锁（Redis） | [dstblock.md](components/dstblock.md) |
| leistd-locallock | 本地锁（内存信号量） | [locallock.md](components/locallock.md) |
