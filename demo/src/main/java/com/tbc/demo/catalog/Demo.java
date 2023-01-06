package com.tbc.demo.catalog;

import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Demo {


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.setUserName("12133");
        String str = "123";
        Integer i = 123;
        test(user, str, i);
    }

    public static void test(Object... objs) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = Demo.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("test1")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                List objects = new ArrayList<>();
                Object invoke = method.invoke(new Demo(), objs);


            }
        }
    }

    public static void test1(User user, String str, Integer integer) {
        System.out.println(user.getUserName());
        System.out.println(user);
        System.out.println(str);
        System.out.println(integer);
    }

}



