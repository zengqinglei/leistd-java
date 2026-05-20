# leistd-locallock

本地锁模块，基于内存信号量实现单实例内的并发控制。

## Maven 坐标

```xml
<!-- 本地锁接口 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-locallock-core</artifactId>
</dependency>

<!-- 内存实现 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-locallock-memory</artifactId>
</dependency>
```

## 适用场景

- 单实例部署，无需跨进程协调
- 需要控制同一 JVM 内的并发访问
- 作为分布式锁的轻量替代（开发/测试环境）

## 使用方式

### 注解方式（推荐）

```java
@Service
public class ReportService {

    // 同一时刻只允许一个线程生成同一报表
    @LocalLock(key = "'report:' + #reportId")
    public void generateReport(String reportId) {
        // 业务逻辑
    }
}
```

`key` 属性支持 SpEL 表达式，与 `@DistributedLock` 用法一致。

### 编程式方式

```java
@Service
@RequiredArgsConstructor
public class ReportService {
    final ILocalLock localLock;

    public void generateReport(String reportId) {
        try (ILock lock = localLock.lock("report:" + reportId)) {
            // 业务逻辑
        }  // 自动 unlock
    }
}
```

## 配置项

```yaml
leistd:
  local-lock:
    # 锁前缀
    prefix: my-app
    # 应用名称
    application-name: ${spring.application.name}
```

## 实现原理

基于 Java `Semaphore` 实现：

- 每个 Key 对应一个 Semaphore（许可数为 1）
- `SemaphoreLockMetadata` 记录锁的使用时间戳
- 内置清理线程自动移除长时间未使用的锁，防止内存泄漏

## 核心类

| 类 | 说明 |
|----|------|
| `ILocalLock` | 本地锁接口 |
| `ILock` | 锁包装（AutoCloseable） |
| `LocalLock` | 方法注解 |
| `LocalLockAspect` | 注解切面 |
| `LocalLockProps` | 配置属性 |
| `MemoryLocalLockImpl` | 内存信号量实现 |
| `SemaphoreLockMetadata` | 信号量元数据（含时间戳） |
