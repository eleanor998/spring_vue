package com.tbc.demo.catalog.jdk8;

import com.tbc.demo.common.model.User;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * stream 流演示
 */
public class StreamDemo {

    /**
     * 1.基基本stream流演示
     * 中间操作: 中间操作会再次返回一个stream可以继续链式编程
     * 中间操作:
     * Sorted(排序):
     * lists.stream().sorted().filter((s -> s.startsWith("a"))).forEach(System.out::println);
     * <p>
     * Filter(过滤器):
     * lists.stream().filter((s -> s.startsWith("a"))).forEach(System.out::println);
     * <p>
     * 映射(map):
     * lists.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);
     * 终止操作: 当调用了一个返回不会stream的时候就终止
     * 终止操作:
     * Match(匹配):
     * lists.stream().anyMatch((s -> s.startsWith("a")));
     * <p>
     * Collect(收集)
     * lists.stream().filter((p) -> p.startsWith("a")).sorted().collect(Collectors.toList());
     * <p>
     * count(计数)
     * lists.stream().filter((s -> s.startsWith("a"))).count();
     * <p>
     * reduce(规约)
     * reduce方法允许我们用自己的方式去计算元素或者将一个Stream中的元素以某种规律关联
     * lists.stream().sorted().reduce((s1, s2) -> {
     * System.out.println(s1 + "|" + s2);
     * return s1 + "|" + s2;
     * });
     */
    @Test
    public void baseDemo() {
        List<String> list = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        list.stream()//创建流
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)//转换
                .sorted()//排序
                .forEach(System.out::println);  //打印
        //规约
        Optional<String> reduced = list
                .stream()
                .sorted()
                .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
    }

    /**
     * 2.可以通过无需创建集合的方式来创建stream流
     */
    @Test
    public void streamOf() {
        Stream.of("a1", "a2", "a3", "a3")
                //设置过滤条件
                .filter(a -> a.startsWith("a3"))
                //找到第一个满足条件的对象
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * 3.Java 8还附带了一些特殊类型的流，用于处理原始数据类型int，long以及double。就是IntStream，LongStream还有DoubleStream。其中，IntStreams.range()方法还可以被用来取代常规的 for
     */
    @Test
    public void fori() {
        IntStream.range(1, 100).forEach(System.out::println);
    }

    /**
     * 4.stream流执行顺序
     */
    @Test
    public void order() {
        //1. stream的中间操作是有延迟性的 当且仅当存在终端操作时，中间操作操作才会被执行。
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .filter(s -> {
//                    System.out.println("filter: " + s);
//                    return true;
//                });
        //2.验证上述逻辑
/*        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));*/
        //3.
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase(); // 转大写
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A"); // 过滤出以 A 为前缀的元素
                });
    }

    /**
     * 5. Parallel-Streams 并行流 :stream: 流是支持顺序和并行的。顺序流操作是单线程操作，而并行流是通过多线程来处理的，能够充分利用物理机
     * 多核 CPU 的优势，同时处理速度将大大提高
     */

    @Test
    public void test1() {
        //1. 创建一个 集合用来测试
        int max = 10000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // 正常顺序流
        long t0 = System.currentTimeMillis();
        long count = values.stream().sorted().count();
        long t1 = System.currentTimeMillis();
        System.out.println(String.format("顺序流排序耗时: %d ms", t1 - t0));

        List<String> values1 = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values1.add(uuid.toString());
        }

        // 并行流
        long t2 = System.currentTimeMillis();
        long count1 = values1.parallelStream().sorted().count();
        long t3 = System.currentTimeMillis();
        System.out.println(String.format("并行流排序耗时: %d ms", t3 - t2));
    }

    /**
     * 6.Map 是不支持 Stream 流的，因为 Map 接口并没有像 Collection 接口那样，定义了 stream() 方法。但是，我们可以对其 key, values, entry 使用
     */
    @Test
    public void mapStream() {
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            // 与老版不同的是，putIfAbent() 方法在 put 之前，
            // 会判断 key 是否已经存在，存在则直接返回 value, 否则 put, 再返回 value
            map.putIfAbsent(i, "val" + i);
        }

        // forEach 可以很方便地对 map 进行遍历操作
        map.forEach((key, value) -> System.out.println(value));

        // computeIfPresent(), 当 key 存在时，才会做相关处理
        // 如下：对 key 为 3 的值，内部会先判断值是否存在，存在，则做 value + key 的拼接操作
        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        // 先判断 key 为 9 的元素是否存在，存在，则做删除操作
        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        // computeIfAbsent(), 当 key 不存在时，才会做相关处理
        // 如下：先判断 key 为 23 的元素是否存在，不存在，则添加
        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        // 先判断 key 为 3 的元素是否存在，存在，则不做任何处理
        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

        map.remove(3, "val3");
        map.get(3);             // val33

        map.remove(3, "val33");
        map.get(3);             // null

        // merge 方法，会先判断进行合并的 key 是否存在，不存在，则会添加元素
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9

        // 若 key 的元素存在，则对 value 执行拼接操作
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat
    }

    /**
     * 测试按照指定字段分组
     */
    @Test
    public void filterGroupBy() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("张三", 23));
        users.add(new User("张三1", 23));
        users.add(new User("张三2", 23));
        users.add(new User("张三3", 25));
        users.add(new User("张三4", 23));
        users.add(new User("张三5", 25));
        users.add(new User("张三6", 24));
        users.add(new User("张三7", 21));
        users.add(new User("张三8", 21));
        users.add(new User("张三9", 21));
        users.add(new User("张三10", 22));
        users.add(new User("张三11", 222));
        Map<Integer, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getAge));
        System.out.println(collect);
    }

    @Test
    public void getTestData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 30; j++) {

            }

        }
    }
}
