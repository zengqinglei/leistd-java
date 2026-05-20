# leistd-core

核心工具模块，提供 Spring 上下文访问、公共异常基类和运行时环境判断。

## Maven 坐标

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-core</artifactId>
</dependency>
```

## 核心类

### ApplicationContextHelper

静态工具类，在非 Spring 管理的对象中获取 Bean。

```java
// 按类型获取（可选，不存在返回 null）
MyService service = ApplicationContextHelper.getBean(MyService.class);

// 按类型获取（必须，不存在抛异常）
MyService service = ApplicationContextHelper.getRequiredBean(MyService.class);

// 按名称获取
Object bean = ApplicationContextHelper.getBean("myService");

// 按名称+类型获取
MyService service = ApplicationContextHelper.getBean("myService", MyService.class);
```

查找策略：先按类型查找，未找到则按名称（首字母小写）查找。

### CommonException

公共运行时异常基类，被 `leistd-exception-core` 中的业务异常继承。

```java
throw new CommonException("操作失败");
throw new CommonException("操作失败", cause);
```

### EnvRuntime

运行时环境判断服务，根据 Spring Profile 判断当前是否为开发环境。

```java
@Service
@RequiredArgsConstructor
public class MyService {
    final EnvRuntime envRuntime;

    public void execute() {
        if (envRuntime.isDevelopment()) {
            // 开发环境逻辑
        }
    }
}
```

开发环境 Profile：`local`、`dev`、`develop`、`development`。无激活 Profile 时默认为开发环境。

### EnvironmentConst

环境常量定义，包含开发环境 Profile 列表。

```java
EnvironmentConst.DEVELOPMENT_ENVIRONMENT // ["local", "dev", "develop", "development"]
```
