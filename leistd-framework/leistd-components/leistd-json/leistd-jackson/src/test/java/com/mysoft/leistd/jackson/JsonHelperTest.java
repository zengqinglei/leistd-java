package com.mysoft.leistd.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json序列化测试
 */
public class JsonHelperTest {

    @Test
    public void testSerializeMap() {
        HashMap<String, Object> keyValues = new HashMap<>();
        keyValues.put("key1", "value1");
        keyValues.put("key2", 2);
        String curJsonString = JsonHelper.toJSONString(keyValues);
        String targetJsonString = "{\"key1\":\"value1\",\"key2\":2}";
        Assert.assertEquals(curJsonString, targetJsonString);
    }

    @Test
    public void testDeserializeMap() {
        String jsonString = "{\"key1\":\"value1\",\"key2\":2}";
        Map<String, Object> keyValues = JsonHelper.parseObject(
                jsonString,
                new TypeReference<>() {
                });
        Assert.assertEquals(keyValues.get("key1"), "value1");
        Assert.assertEquals(keyValues.get("key2"), 2);
    }

    @Test
    public void testSerializeCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(new CustomerDTO("3192c38b-c923-417a-bf1d-213b551784c9", "测试客户1"));
        customers.add(new CustomerDTO("e8f5a534-bfef-449e-b159-cbbfcfb74b74", "测试客户2"));
        String customersJsonString = JsonHelper.toJSONString(customers);
        String targetJsonString = "[{\"id\":\"3192c38b-c923-417a-bf1d-213b551784c9\",\"name\":\"测试客户1\"},{\"id\":\"e8f5a534-bfef-449e-b159-cbbfcfb74b74\",\"name\":\"测试客户2\"}]";
        Assert.assertEquals(customersJsonString, targetJsonString);
    }

    @Test
    public void testDeserializeCustomers() {
        String jsonString = "[{\"id\":\"3192c38b-c923-417a-bf1d-213b551784c9\",\"name\":\"测试客户1\"},{\"id\":\"e8f5a534-bfef-449e-b159-cbbfcfb74b74\",\"name\":\"测试客户2\"}]";
        List<CustomerDTO> customers = JsonHelper.parseObject(jsonString, new TypeReference<>() {
        });
        Assert.assertEquals(customers.getFirst().getId(), "3192c38b-c923-417a-bf1d-213b551784c9");
        Assert.assertEquals(customers.getFirst().getName(), "测试客户1");
    }

    @Test
    public void testDeserializeCustomerPage() {
        String jsonString = "{\"total\":10,\"items\":[{\"id\":\"3192c38b-c923-417a-bf1d-213b551784c9\",\"name\":\"测试客户1\"},{\"id\":\"e8f5a534-bfef-449e-b159-cbbfcfb74b74\",\"name\":\"测试客户2\"}]}";
        PageDTO<CustomerDTO> customerPage = JsonHelper.parseObject(jsonString, new TypeReference<>() {
        });
        Assert.assertTrue("客户总数应大于0", customerPage.getTotal() > 0);
        Assert.assertFalse("返回的客户集合不能为空", customerPage.getItems().isEmpty());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    static class CustomerDTO {
        private String id;
        private String name;
    }

    @Getter
    @Setter
    static class PageDTO<T> {
        private long total;
        private List<T> items;
    }
}
