package com.tbc.demo.catalog.yingyong;

import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname YinYong测试
 * @Description 测试引用对象
 * @Date 2021/9/18 下午6:58
 * @Created by ihr
 */
@Slf4j
public class YinYong {
    /**
     * 测试对象引用 ， 测试集合引用 ， 测试for循环修改对象
     */
    @Test
    public void test() {
        User user = new User();
        user.setAge(999);
        List<User> users = new ArrayList<>();
        users.add(user);
        User user1 = users.get(0);
        for (User user2 : users) {
            user2.setAge(9);
            log.info("循环直接修改是否能够影响集合内的值 ： [{}]", 9 == users.get(0).getAge());
        }
        log.info("对象传入list后 list与原有的对象是否还有关系： [{}]", user.getAge() == 9);
        user1.setAge(888);
        log.info("从list内获取后与集合内对象是否还有关系： [{}]", user1.getAge() == users.get(0).getAge());
    }
}
