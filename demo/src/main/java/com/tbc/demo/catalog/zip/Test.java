package com.tbc.demo.catalog.zip;

import com.tbc.demo.entity.User;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        User user = new User();
        user.setNickname("张三");
        User user1 = new User();
        user1.setNickname("张三");
        List<User> users = Arrays.asList(user, user1);
        System.out.println(users.stream().map(User::getNickname).distinct().count());
    }
}
