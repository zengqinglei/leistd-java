package com.mysoft.leistd.domain.repositories;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.mysoft.leistd.domain.entities.Entity;
import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;

import java.util.Collection;
import java.util.List;

/**
 * 含实体的仓储接口
 *
 * @param <TEntity> 实体泛型
 */
public interface Repository<TEntity extends Entity> {

    /**
     * 获取一个实体
     *
     * @param queryWrapper 查询条件
     * @return 返回一个实体（符合条件有多条时抛出异常）
     */
    TEntity getOne(Wrapper<TEntity> queryWrapper);

    /**
     * 查找第一条实体
     *
     * @param queryWrapper 查询条件
     * @return 返回符合条件的第一个实体
     */
    TEntity getFirst(Wrapper<TEntity> queryWrapper);

    /**
     * 查询所有
     *
     * @return 返回所有实体
     */
    List<TEntity> list();

    /**
     * 根据查询条件获取列表
     *
     * @param queryWrapper 查询条件
     * @return 返回符合条件的列表
     */
    List<TEntity> list(Wrapper<TEntity> queryWrapper);

    /**
     * 根据查询条件获取分页数据
     *
     * @param queryWrapper 查询条件
     * @param pageQuery    分页条件
     * @return 返回符合条件的分页数据
     */
    PageResult<TEntity> page(Wrapper<TEntity> queryWrapper, PageQuery pageQuery);

    /**
     * 统计总数量
     *
     * @return 返回实体总数量
     */
    long count();

    /**
     * 根据条件统计数量
     *
     * @param queryWrapper 查询条件
     * @return 返回符合条件的实体数量
     */
    long count(Wrapper<TEntity> queryWrapper);

    /**
     * 插入一条实体记录
     *
     * @param entity 实体对象
     * @return 返回是否成功
     */
    boolean insert(TEntity entity);

    /**
     * 批量插入实体记录
     *
     * @param entities 实体对象集合
     * @return 返回是否成功
     */
    boolean insertBatch(Collection<TEntity> entities);

    /**
     * 根据条件修改实体信息
     *
     * @param entity       实体对象
     * @param queryWrapper 查询条件
     * @return 返回是否成功
     */
    boolean update(TEntity entity, Wrapper<TEntity> queryWrapper);

    /**
     * 删除符合条件的实体对象
     *
     * @param queryWrapper 查询条件
     * @return 返回是否成功（软删除不会填充审计字段）
     */
    boolean delete(Wrapper<TEntity> queryWrapper);
}
