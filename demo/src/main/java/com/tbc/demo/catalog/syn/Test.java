package com.tbc.demo.catalog.syn;

import com.tbc.demo.entity.User;
import com.tbc.demo.utils.SpringUtil;
import org.springframework.scheduling.annotation.Async;

public interface Test {
    void test1(User user);

    @Async
    default void test(User user) {
        Test bean = SpringUtil.getBean(Test.class);
        System.out.println(user);
        bean.test1(user);
    }
}
