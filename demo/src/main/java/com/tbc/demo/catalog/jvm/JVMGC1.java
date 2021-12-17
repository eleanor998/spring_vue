package com.tbc.demo.catalog.jvm;

import com.tbc.demo.catalog.asynchronization.model.User;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 引用类型
 */
public class JVMGC1 {

    //这个是GC Roots , 属于强引用
    public static User user = new User();
    //这里面的user对象是软引用
    public static SoftReference<User> userSoftReference = new SoftReference(new User());
    //虚引用
    public static WeakReference<User> userWeakReference = new WeakReference(new User());

    public static void main(String[] args) {
        gcTest1();
    }

    public static void gcTest1() {
        Users users = new Users();
    }

    public static void gcTest2() {
        ArrayList<Object> objects = new ArrayList<>();
        //这里面的user对象就是弱引用
        objects.add(new User());
    }
}
