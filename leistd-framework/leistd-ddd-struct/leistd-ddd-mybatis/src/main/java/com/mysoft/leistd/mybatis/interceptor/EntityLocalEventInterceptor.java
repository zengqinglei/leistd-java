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
            case INSERT, DELETE -> { // 插入、硬删除数据时触发
                if (parameter instanceof Entity entity) {
                    publishEntityEvents(entity);
                }
            }
            case UPDATE -> { // 修改或逻辑删除时触发
                if (parameter instanceof Entity entity) { // 逻辑删除触发
                    publishEntityEvents(entity);
                }
                if (parameter instanceof MapperMethod.ParamMap<?> params
                        && params.getOrDefault(Constants.ENTITY, null) instanceof Entity entity) {
                    // 修改触发
                    publishEntityEvents(entity);
                }
            }
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
