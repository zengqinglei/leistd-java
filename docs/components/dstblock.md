# leistd-dstblock

分布式锁模块，基于 Redis 实现分布式环境下的并发控制。

## Maven 坐标

```xml
<!-- 分布式锁接口 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-dstblock-core</artifactId>
</dependency>

<!-- Redis 实现 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-dstblock-springredis</artifactId>
</dependency>
```

## 前置条件

- Redis 服务可用
- Spring Boot 已配置 Redis 连接

## 使用方式

### 注解方式（推荐）

使用 `@DistributedLock` 注解声明式加锁：

```java
@Service
public class OrderService {

    // SpEL 表达式引用方法参数
    @DistributedLock(key = "'order:' + #orderId")
    public void processOrder(String orderId) {
        // 方法执行前自动获取锁，执行后自动释放
    }

    // 固定 key
    @DistributedLock(key = "'global:sync'")
    public void syncData() {
        // 全局同步锁
    }
}
```

#### SpEL 表达式

`key` 属性支持 Spring Expression Language，可引用方法参数：

```java
@DistributedLock(key = "'user:' + #user.id")
public void updateUser(User user) { }

@DistributedLock(key = "'batch:' + #batchId + ':' + #itemId")
public void processItem(String batchId, String itemId) { }
```

### 编程式方式

注入 `IDistributedLock` 接口：

```java
@Service
@RequiredArgsConstructor
public class OrderService {
    final IDistributedLock distributedLock;

    public void processOrder(String orderId) {
        String lockKey = "order:" + orderId;
        ILock lock = distributedLock.lock(lockKey);
        try {
            // 业务逻辑
        } finally {
            lock.unlock();
        }
    }
}
```

### try-with-resources

`ILock` 实现 `AutoCloseable`，支持 try-with-resources：

```java
public void processOrder(String orderId) {
    try (ILock lock = distributedLock.lock("order:" + orderId)) {
        // 业务逻辑
    }  // 自动 unlock
}
```

## 配置项

```yaml
leistd:
  distributed-lock:
    # 锁前缀（默认应用名）
    prefix: my-app
    # 应用名称
    application-name: ${spring.application.name}
```

锁的完整 Key 格式：`{prefix}:{application-name}:{key}`

## 实现原理

基于 Spring Integration 的 `RedisLockRegistry` 实现：

- 使用 Redis SET 命令加锁
- 支持锁的自动过期（防止死锁）
- `SpringRedisDstbLockConfig` 自动注册 `RedisLockRegistry` Bean

## 核心类

| 类 | 说明 |
|----|------|
| `IDistributedLock` | 分布式锁接口 |
| `ILock` | 锁包装（AutoCloseable） |
| `DistributedLock` | 方法注解 |
| `DistributedLockAspect` | 注解切面 |
| `DistributedLockProps` | 配置属性 |
| `SpringRedisDstbLockImpl` | Redis 实现 |
