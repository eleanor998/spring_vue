package com.tbc.demo.catalog.jdk8;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tbc.demo.catalog.asynchronization.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Java 8通过发布新的Date-Time API (JSR 310)来进一步加强对日期与时间的处理。
 * <p>
 * 在旧版的 Java 中，日期时间 API 存在诸多问题，其中有：
 * <p>
 * 非线程安全 − java.util.Date 是非线程安全的，所有的日期类都是可变的，这是Java日期类最大的问题之一。
 * <p>
 * 设计很差 − Java的日期/时间类的定义并不一致，在java.util和java.sql的包中都有日期类，此外用于格式化和解析的类在java.text包中定义。java.util.Date同时包含日期和时间，而java.sql.Date仅包含日期，将其纳入java.sql包并不合理。另外这两个类都有相同的名字，这本身就是一个非常糟糕的设计。
 * <p>
 * 时区处理麻烦 − 日期类并不提供国际化，没有时区支持，因此Java引入了java.util.Calendar和java.util.TimeZone类，但他们同样存在上述所有的问题。
 */
public class DateAPI {

    /**
     * 1. Clock 使用时区提供对当前即时，日期和时间的访问的时钟。
     * 2. Instant时间戳操作
     */
    @Test
    public void test1() {

        Clock clock = Clock.systemDefaultZone();
        System.out.println("当前毫秒时间" + clock.millis());
        System.out.println("当前时区:" + clock.getZone());

        Instant instant = clock.instant();
        System.out.println("格式化时间:" + instant);
    }

    /**
     * 3.ZoneId 代表时区类。通过静态工厂方法方便地获取它，入参我们可以传入某个时区编码。另外，时区类还定义了一个偏移量，用来在当前时刻或某时间
     * <p>
     * 与目标时区时间之间进行转换。
     * <p>
     * 4.LocalTime LocalTime 表示一个没有指定时区的时间类
     */
    @Test
    public void test2() {
        System.out.println(ZoneId.getAvailableZoneIds());
        // prints all available timezone ids
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239
    }

    @Test
    public void test3() {
        //取得指定时区的时间：　　　　　　
        long timeInMillis1 = Calendar.getInstance(TimeZone.getTimeZone("GMT-8:00")).getTimeInMillis();
        System.out.println(timeInMillis1);
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
    }


    /**
     * 获取当前分钟
     */
    @Test
    public void getMinute() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        System.out.println(instance.get(Calendar.MINUTE) % 5);
    }

    /**
     * 获取当前到明天凌晨时间毫秒值
     */
    @Test
    public void test() {
        Long time = -Duration.between(LocalDateTime.of(LocalDate.now(), LocalTime.MAX), LocalDateTime.now()).toMillis() / 1000 + 3600;
        System.out.println(time);
    }

    /**
     * 测试解析
     */
    @Test
    public void testDate() {
        System.out.println(String.valueOf(null));

    }

    public Map test(Map<String, Object> map) {
        return map;
    }
}
