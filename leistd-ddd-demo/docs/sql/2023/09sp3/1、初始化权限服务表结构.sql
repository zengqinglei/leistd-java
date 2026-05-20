create table enterprise_user_role
(
    id                 bigint auto_increment comment '主键id' primary key,
    enterprise_id      bigint comment '企业id',
    user_id            varchar(64) not null comment '用户id',
    role_id            bigint      not null comment '角色id',
    is_deleted         tinyint(1)  not null comment '是否删除',
    creator_id         varchar(64) not null comment '创建人用户名',
    creator_name       varchar(64) not null comment '创建人姓名',
    created_time       datetime(3) not null comment '创建时间',
    last_modifier_id   varchar(64) comment '上次修改人用户名',
    last_modifier_name varchar(64) comment '上次修改人姓名',
    last_modified_time datetime(3) comment '上次修改时间',
    deleter_id         varchar(64) comment '删除人用户名',
    deleter_name       varchar(64) comment '删除人姓名',
    deleted_time       datetime(3) comment '删除时间'
);

create table permission
(
    id                 bigint auto_increment comment '主键' primary key,
    name               varchar(128) not null comment '权限名称',
    display_name       varchar(128) not null comment '权限显示名称',
    code               varchar(128) not null comment '权限编码',
    is_deleted         tinyint(1)   not null comment '是否删除',
    creator_id         varchar(64)  not null comment '创建人用户名',
    creator_name       varchar(64)  not null comment '创建人姓名',
    created_time       datetime(3)  not null comment '创建时间',
    last_modifier_id   varchar(64) comment '上次修改人用户名',
    last_modifier_name varchar(64) comment '上次修改人姓名',
    last_modified_time datetime(3) comment '上次修改时间',
    deleter_id         varchar(64) comment '删除人用户名',
    deleter_name       varchar(64) comment '删除人姓名',
    deleted_time       datetime(3) comment '删除时间'
);

create table role
(
    id                       bigint auto_increment comment '主键' primary key,
    enterprise_id            bigint comment '企业Id',
    name                     varchar(64) not null comment '角色名称',
    is_allow_edit_permission tinyint(1)  not null comment '是否允许编辑权限',
    is_allow_delete          tinyint(1)  not null comment '是否允许删除',
    is_deleted               tinyint(1)  not null comment '是否删除',
    creator_id               varchar(64) not null comment '创建人用户名',
    creator_name             varchar(64) not null comment '创建人姓名',
    created_time             datetime(3) not null comment '创建时间',
    last_modifier_id         varchar(64) comment '上次修改人用户名',
    last_modifier_name       varchar(64) comment '上次修改人姓名',
    last_modified_time       datetime(3) comment '上次修改时间',
    deleter_id               varchar(64) comment '删除人用户名',
    deleter_name             varchar(64) comment '删除人姓名',
    deleted_time             datetime(3) comment '删除时间'
);

create table role_permission
(
    id                 bigint auto_increment comment '主键' primary key,
    role_id            bigint      not null comment '角色id',
    permission_id      bigint      not null comment '权限id',
    is_deleted         tinyint(1)  not null comment '是否删除',
    creator_id         varchar(64) not null comment '创建人用户名',
    creator_name       varchar(64) not null comment '创建人姓名',
    created_time       datetime(3) not null comment '创建时间',
    last_modifier_id   varchar(64) comment '上次修改人用户名',
    last_modifier_name varchar(64) comment '上次修改人姓名',
    last_modified_time datetime(3) comment '上次修改时间',
    deleter_id         varchar(64) comment '删除人用户名',
    deleter_name       varchar(64) comment '删除人姓名',
    deleted_time       datetime(3) comment '删除时间'
);

create table user
(
    id           varchar(64) not null comment '用户id' primary key,
    name         varchar(64) not null comment '姓名',
    username     varchar(64) not null comment '登录名',
    email        varchar(64) not null comment '邮箱',
    phone        varchar(32) not null comment '手机号',
    is_enabled   tinyint(1)  not null comment '是否启用',
    is_admin     tinyint(1)  not null comment '是否管理员',
    creator_id   varchar(64) not null comment '创建人用户名',
    creator_name varchar(64) not null comment '创建人姓名',
    created_time datetime(3) not null comment '创建时间'
);

