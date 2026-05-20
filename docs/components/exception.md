# leistd-exception

业务异常处理模块，提供标准化的异常体系和 Spring MVC 全局异常处理。

## Maven 坐标

```xml
<!-- 异常类定义 -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-exception-core</artifactId>
</dependency>

<!-- 全局异常处理（Web 应用引入） -->
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-exception-mvc</artifactId>
</dependency>
```

## 异常体系

```
RuntimeException
└── CommonException                    # 通用异常（leistd-core）
└── BusinessException                  # 业务异常基类
    ├── BadRequestException            # 400 - 请求参数错误
    ├── UnauthorizedErrorException     # 401 - 未认证
    ├── ForbiddenException             # 403 - 无权限
    ├── NotFoundErrorException         # 404 - 资源不存在
    ├── UnsupportedMediaTypeException  # 415 - 不支持的媒体类型
    ├── UnprocessableEntityException   # 422 - 验证错误
    └── InternalServerErrorException   # 500 - 服务器内部错误
```

## 使用方式

### 抛出业务异常

```java
// 400 请求参数错误
throw new BadRequestException("用户名不能为空");

// 404 资源不存在
throw new NotFoundErrorException("用户不存在");

// 403 无权限
throw new ForbiddenException("无权访问该资源");

// 422 验证错误（支持多个错误详情）
throw new UnprocessableEntityException("数据验证失败")
    .addError("name", "名称不能为空")
    .addError("email", "邮箱格式不正确");

// 500 服务器内部错误
throw new InternalServerErrorException("系统异常");
```

### 自定义错误码

每个异常都有默认错误码，也可以自定义：

```java
// 默认错误码：40000
throw new BadRequestException("参数错误");

// 自定义错误码
throw new BadRequestException(40001, "用户名已存在");
```

| 异常类 | 默认错误码 |
|--------|-----------|
| BadRequestException | 40000 |
| UnauthorizedErrorException | 40100 |
| ForbiddenException | 40300 |
| NotFoundErrorException | 40400 |
| UnsupportedMediaTypeException | 41500 |
| UnprocessableEntityException | 42200 |
| InternalServerErrorException | 50000 |

## 全局异常处理

引入 `leistd-exception-mvc` 后，所有 Controller 抛出的异常自动转换为标准响应格式。

### 自动转换规则

| 异常类型 | HTTP 状态码 | 响应示例 |
|---------|------------|---------|
| BadRequestException | 400 | `{"code": 40000, "message": "参数错误"}` |
| UnauthorizedErrorException | 401 | `{"code": 40100, "message": "未认证"}` |
| ForbiddenException | 403 | `{"code": 40300, "message": "无权限"}` |
| NotFoundErrorException | 404 | `{"code": 40400, "message": "资源不存在"}` |
| UnprocessableEntityException | 422 | `{"code": 42200, "message": "...", "errors": [...]}` |
| CommonException | 500 | `{"code": 50000, "message": "..."}` |
| 未知异常 | 500 | `{"code": 50000, "message": "Internal Server Error"}` |

### 自定义异常处理

实现 `ExceptionHandler` 接口替换默认行为：

```java
@Component
public class CustomExceptionHandler implements ExceptionHandler {
    @Override
    public Response handle(Exception ex) {
        // 自定义异常处理逻辑
        return Response.failure(500, ex.getMessage());
    }
}
```

### 配置项

```yaml
leistd:
  global-exception:
    # 是否包装非 API 请求的失败响应（默认 true）
    wrap-failure-response: true
```
