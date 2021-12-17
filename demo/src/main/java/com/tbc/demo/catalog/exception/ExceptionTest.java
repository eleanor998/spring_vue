package com.tbc.demo.catalog.exception;

/**
 * @Classname ExceptionTest
 * @Description 测试执行顺序
 * @Date 2021/11/25 下午6:07
 * @Created by ihr
 */
public class ExceptionTest {

    public static void main(String[] args) {
        try {
            Integer a = 1 * 0;
        } catch (java.lang.Exception e) {
            System.out.println("catch");
            throw e;
        } finally {
            System.out.println("finally");
        }
    }

}
