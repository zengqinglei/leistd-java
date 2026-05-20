# leistd-response

统一 API 响应包装模块，通过注解自动将 Controller 返回值包装为标准响应格式。

## Maven 坐标

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-response-mvc</artifactId>
</dependency>
```

## 响应格式

所有被包装的接口统一返回以下格式：

**成功响应**

```json
{
  "code": 0,
  "data": { ... }
}
```

**失败响应**

```json
{
  "code": 40000,
  "message": "参数错误",
  "details": "..."
}
```

## 使用方式

### @ResponseWrapper 注解

在 Controller 类或方法上添加注解，启用自动响应包装：

```java
@RestController
@RequestMapping("/api/users")
@ResponseWrapper
public class UserController {

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        // 返回值自动包装为 {"code": 0, "data": {"id":"1","name":"张三"}}
        return userService.getUser(id);
    }

    @PostMapping
    public UserDTO create(@RequestBody CreateUserReqDTO reqDTO) {
        return userService.createUser(reqDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        // void 返回值包装为 {"code": 0}
        userService.delete(id);
    }
}
```

### 方法级别控制

可以对单个方法关闭包装：

```java
@RestController
@ResponseWrapper
public class UserController {

    @ResponseWrapper(disabled = true)
    @GetMapping("/raw")
    public Map<String, Object> raw() {
        // 不包装，直接返回原始对象
        return Map.of("custom", "format");
    }
}
```

### 跳过包装的类型

以下返回类型不会被自动包装：

- `ResponseEntity` - Spring 原生响应
- `Response` / `DataResponse` - 已是标准响应格式

```java
@GetMapping("/manual")
public DataResponse<UserDTO> manual() {
    // 手动构造响应，不会被二次包装
    return DataResponse.success(userDTO);
}
```

## 自定义响应处理

实现 `ResponseHandler` 接口替换默认包装逻辑：

```java
@Component
public class CustomResponseHandler implements ResponseHandler {
    @Override
    public Object handle(Object result) {
        // 自定义包装逻辑
        return Map.of("code", 0, "data", result);
    }
}
```

## 工作原理

1. `ResponseInterceptor` 检查请求映射的 Controller/方法是否有 `@ResponseWrapper` 注解
2. `ResponseWrapperBodyAdvice` 在响应写入前，根据拦截器标记对返回值进行包装
3. 包装逻辑委托给 `ResponseHandler` Bean（默认 `DefaultResponseHandler`）
