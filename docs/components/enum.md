# leistd-enum

枚举规范模块，提供自定义 Code 枚举基类，并自动集成 Jackson 序列化和 Spring MVC 参数转换。

## Maven 坐标

```xml
<!-- 核心枚举定义 + Jackson 反序列化 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-enum-core</artifactId>
</dependency>

<!-- MVC 表单参数转换（按需引入） -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-enum-mvc</artifactId>
</dependency>
```

## 枚举接口层次

```
IEnum<E>                    # 最基础枚举接口（按 Name 匹配）
└── BaseEnum<V, E>          # 带 Code 的枚举接口（按 Code 或 Name 匹配）
    └── DefaultEnum<E>      # Code 类型为 Integer 的便捷接口
```

## 使用方式

### 定义枚举

**方式一：IEnum（仅按 Name 匹配）**

适用于枚举值与 JSON 字段一一对应的简单场景。

```java
public enum Status implements IEnum<Status> {
    ACTIVE, INACTIVE;
}
```

JSON 序列化/反序列化使用枚举名称：`"ACTIVE"`

**方式二：BaseEnum（按 Code 匹配）**

适用于前端使用数字/字符串编码的场景。

```java
@Getter
public enum UserType implements BaseEnum<Integer, UserType> {
    ADMIN(1, "管理员"),
    MEMBER(2, "普通用户");

    private final int code;
    private final String desc;

    UserType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
```

JSON 序列化输出 Code 值：`1` 或 `2`，反序列化同时支持 Code 和 Name。

**方式三：DefaultEnum（Integer Code 快捷方式）**

```java
@Getter
public enum Gender implements DefaultEnum<Gender> {
    MALE(1), FEMALE(2);

    private final int code;

    Gender(int code) {
        this.code = code;
    }
}
```

### Code 查找

```java
// 按 Code 查找枚举实例
UserType type = BaseEnum.codeOf(1, UserType.class);  // ADMIN

// 按 Name 查找枚举实例
UserType type = IEnum.nameOf("ADMIN", UserType.class);  // ADMIN
```

### MVC 参数绑定

引入 `leistd-enum-mvc` 后，Controller 方法参数自动支持枚举转换：

```java
@GetMapping("/users")
public List<UserDTO> list(@RequestParam UserType type) {
    // 以下请求均能正确绑定：
    // /users?type=1       → UserType.ADMIN（按 Code）
    // /users?type=ADMIN   → UserType.ADMIN（按 Name）
}
```

## 自动配置

| 模块 | 自动注册 |
|------|---------|
| leistd-enum-core | `BaseEnumDeserializer`、`IEnumDeserializer`（Jackson 反序列化器） |
| leistd-enum-mvc | `BaseEnumConvertFactory`、`IEnumConvertFactory`（MVC 参数转换器） |
