package com.mysoft.leistd.mybatis.repositories;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.domain.entities.Entity;
import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;
import com.mysoft.leistd.domain.repositories.Repository;
import com.mysoft.leistd.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MybatisRepositoryImpl<TEntity extends Entity> implements Repository<TEntity> {
    protected final IService<TEntity> service;
    protected final BaseMapper<TEntity> mapper;

    @Override
    public TEntity getOne(Wrapper<TEntity> queryWrapper) {
        return service.getOne(queryWrapper);
    }

    @Override
    public TEntity getFirst(Wrapper<TEntity> queryWrapper) {
        IPage<TEntity> entityPage = service.page(new Page<>(1, 1), queryWrapper);
        return entityPage.getRecords().stream().findFirst().orElse(null);
    }

    @Override
    public List<TEntity> list() {
        return service.list();
    }

    @Override
    public List<TEntity> list(Wrapper<TEntity> queryWrapper) {
        return service.list(queryWrapper);
    }

    @Override
    public PageResult<TEntity> page(Wrapper<TEntity> queryWrapper, PageQuery pageQuery) {
        IPage<TEntity> pageable = new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize());
        if (StringUtils.isNotBlank(pageQuery.getOrder())) {
            for (String orderArr : pageQuery.getOrder().split(",")) {
                String[] orderStr = orderArr.split(" ");
                if (orderStr.length == 0) {
                    log.error("输入的排序字符串【{}】不符合规范", pageQuery.getOrder());
                    throw new BadRequestException("请输入正确的排序内容");
                }
                String fieldName = orderStr[0];
                boolean isAsc = orderStr.length > 1 && StringUtils.equalsIgnoreCase(orderStr[1], "asc");
                pageable.orders().add(new OrderItem().setColumn(fieldName).setAsc(isAsc));
            }
        }
        IPage<TEntity> pageResult = service.page(pageable, queryWrapper);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public long count() {
        return service.count();
    }

    @Override
    public long count(Wrapper<TEntity> queryWrapper) {
        return service.count(queryWrapper);
    }

    @Override
    public boolean insert(TEntity entity) {
        return service.save(entity);
    }

    @Override
    public boolean insertBatch(Collection<TEntity> entities) {
        return service.saveBatch(entities);
    }

    @Override
    public boolean update(TEntity entity, Wrapper<TEntity> queryWrapper) {
        return service.update(entity, queryWrapper);
    }

    @Override
    public boolean delete(Wrapper<TEntity> queryWrapper) {
        return service.remove(queryWrapper);
    }
}
