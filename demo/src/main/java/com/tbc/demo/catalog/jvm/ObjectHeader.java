package com.tbc.demo.catalog.jvm;

import com.tbc.demo.catalog.asynchronization.model.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * 打印对象头信息
 */
public class ObjectHeader {

    public static void main(String[] args) {
        User[] user = new User[10];
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        synchronized (user) {
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
    }
}
