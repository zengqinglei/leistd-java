# leistd-json

JSON 序列化工具模块，基于 Jackson 封装，提供简洁的 JSON 操作 API。

## Maven 坐标

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-jackson</artifactId>
</dependency>
```

## JsonHelper

### 对象转 JSON 字符串

```java
User user = new User("1", "张三");
String json = JsonHelper.toJSONString(user);
// {"id":"1","name":"张三"}
```

### JSON 字符串转对象

```java
String json = "{\"id\":\"1\",\"name\":\"张三\"}";
User user = JsonHelper.parseObject(json, User.class);
```

### JSON 字符串转列表

```java
String json = "[{\"id\":\"1\"},{\"id\":\"2\"}]";
List<User> users = JsonHelper.parseArray(json, User.class);
```

### 安全转换

所有方法在转换失败时返回 `null` 而不抛出异常，适合在日志、缓存等非关键路径使用：

```java
// 转换失败返回 null
User user = JsonHelper.parseObject(invalidJson, User.class);
```
