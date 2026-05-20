package com.mysoft.leistd.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mysoft.leistd.domain.entities.Entity;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Intercepts({
        @Signature(method = "update", type = Executor.class, args = {
                MappedStatement.class,
                Object.class
        })
})
public class EntityLocalEventInterceptor implements Interceptor {
    final ApplicationEventPublisher eventPublisher;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 先执行完毕
        Object proceed = invocation.proceed();
        Object[] args = invocation.getArgs();
        if (args.length < 2) {
            return proceed;
        }
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        switch (ms.getSqlCommandType()) {
            case INSERT:
            case DELETE:
                // 插入、硬删除数据时触发
                if (parameter instanceof Entity) {
                    publishEntityEvents((Entity) parameter);
                }
                break;
            case UPDATE:
                // 修改或逻辑删除时触发
                if (parameter instanceof Entity) { // 逻辑删除触发
                    publishEntityEvents((Entity) parameter);
                }
                if (parameter instanceof MapperMethod.ParamMap<?>) {
                    MapperMethod.ParamMap<?> params = (MapperMethod.ParamMap<?>) parameter;
                    Object entityObject = params.getOrDefault(Constants.ENTITY, null);
                    if (entityObject instanceof Entity) {
                        Entity entity = (Entity) entityObject;
                        publishEntityEvents(entity);
                    }
                }
                break;
        }
        return proceed;
    }

    private void publishEntityEvents(Entity entity) {
        for (Object localEvent : entity.getLocalEvents()) {
            eventPublisher.publishEvent(localEvent);
        }
        entity.clearLocalEvents();
    }
}
