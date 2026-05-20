package com.mysoft.leistd.mdc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.MDC;

@Slf4j
public class MdcProviderTest {

    @Test
    public void testScopeMdc() {
        String key = "key1";
        try (MdcProvider mdcProvider = new MdcProvider()) {
            mdcProvider.add("key1", "value1");
            Assert.assertEquals("value1", MDC.get(key));
        }
        Assert.assertNull(MDC.get(key));
    }
}
