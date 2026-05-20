package com.mysoft.leistd.domain.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mysoft.leistd.jackson.JsonHelper;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体基类接口
 */
@Getter
public abstract class Entity {

    /**
     * 领域事件
     */
    @TableField(exist = false)
    private final List<ApplicationEvent> localEvents = new ArrayList<>();

    /**
     * 获取主键
     *
     * @return 返回主键值
     */
    public abstract Object[] getKeys();

    /**
     * 添加本地事件（注意：如果是删除操作，仅 deleteById(entity) 方式生效）
     *
     * @param event 事件
     */
    protected <T extends ApplicationEvent> void addLocalEvent(T event) {
        this.localEvents.add(event);
    }

    /**
     * 清除事件
     */
    public void clearLocalEvents() {
        this.localEvents.clear();
    }

    @Override
    public String toString() {
        return "[ENTITY: " + getClass().getTypeName() + "] Keys = " + JsonHelper.toJSONString(getKeys());
    }
}
