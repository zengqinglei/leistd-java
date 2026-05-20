package com.mysoft.leistd.dstblock.service;

import com.mysoft.leistd.dstblock.entity.User;
import com.mysoft.leistd.dstblock.interceptor.DistributedLock;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class JobDemoService {
    @DistributedLock(key = "test_job_lock_#{id}_#{user.name}")
    public void execute(Long id, User user) {
        System.out.println(MessageFormat.format("Job Demo:{0}", id));
    }
}
