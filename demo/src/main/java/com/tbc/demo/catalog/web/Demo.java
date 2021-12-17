package com.tbc.demo.catalog.web;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 注释
 *
 * @author gekangkang
 * @date 2019/8/23 13:46
 */
@Slf4j
@RequestMapping("demo")
@Controller
public class Demo {

    @Value("${spring.application.name}")
    private String test;

    @RequestMapping("demo")
    @ResponseBody
    public String from(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return test;
    }

    @Test
    public void test() {
        DateTime dateTime = DateUtil.beginOfDay(new Date());
        System.out.println(dateTime);
    }

    private List<List<User>> getUserlist() {
        List<List<User>> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> objects1 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                User user = new User();
                objects1.add(user);
            }
            objects.add(objects1);
        }
        return objects;
    }


    class CGlibProxyUser implements MethodInterceptor {

        private Object targetObject;

        //获取代理理对象,绑定代理
        public <T> T newProxyInstance(Object targetObject) {
            this.targetObject = targetObject;
            return (T) Enhancer.create(targetObject.getClass(), this);
        }

        /**
         * 重写需要代理的代理方法
         *
         * @param o
         * @param method
         * @param objects
         * @param methodProxy
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object invoke = null;
            if (method.getName().equals("getUsername")) {
                System.out.println("CGLib 动态代理开始");
                invoke = methodProxy.invokeSuper(o, objects);
                System.out.println("CGLib 动态代理结束");
            }

            return invoke;
        }


    }
}