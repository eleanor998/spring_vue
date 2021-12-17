package com.tbc.demo.catalog.asynchronization;


import com.tbc.demo.catalog.asynchronization.model.User;

/**
 * @Classname SubUser
 * @Description TODO
 * @Date 2021/6/10 15:51
 * @Created by gekang
 */
public class SubUser extends User {

    public String getUsername() {
        System.out.println("这个是重写后的方法");
        return "这个是重写后的方法";
    }
}
