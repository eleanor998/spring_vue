package com.tbc.demo.catalog.jvm;

import com.tbc.demo.catalog.asynchronization.model.User;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 新生代回收算法
 */
@Slf4j
public class JVMGC2 {
    public static Users users = new Users();
    /**
     * JVM 参数 1:
     * `        `-Xms5m -Xmx5m -Xmn4m -XX:SurvivorRatio=5 -XX:+PrintGC  -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC
     * `        `使用以上参数初步怀疑,由于内存太小导致jdk运行的一些基本基本对象占用survivor区域从而进入老年区
     * JVM 参数 2:
     * `        `-Xms32m -Xmx32m -Xmn16m -XX:SurvivorRatio=8  -XX:+PrintGC  -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC
     * `        `在扩大了基础的内存之后发现明显无论怎么增加循环的次数都没有再出现Full GC 的情况!
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(gcTest());

        }
    }

    public static byte[] gcTest() {
        byte[] bytes = new byte[20*1024*1024];
        return bytes;
    }


    @Test
    public void test() {
        System.out.println(ObjectSizeCalculator.getObjectSize(gcTest()));
        System.out.println(ObjectSizeCalculator.getObjectSize(new JVMGC2()));
    }
}
class Users {
    public static User user = new User();
}