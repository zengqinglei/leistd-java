package com.mysoft.leistd.dstblock.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }
}
