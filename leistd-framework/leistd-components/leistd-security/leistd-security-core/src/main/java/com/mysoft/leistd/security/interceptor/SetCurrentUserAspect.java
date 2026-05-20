package com.mysoft.leistd.security.interceptor;

import com.mysoft.leistd.exception.CommonException;
import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.ClaimTypes;
import com.mysoft.leistd.security.ClaimsPrincipal;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;
import com.mysoft.leistd.security.mdc.SecurityLogMdc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置当前用户拦截器（一般用于接口传递用户信息或调度任务、异步任务等后台线程的方法进行配置用户信息）
 */
@Component
@Aspect
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class SetCurrentUserAspect {
    final CurrentPrincipalAccessor currentPrincipalAccessor;
    final SecurityLogMdc securityLogMdc;
    final SpelExpressionParser parser = new SpelExpressionParser();
    final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around(value = "@annotation(setCurrentUser)")
    public Object doAround(ProceedingJoinPoint joinPoint, SetCurrentUser setCurrentUser) throws Throwable {
        Object target = joinPoint.getTarget();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        // 解析el表达式
        EvaluationContext context = new MethodBasedEvaluationContext(target, method, args, parameterNameDiscoverer);
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimTypes.UserId, getValue(context, setCurrentUser.userId()));
        claims.put(ClaimTypes.UserName, getValue(context, setCurrentUser.username()));
        claims.put(ClaimTypes.Name, getValue(context, setCurrentUser.name()));
        claims.put(ClaimTypes.PhoneNumber, getValue(context, setCurrentUser.phoneNumber()));
        claims.put(ClaimTypes.Email, getValue(context, setCurrentUser.email()));
        ClaimsPrincipal currentPrincipal = new ClaimsPrincipal(claims);
        try (AutoCloseable ignore = currentPrincipalAccessor.change(currentPrincipal); MdcProvider mdcProvider = new MdcProvider()) {
            securityLogMdc.addUser(mdcProvider);
            return joinPoint.proceed();
        }
    }

    private String getValue(EvaluationContext context, String key) {
        // 检查是否符合 #{} 格式，如果是，则替换为 #
        if (!key.matches("#\\{[^}]+}")) {
            throw new CommonException(MessageFormat.format("{0} 不符合规范", key));
        }
        String el = key.replaceAll("#\\{([^}]+)}", "#$1");
        Expression expression = parser.parseExpression(el);
        Object value = expression.getValue(context);
        return value == null ? null : value.toString();
    }
}
