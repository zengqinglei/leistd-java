package com.mysoft.leistd.exception;

import com.mysoft.leistd.dto.model.Response;
import com.mysoft.leistd.runtime.EnvRuntime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认异常处理实现
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultExceptionHandler implements ExceptionHandler {
    final GlobalExceptionProps globalExceptionProps;
    final EnvRuntime envRuntime;

    @Override
    public ResponseEntity<Object> handle(Throwable e, HttpServletRequest request) throws Throwable {
        Boolean wrapperFailureRes = (Boolean) request.getAttribute(GlobalExceptionFilter.IS_WRAPPER_FAILURE_RES_KEY);
        // 重新组织异常
        BusinessException bizException;
        if (e instanceof BusinessException) {
            bizException = (BusinessException) e;
        } else if (e instanceof BindException) {
            bizException = new UnprocessableEntityException(
                    getErrorsFromBindException((BindException) e),
                    "输入的信息有误");
        } else if (e instanceof ConstraintViolationException) {
            bizException = new UnprocessableEntityException(
                    getErrorsFromConstraintViolationException((ConstraintViolationException) e),
                    "输入的信息有误");
        } else if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            bizException = new BadRequestException(
                    commonException.getCause(),
                    commonException.getMessage()
            ).details(hasOverriddenToString(e.getClass()) && commonException.getCause() == null ? commonException.toString() : null);
        } else {
            bizException = new InternalServerErrorException(e, "系统异常，请联系管理员");
        }
        if (bizException instanceof InternalServerErrorException) {
            if (!wrapperFailureRes) {
                log.error(MessageFormat.format("UNKNOWN EXCEPTION: {0}", e.getMessage()), e);
                throw e;
            } else {
                log.error(bizException.toString(), bizException);
            }
        } else {
            if (!wrapperFailureRes) {
                String errorMessage = MessageFormat.format("EXCEPTION: {0}", e.getMessage());
                if (e.getCause() != null) {
                    log.warn(errorMessage, e);
                } else {
                    log.warn(errorMessage);
                }
                throw e;
            } else {
                if (e.getCause() != null) {
                    log.warn(bizException.toString(), bizException);
                } else {
                    log.warn(bizException.toString());
                }
            }
        }

        // 统一返回结果
        Response error = Response.failure(bizException.getCode(), bizException.getMessage());
        if (isShowDetails()) {
            error.setDetails(bizException.getDetails() == null ? bizException.getStackTraceStr() : bizException.getDetails());
        } else {
            error.setDetails(bizException.getDetails());
        }
        if (bizException instanceof UnprocessableEntityException) {
            UnprocessableEntityException unprocessableEntityException = (UnprocessableEntityException) bizException;
            error.setErrors(unprocessableEntityException.getErrors());
        }
        HttpStatus httpStatus = null;
        String errorCode = String.valueOf(bizException.getCode());
        if (errorCode.length() > 3) {
            int httpStatusCode = Integer.parseInt(errorCode.substring(0, 3));
            httpStatus = HttpStatus.resolve(httpStatusCode);
        }
        return new ResponseEntity<>(error, httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus);
    }

    private boolean isShowDetails() {
        if (globalExceptionProps.getIsShowDetails() == Boolean.TRUE) {
            return true;
        }
        if (globalExceptionProps.getIsShowDetails() == Boolean.FALSE) {
            return false;
        }
        return envRuntime.isDevelopment();
    }

    private List<Map<String, String>> getErrorsFromBindException(BindException bindException) {
        List<Map<String, String>> errors = new ArrayList<>();
        bindException.getAllErrors().forEach(objectError -> {
            Map<String, String> error = new HashMap<>();
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
            } else {
                error.put("field", objectError.getObjectName());
                error.put("message", objectError.getDefaultMessage());
            }
            errors.add(error);
        });
        return errors;

    }

    private List<Map<String, String>> getErrorsFromConstraintViolationException(ConstraintViolationException constraintViolationException) {
        List<Map<String, String>> errors = new ArrayList<>();
        // e.getMessage() 的格式为 getUser.id: id不能为空, getUser.name: name不能为空
        String[] errorMessages = constraintViolationException.getMessage().split(", ");
        for (String errorMessage : errorMessages) {
            String[] fieldAndMsg = errorMessage.split(": ");
            String field = fieldAndMsg[0].split("\\.")[1];
            String message = fieldAndMsg[1];

            Map<String, String> error = new HashMap<>();
            error.put("field", field);
            error.put("message", message);
            errors.add(error);
        }
        return errors;
    }

    private boolean hasOverriddenToString(Class<?> clazz) {
        try {
            // 获取类中的 toString 方法
            Method toStringMethod = clazz.getMethod("toString");
            // 检查该方法是否由当前类声明
            return toStringMethod.getDeclaringClass() != Object.class;
        } catch (NoSuchMethodException e) {
            // 如果类中没有 toString 方法（理论上不可能发生），直接返回 false
            return false;
        }
    }
}
