package com.mysoft.leistd.exception;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class ExceptionTest {
    @Test
    public void testExceptionToString() {
        BusinessException bizException = new BadRequestException("单元测试异常");
        System.out.println(bizException.toString());
        Assert.assertNotNull(bizException.toString());
    }
}
