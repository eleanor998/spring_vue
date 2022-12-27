package com.tbc.demo.catalog.jedis;


import cn.hutool.core.util.ZipUtil;
import com.google.common.collect.Lists;
import com.tbc.demo.catalog.asynchronization.model.User;
import com.tbc.demo.catalog.zip.TestString;
import com.tbc.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.portable.OutputStream;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.ProtocolCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 注释
 *
 * @author gekangkang
 * @date 2019/10/31 14:04
 */
@Slf4j
public class jedis {
    private static Jedis jedis = RedisUtils.getJedis();
    private static Pattern regex = Pattern.compile("(?<=used_memory_rss:)\\d*");

    public static void main(String[] args) throws InterruptedException {
        List<User> menuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.mockDate();
        }
        Map<Integer, List<User>> collect = menuList.stream().collect(Collectors.toMap(User::getAge, item -> Lists.newArrayList(item), (List<User> newValueList, List<User> oldValueList) -> {
            oldValueList.addAll(newValueList);
            return oldValueList;
        }));
    }


    /**
     * 测试Hash (map)类型
     */
    @Test
    public void hashTest() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                jedis.hset("test1", "test" + i, "1" + j);
            }
        }
        Set<String> test1 = jedis.hkeys("test1");
        for (String s : test1) {
            String test11 = jedis.hget("test1", s);
            System.out.println(test11);

        }
        System.out.println(test1);
    }

    /**
     * 测试rpush插入顺序与获取顺序
     */
    @Test
    public void listTest() {
        for (int i = 0; i < 10; i++) {
            jedis.rpush("test3", "" + i);
        }
        System.out.println(jedis.llen("test3"));
    }

    //序列化
    private static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] getByte = byteArrayOutputStream.toByteArray();
            return getByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    private static Object unserizlize(byte[] binaryByte) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getList() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("172.21.0.19", 16383));
        nodes.add(new HostAndPort("172.21.0.19", 16384));
        JedisCluster cluster = new JedisCluster(nodes);
        List<String> list = getList();
        return list;
    }

    /**
     * 使用当中如果不，手动释放连接redis不会回收空闲连接？
     */
    @Test
    public void tsetJedisPool() throws InterruptedException {
        GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(30);
        //连接池中的最大空闲连接
        jedisPoolConfig.setMaxIdle(1);
        //连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(0);
        //连接池最大阻塞等待时间
        jedisPoolConfig.setMaxWaitMillis(100);
        //设置最小可回收时间
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(0);
        //最小可回收时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(0);
        //设置回收间隔
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
        System.out.println("活跃数" + jedisPool.getNumActive());
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            new Thread(() -> {
                jedisPool.getResource();
            }).start();
            System.out.println("活跃数" + jedisPool.getNumActive());
        }
    }

    /**
     * 测试一个连接多个线程执行任务，会出现 Unexpected end of stream  报错的问题
     */
    @Test
    public void tset1() throws InterruptedException {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        System.out.println("活跃数" + jedisPool.getNumActive());
        Jedis resource = jedisPool.getResource();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                Jedis jedis = jedisPool.getResource();
                jedis.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            });
            System.out.println("活跃数" + jedisPool.getNumActive());
        }
    }

    /**
     * 测试数据储存占用空间
     */
    @Test
    public void tset2() {
        Jedis jedis = new JedisPool("127.0.0.1", 6379).getResource();
        jedis.set("test", TestString.content);
        jedis.set("test1".getBytes(), TestString.content.getBytes(StandardCharsets.UTF_8));
        byte[] gzip = ZipUtil.gzip(TestString.content, StandardCharsets.UTF_8.name());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File zip = ZipUtil.zip(TestString.content);
        jedis.set("test2".getBytes(), gzip);
    }


    /**
     * Jedis 统计api
     */
    @Test
    public void tset3() {
        Jedis jedis = new JedisPool("127.0.0.1", 6379).getResource();
        for (int i = 0; i < 100; i++) {
            jedis.pfadd("user1", "a" + i);
            if (i % 5 == 0) {
                jedis.pfadd("user","a");
            }
        }
        System.out.println(jedis.pfcount("user1"));
        System.out.println(jedis.pfcount("user"));

    }


}
