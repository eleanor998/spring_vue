package com.tbc.demo.catalog.syn;

import com.tbc.demo.entity.User;
import com.tbc.demo.utils.SpringUtil;
import org.springframework.stereotype.Service;

@Service
public class Test1 implements Test {


    public void test1(User user) {
        System.out.println(user);
    }
}
