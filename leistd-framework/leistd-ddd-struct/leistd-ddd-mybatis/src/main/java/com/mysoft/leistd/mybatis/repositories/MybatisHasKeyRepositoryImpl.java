package com.mysoft.leistd.mybatis.repositories;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.domain.entities.Entity;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;

import java.io.Serializable;
import java.util.Collection;

public abstract class MybatisHasKeyRepositoryImpl<TEntity extends Entity, TPrimaryKey extends Serializable>
        extends MybatisRepositoryImpl<TEntity>
        implements HasKeyRepository<TEntity, TPrimaryKey> {

    public MybatisHasKeyRepositoryImpl(IService<TEntity> service, BaseMapper<TEntity> mapper) {
        super(service, mapper);
    }

    @Override
    public TEntity getById(TPrimaryKey id) {
        return service.getById(id);
    }

    @Override
    public boolean updateById(TEntity entity) {
        return service.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<TEntity> entities) {
        return service.updateBatchById(entities);
    }

    @Override
    public boolean deleteById(TPrimaryKey id) {
        return service.removeById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<TPrimaryKey> ids) {
        return service.removeBatchByIds(ids);
    }

    @Override
    public boolean deleteById(TEntity entity) {
        return service.removeById(entity);
    }
}
