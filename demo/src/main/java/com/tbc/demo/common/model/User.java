package com.tbc.demo.common.model;

import lombok.Data;


@Data
public class User extends com.tbc.demo.catalog.asynchronization.model.User implements Cloneable {

    protected String username;

    protected Integer age1;


    public User() {
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age1 = age;
    }

    @Override
    public Object clone() {
        User stu = null;
        try {
            stu = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
