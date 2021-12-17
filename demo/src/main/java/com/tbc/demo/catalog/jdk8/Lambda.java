package com.tbc.demo.catalog.jdk8;

import com.tbc.demo.catalog.asynchronization.model.User;
import org.junit.jupiter.api.Test;

import java.util.*;


import static java.util.stream.Collectors.toList;

/**
 * lambda 表达式测试
 * lambda运算符：所有的lambda表达式都是用新的lambda运算符 " => ",可以叫他，“转到”或者 “成为”。运算符将表达式分为两部分，左边指定输入参数，右边是lambda的主体。
 */
public class Lambda {

    /**
     * lambada语法规则
     * <p>
     * 1. lambda表达式的完整形态：有输入参数，有返回值，有代码块。
     * (int a, int b) -> {
     * int c = a + b;
     * return c;
     * }
     * 2. lambda表达式在某些情况也可以省略一部分
     * //2.1 当代码块不需要返回值的 时候可以省略return语句
     * (int a, int b) -> {
     * int c = a + b;
     * }
     * //2.2 当代码块只有一句的时候可以省略大括号
     * //并且这种情况默认返回这一句代码的执行结果
     * (int a, int b) -> a + b; // 同1具有同样的作用
     * //2.3 形参的类型可以根据函数式接口的里面的方法声明自行推断
     * //可以省略形参类型
     * (a, b) -> a + b; // 同1，2.2具有同样的效果
     * //2.4 当只有一个参数的时候可以省略，形参的小括号
     * a -> a * a;
     * 3.双冒号
     * list.forEach(File::getName); // 使用双冒号传递一个函数进来,
     * list.forEach( file -> file.getName()); // 使用正常的lambda表达式
     */
    private static List<User> list = getUserList();

    /**
     * 循环遍历
     */
    @Test
    public void testForEach() {
        list.forEach(user -> System.out.println(user));
    }


    /**
     * 测试排序 根据age排序
     */
    @Test
    public void testSort() {
        // :: 关键字来访问类的构造方法，对象方法，静态方法,只能是类内部的方法
        list.stream().sorted(Comparator.comparing(User::getAge)).forEach(user -> System.out.println(user));
    }


    /**
     * 过滤器 过滤 age小于20的人
     */

    @Test
    public void testFilter() {
        list.stream().filter((User user) -> user.getAge() > 20).forEach(user -> System.out.println(user));
    }

    /**
     * limit 截断
     */

    @Test
    public void testLimit() {
        list.stream().filter((User user) -> user.getAge() > 20).forEach(user -> System.out.println(user));
        list.stream().limit(3).forEach(user -> System.out.println(user));
    }


    /**
     * distinct 过滤重复
     */

    @Test
    public void testDistinct() {
        list.stream().distinct().forEach(user -> user.getAge());
    }

    /**
     * 测试计算.
     */
    @Test
    public void testNum() {
        IntSummaryStatistics num = list.stream().mapToInt(u -> u.getAge())
                .summaryStatistics();
        System.out.println("总共人数：" + num.getCount());
        System.out.println("平均年龄：" + num.getAverage());
        System.out.println("最大年龄：" + num.getMax());
        System.out.println("最小年龄：" + num.getMin());
        System.out.println("年龄之和：" + num.getSum());
    }


    /**
     * map 映射. 接收一个方法作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
     */
    @Test
    public void testMap() {
        // 只输出所有人的年龄
        list.stream().forEach(user -> System.out.println(user));
        System.out.println("映射后----->");
        List<Integer> ages = list.stream().map(user -> user.getAge()).collect(toList());
        ages.forEach(age -> System.out.println(age));

        // 小写转大写
        List<String> words = Arrays.asList("aaa", "vvvv", "cccc");
        System.out.println("全部大写---->");
        List<String> collect = words.stream().map(s -> s.toUpperCase()).collect(toList());
        collect.forEach(s -> System.out.println(s));
    }

    private static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(User.mockDate());
        }
        return users;
    }


    public static void main(String[] args) {
        list.stream().filter(user -> user.getUserName().equals("张1")).forEach(user -> System.out.println(user.getUserName()));
    }
}
