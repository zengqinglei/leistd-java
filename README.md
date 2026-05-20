# LeiStd 文档

LeiStd 是 "Lei Standard" 的缩写，意在打造一个精简、标准统一的开发架构。致力于统一团队开发规范，提升研发效率及质量。

## 为什么选择 LeiStd 框架？
下面概述了您应该使用 LeiStd 平台的原因以及如何使用它：

__1、为何选择 LeiStd ？__

每当我们开始一个新的项目时，我们需要做很多的准备工作： 
- 如何创建新的项目？
- 项目有哪些分层（三层架构、多层架构、领域驱动架构）以及它们如何相互作用？
- 在编写业务代码前，项目是否已就绪下面这些基础能力：
  - 基础能力：获取当前用户信息、分布式锁
  - 其他规范：枚举定义、事件的发布及订阅编写规范、日志规范
  - 异常处理：异常类、异常响应
  - 对象设计规范：统一审计字段、自动填充审计信息
  - 接口规范：统一返回响应体、接口认证、日期格式支持多种解析
  - 入口适配：调度Job、消息消费、链路追踪
- 如何与第三方库集成（Redis、RabbitMq）？
- 如何编写单元测试？

基于以上的一些诉求，LeiStd 提供了一个基于领域驱动设计原则、架构良好、分层且可用于生产的启动解决方案。


__2、它是如何工作的？__

LeiStd 是基于 Spring 开发，因此普通 Spring Boot 项目可以实现的功能，现在都可以使用 LeiStd实现。
LeiStd 通过对组件的封装以及提供一些最佳事件的例子，使得开发入门变得简单高效。 

_注：为了保障更好的可扩展性以及可持续发展，我们在框架设计上尽可能保持 Spring Boot 以及第三方组件的默认约定。_

__3、投资回报__

使用 LeiStd 预计可以降低开发成本30%以上， 主要表现在开发软件项目的几个基本步骤：
- 搭建项目框架（建立代码库、集成必要的第三方库、确定代码开发规范并传递）
- 设计用户界面
- 开发应用程序功能
  - 开发自己的业务模块
  - 开发一些常用的业务模块
  - 在每个用例中应用横切关注点（数据库事务、授权、验证、异常处理等...）
- 开发身份验证系统（OAuth2认证、Jwt用户密码认证等...）
- 开发通用的基类和实体程序服务
- 开发常见的非业务需求（审计日志、软删除、后台作业、权限系统等...）

## 框架介绍
我们提倡以业务为核心、并期望开发者能学习和实践领域驱动设计的思想，将项目的主要关注点放在核心领域和领域逻辑上。
![LeiStd 应用架构](framework/docs/imgs/app-structure.jpg)



## 组件
为了开发更加通用、标准和轻量化的组件，我们减少了对第三方组件的依赖，并降低了组件之间的耦合。

### 领域对象
#### 1、自定义主键类型
```java
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "user")
public class User extends CreationAuditedHasKeyEntity<String> {
    /**
     * -- 自定义主键策略
     */
    @TableId(type = IdType.INPUT)
    private String id;

    @Override
    protected void setId(String id) {
        this.id = id;
        super.setId(id);
    }

    /**
     * 姓名
     */
    private String name;
}
```

### Security（认证）
#### 1、CurrentUser
CurrentUser是获取当前活跃用户信息的主要服务。

示例：注入到ICurrentUser服务中（依赖组件：leistd-security-core）
``` java
@Service
@RequiredArgsConstructor
public class MyService {
    final CurrentUser currentUser;

    public void foo() {
        String userId = currentUser.getId();
        String username = currentUser.getUsername();
    }
}
```

示例：从执行方法中取用户信息（依赖组件：leistd-security-core）
``` java
@Component
@RequiredArgsConstructor
public class JobDemoService {
    final CurrentUser currentUser;

    @SetCurrentUser(username = "#{user.username}")
    public void execute(String userId, User user) {
        String userId = currentUser.getId();
        String username = currentUser.getUsername();
    }
}
```

示例：开启（配置文件：bootstrap.yml）从请求头读取用户信息（依赖组件：leistd-security-mvc）
``` java
leistd:
  current-user-header:
    enable: true
    user-id: X-User-Id
```

## DDD 分层