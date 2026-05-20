package com.mysoft.leistd.locallock.service;

import com.mysoft.leistd.locallock.entity.User;
import com.mysoft.leistd.locallock.interceptor.LocalLock;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class JobDemoService {
    @LocalLock(key = "test_job_lock_#{id}_#{user.name}")
    public void execute(Long id, User user) {
        System.out.println(MessageFormat.format("Job Demo:{0}", id));
    }
}
