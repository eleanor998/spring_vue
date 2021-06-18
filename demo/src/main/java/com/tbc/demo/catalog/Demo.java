package com.tbc.demo.catalog;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tbc.demo.common.model.TagManager;
import com.tbc.demo.utils.BitStateUtils;
import com.tbc.demo.utils.BitsMap;
import com.tbc.demo.utils.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterCommand;
import sun.security.krb5.internal.HostAddress;

import javax.websocket.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.xmlbeans.impl.store.Public2.test;

@Slf4j
public class Demo {
    private static ExecutorService executorService = Executors.newFixedThreadPool(30);

    public static void main(String[] args) {
    }

    @Test
    public void test() {


    }

}



