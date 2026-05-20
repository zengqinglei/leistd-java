package com.mysoft.permission.user.event;

import com.mysoft.permission.user.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    public UserCreatedEvent(User user) {
        super(user);
    }

    @Override
    public User getSource() {
        return (User) super.getSource();
    }
}
