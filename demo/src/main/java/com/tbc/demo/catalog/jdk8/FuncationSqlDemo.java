package com.tbc.demo.catalog.jdk8;



import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FuncationSqlDemo {

    /**
     * 1.函数式接口调用测试
     */
    @Test
    public void funcationDemo1() {
        //1.创建匿名对象,调用接口默认的抽象方法,接口的类型与参数类型根据后面的接口对象来判断
        FuncationNal<String, Integer> converter = (form) -> String.valueOf(form);
        String a = converter.convert(9999);
        System.out.println(a);
    }


    /**
     * 2. ::符号测试
     * 1.类名::方法名
     * 2.用::提取的函数，最主要的区别在于静态与非静态方法，非静态方法比静态方法多一个参数，就是被调用的实例。
     */

    @Test
    public void test() {
        FuncationNal<String, Integer> converter = String::valueOf;
    }

    /**
     * 3.内置函数接口
     * Predicate: 是一个可以指定入参类型，并返回 boolean 值的函数式接口。
     * 1.   test:方法接受一个参数,判断这个参数是否符合test()方法体里面的判断
     * Function:函数式接口的作用是，我们可以为其提供一个原料，他给生产一个最终的产品。通过它提供的默认方法，组合,链行处理(compose, andThen)：
     *
     * Optional: Optional 它不是一个函数式接口，设计它的目的是为了防止空指针异常（NullPointerException）
     *      让我们来快速了解一下 Optional 要如何使用！你可以将 Optional 看做是包装对象（可能是 null, 也有可能非 null）的容器。当你定义了一个方法，这个方法返回的对象可能是空，也有可能非空的时候，你就可以考虑用 Optional 来包装它，这也是在 Java 8 被推荐使用的做法。
     *Consumer : 消费者
     */
    @Test
    public void test1() {
        //1.Predicate: 后方校验规则可以自定义
        Predicate<String> predicate = String::isEmpty;
        Boolean a = predicate.test("1111");
        System.out.println("校验是否为空:" + a);

        //2.Function: 类似工厂模式的作用
        Function<String, Integer> toInteger = Integer::valueOf;
        Integer num = toInteger.apply("123");

        //3.Optional :建议用来包装对象中可能为空的参数
        String userName = "张三";
        Optional<String> optional = Optional.of("userName");
        System.out.println(optional.get());
        System.out.println(optional.isPresent());
        System.out.println(optional.equals("李四"));

        //4.Consumer

    }
}