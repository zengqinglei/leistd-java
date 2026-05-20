package com.mysoft.leistd;

import com.mysoft.leistd.dto.model.Response;
import org.junit.Assert;
import org.junit.Test;

public class ResponseTest {
    @Test
    public void testResponseSuccess() {
        Response response = Response.success();
        Assert.assertEquals(0, response.getCode());
    }

    @Test
    public void testResponseFailure() {
        int failureCode = 40000;
        String failureMessage = "客户信息已存在";
        Response response = Response.failure(failureCode, failureMessage);
        Assert.assertEquals(failureCode, response.getCode());
        Assert.assertEquals(failureMessage, response.getMessage());
    }

    @Test
    public void testResponseCustom() {
        int code = 0;
        String message = "操作成功";
        String details = "操作成功详情";
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        response.setDetails(details);

        Assert.assertEquals(code, response.getCode());
        Assert.assertEquals(message, response.getMessage());
        Assert.assertEquals(details, response.getDetails());
    }
}
