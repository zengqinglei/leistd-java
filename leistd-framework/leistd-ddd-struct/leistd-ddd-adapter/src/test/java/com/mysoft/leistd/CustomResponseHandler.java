package com.mysoft.leistd;

import com.mysoft.leistd.response.ResponseHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomResponseHandler implements ResponseHandler {


    @Override
    public Object handle(Class returnType, Object data) {
        System.out.println("==== This is Customized Response handler");
        return new Demo.DemoResponse("demo-response", true);
    }
}
