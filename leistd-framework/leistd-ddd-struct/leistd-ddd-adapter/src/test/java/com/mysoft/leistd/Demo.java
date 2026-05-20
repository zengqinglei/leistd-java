package com.mysoft.leistd;

import com.mysoft.leistd.dto.model.DataResponse;
import com.mysoft.leistd.dto.model.Response;
import com.mysoft.leistd.exception.BadRequestException;
import com.mysoft.leistd.exception.CommonException;
import com.mysoft.leistd.exception.InternalServerErrorException;
import com.mysoft.leistd.response.ResponseWrapper;
import com.mysoft.leistd.response.ResponseWrapperBodyAdvice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
@ResponseWrapper
public class Demo implements ApplicationContextAware {
    ApplicationContext applicationContext;

    public void doSomething() {
        System.out.println("Doing something");
        ResponseWrapperBodyAdvice catchAndLog = applicationContext.getBean(ResponseWrapperBodyAdvice.class);
        System.out.println(catchAndLog);
        doSomethingInner();
    }

    private void doSomethingInner() {
        System.out.println("doSomethingInner");
    }

    public DemoResponse execute(Request request) {
        System.out.println("executing request");
        return new DemoResponse(request.name, true);
    }

    public DemoResponse executeWithExceptionAndDemoResponse() {
        throw new CommonException("executeWithExceptionAndDemoResponse");
    }

    public Response executeWithResponse(Request request) {
        DemoResponse demoResponse = new DemoResponse("Jack Ma", true);
        return DataResponse.success(demoResponse);
    }

    public Response executeWithExceptionAndResponse() {
        if (true) {
            throw new CommonException("execute With Exception And Response");
        }
        return null;
    }

    public void executeWithVoid() {
        System.out.println("execute with void");
    }

    public void executeWithExceptionAndVoid() {
        throw new BadRequestException("execute With Exception And Void");
    }

    public Response executeWithBizExceptionAndResponse() {
        throw new InternalServerErrorException("execute With BizException And Response");
    }

    public Response executeWithSysExceptionAndResponse() {
        throw new InternalServerErrorException("execute With SysException And Response");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static class Request {
        public String name;
        public int age;
    }

    public static class DemoResponse extends Response {
        final String name;
        final boolean isSuccess;

        public DemoResponse(String name, boolean isSuccess) {
            this.name = name;
            this.isSuccess = isSuccess;
        }
    }
}
