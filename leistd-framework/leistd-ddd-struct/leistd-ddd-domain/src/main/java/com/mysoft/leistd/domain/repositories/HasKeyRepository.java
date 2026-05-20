package com.mysoft.leistd.domain.repositories;

import com.mysoft.leistd.domain.entities.Entity;

import java.util.Collection;

/**
 * 含有主键的实体仓储接口
 *
 * @param <TEntity>     实体泛型
 * @param <TPrimaryKey> 主键泛型
 */
public interface HasKeyRepository<TEntity extends Entity, TPrimaryKey> extends Repository<TEntity> {
    /**
     * 根据主键获取一个实体
     *
     * @param id 主键
     * @return 返回一个实体
     */
    TEntity getById(TPrimaryKey id);

    /**
     * 根据Id修改实体信息
     *
     * @param entity 实体对象
     * @return 返回是否成功
     */
    boolean updateById(TEntity entity);

    /**
     * 批量根据Id修改实体信息
     *
     * @param entities 实体对象集合
     * @return 返回是否成功
     */
    boolean updateBatchById(Collection<TEntity> entities);

    /**
     * 根据主键删除一个实体
     *
     * @param id 主键
     * @return 返回是否删除成功
     */
    boolean deleteById(TPrimaryKey id);

    /**
     * 批量根据主键删除实体
     *
     * @param ids 主键集合
     * @return 返回是否删除成功（软删除不会填充审计字段）
     */
    boolean deleteBatchByIds(Collection<TPrimaryKey> ids);

    /**
     * 根据主键删除一个实体
     *
     * @param entity 实体对象
     * @return 返回是否删除成功
     */
    boolean deleteById(TEntity entity);
}
