package com.mysoft.leistd.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

public class ApplicationContextHelperTest {

    @Test
    public void testGetObjectMapper() {
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        ObjectMapper oldObjectMapper = new JsonConfig().registerObjectMapper();
        Mockito.when(applicationContext.getBean(ObjectMapper.class)).thenReturn(oldObjectMapper);

        ApplicationContextHelper helper = new ApplicationContextHelper();
        helper.setApplicationContext(applicationContext);

        ObjectMapper newObjectMapper = helper.getRequiredBean(ObjectMapper.class);
        Assert.assertEquals(oldObjectMapper, newObjectMapper);
    }

    static class JsonConfig {
        public ObjectMapper registerObjectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();//可解决时间格式等一系列问题
            return objectMapper;
        }
    }

}
