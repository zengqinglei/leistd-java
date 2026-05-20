package com.mysoft.leistd.locallock.interceptor;

import com.mysoft.leistd.locallock.ILocalLock;
import com.mysoft.leistd.locallock.ILock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Method;

/**
 * 分布式锁拦截器（一般用于调度任务、异步任务等后台线程的方法）
 */
@Component
@Aspect
@Slf4j
@Order(3)
@RequiredArgsConstructor
public class LocalLockAspect {
    final ILocalLock localLock;
    final SpelExpressionParser parser = new SpelExpressionParser();
    final PropertyPlaceholderHelper defaultHelper = new PropertyPlaceholderHelper("#{", "}");
    final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around(value = "@annotation(localLockAttr)")
    public Object doAround(ProceedingJoinPoint joinPoint, LocalLock localLockAttr) throws Throwable {
        Object target = joinPoint.getTarget();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        // 解析el表达式
        EvaluationContext context = new MethodBasedEvaluationContext(target, method, args, parameterNameDiscoverer);
        String lockKey = defaultHelper.replacePlaceholders(localLockAttr.key(), placeholderName -> {
            Expression expression = parser.parseExpression("#" + placeholderName);
            return String.valueOf(expression.getValue(context));
        });
        if (StringUtils.isBlank(lockKey)) {
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            lockKey = className + "." + method.getName();
        }
        try (ILock ignore = localLock.lock(lockKey, localLockAttr.waitTime(), localLockAttr.timeUnit())) {
            return joinPoint.proceed();
        }
    }
}
