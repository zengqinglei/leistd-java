# leistd-dto

数据传输对象模块，提供 DTO 基类、分页模型和转换接口。

## Maven 坐标

```xml
<dependency>
    <groupId>com.mysoft.leistd</groupId>
    <artifactId>leistd-dto</artifactId>
</dependency>
```

## DTO 基类

### 类层次

```
DTO (Serializable)
├── ReqDTO          # 请求输入基类
│   └── PageReqDTO  # 分页请求
└── ResDTO          # 响应输出基类
    └── Response    # 标准响应（code + message）
        └── DataResponse<T>  # 带数据的响应
```

### PageReqDTO

分页请求基类：

```java
public class UserPageReqDTO extends PageReqDTO {
    private String name;  // 业务字段
}
```

| 字段 | 默认值 | 说明 |
|------|--------|------|
| pageIndex | 1 | 页码（从 1 开始） |
| pageSize | 10 | 每页条数 |
| order | null | 排序（如 `created_time desc`） |

通过 `getOffset()` 获取数据库偏移量。

### PageResDTO

分页响应：

```java
PageResDTO<UserResDTO> result = new PageResDTO<>(totalCount, userList);
long total = result.getTotalCount();
List<UserResDTO> items = result.getItems();
```

### Response / DataResponse

标准响应对象：

```java
// 无数据响应
Response.success();                           // {"code": 0}
Response.failure(400, "参数错误");              // {"code": 400, "message": "参数错误"}

// 带数据响应
DataResponse<UserDTO> resp = DataResponse.success(userDTO);  // {"code": 0, "data": {...}}
```

## 转换接口

### BaseReqDTOConvert

请求 DTO → 领域对象：

```java
@Component
public class UserConvert implements BaseReqDTOConvert<User, CreateUserReqDTO> {

    @Override
    public User fromReqDTO(CreateUserReqDTO reqDTO) {
        User user = new User();
        user.setName(reqDTO.getName());
        return user;
    }

    @Override
    public List<User> fromReqDTOList(List<CreateUserReqDTO> reqDTOList) {
        return reqDTOList.stream().map(this::fromReqDTO).collect(Collectors.toList());
    }
}
```

### BaseResDTOConvert

领域对象 → 响应 DTO：

```java
@Component
public class UserConvert implements BaseResDTOConvert<User, UserResDTO> {

    @Override
    public UserResDTO toResDTO(User source) {
        UserResDTO dto = new UserResDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        return dto;
    }

    @Override
    public List<UserResDTO> toResDTOList(List<User> sources) {
        return sources.stream().map(this::toResDTO).collect(Collectors.toList());
    }
}
```
