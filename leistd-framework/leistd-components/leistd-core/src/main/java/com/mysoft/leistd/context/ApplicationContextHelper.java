package com.mysoft.leistd.context;

import com.mysoft.leistd.exception.CommonException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Getter
@Component("leistdApplicationContextHelper")
@Slf4j
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    private static <T> T getBean(Class<T> targetClz, boolean required) {
        T beanInstance = null;
        //优先按type查
        try {
            beanInstance = (T) applicationContext.getBean(targetClz);
        } catch (Exception e) {
            String error = MessageFormat.format(
                    "No bean found for {0} by type",
                    targetClz.getCanonicalName());
            log.debug(error, e);
        }

        //按name查
        try {
            if (beanInstance == null) {
                String simpleName = targetClz.getSimpleName();
                //首字母小写
                simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
                beanInstance = (T) applicationContext.getBean(simpleName);
            }
        } catch (Exception e) {
            String error = MessageFormat.format(
                    "No bean found for {0} by name",
                    targetClz.getCanonicalName());
            if (required) {
                throw new CommonException(error, e);
            } else {
                log.debug(error, e);
            }
        }
        return beanInstance;
    }

    public static <T> T getBean(Class<T> targetClz) {
        return getBean(targetClz, false);
    }

    public static <T> T getRequiredBean(Class<T> targetClz) {
        return getBean(targetClz, true);
    }

    public static Object getBean(String claz) {
        return ApplicationContextHelper.applicationContext.getBean(claz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return ApplicationContextHelper.applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... params) {
        return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
    }

}
