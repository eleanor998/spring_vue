package com.tbc.demo.catalog.asynchronization.utils;

import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.common.model.User;
import com.tbc.demo.utils.AutoCallable;
import com.tbc.demo.utils.ThreadsUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
public class Demo {

    public static void main(String[] args) throws Exception {
        List<Object> objects = new ArrayList<>();
        User user = new User();
        user.setUsername("asdf ");
        objects.add(user);
        String s = JSONObject.toJSONString(objects);
        List<Object> demo1 = JSONObject.parseObject(s, objects.getClass());
        System.out.println(demo1);
    }


    /**
     * 测试两个线程是否会并发执行
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        ThreadsUtils.autoExecute(new Demo(), "test3");
        ThreadsUtils.autoExecute(new Demo(), "test4");
        Thread.sleep(10000);
    }


    /**
     * 测试 JSON 与 list 之间的转换
     */
    @Test
    public void JSONTest() {
        String s = JSONObject.toJSONString(getUsers());
        log.info("把user集合转成JSON : {}", s);
        //如果是集请使用parseArray(args,集合对象内的class);如果传入的是list解析出来的里面全是jsonobject
        List<User> list = JSONObject.parseObject(s, List.class);
        log.info("把String 集合转成List : {}", list);
    }

    /**
     * 测试 JSON 与 list 之间的转换
     */
    @Test
    public void jedisGetToList() throws Exception {
        //测试结果为集合的对象可以直接强转成功
        List<User> getUsers1 = (List<User>) AutoCallable.execute(new Demo(), "getUsers");
        log.info("{}", getUsers1);
    }


    public List<User> getUsers() {
        User user = new User();
        User user1 = new User();
        user.setUsername("张三");
        user.setAge(12);
        List<User> users = Arrays.asList(user, user1);
        return users;
    }


    public void test3() {
        for (int i = 0; i < 10; i++) {
            log.info("线程1_________" + i);
        }
    }

    public void test4() {
        for (int i = 0; i < 10000000; i++) {
            log.info("线程2_________" + i);
        }
    }

    /**
     * 1. 自动执行参数转换有问题测试,
     */
    @Test
    public void testInsertParam() throws Exception {
        User user = new User();
        user.setUsername("张三");
        Future<String> resutl = ThreadsUtils.autoExecute(new Demo(), "testParams", "参数1_", "参数2_", user);
        log.info(resutl.get());
    }

    public String testParams(String str, String str2, com.tbc.demo.catalog.asynchronization.model.User user) {
        return str + str2 + user.getUserName();
    }


}
