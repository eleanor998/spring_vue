package com.tbc.demo.catalog.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.tbc.demo.utils.HttpClientUtils;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class DingDingMessageUtils {

    private static final Logger log = LoggerFactory.getLogger(DingDingMessageUtils.class);
    //钉钉限制一分钟20个如果超过了会限制10分钟所以每1秒0.32个每3秒发送一次
    private static final double SPEED_LIMIT = 0.32;
    //钉钉限制最大数据byte为2000超过会报错
    private static final int SIZE_LIMIT = 1990;
    private String url;
    private String secret;
    //异步执行任务执行后停止标识
    private Boolean execute;
    //错误队列，会重试一次
    private List<String> errorList = new ArrayList<>();
    //全局任务队列
    private static Queue<String> taskQueue = new LinkedBlockingQueue();

    /**
     * 添加待发送的任务
     *
     * @param message
     */
    public static void addTask(String message) {
        if (!taskQueue.contains(message)) {
            taskQueue.add(message);
        }
    }

    /**
     * 构建
     *
     * @param url
     * @param secret
     */
    public DingDingMessageUtils(String url, String secret) {
        this.url = url;
        this.secret = secret;
    }


    /**
     * @param content 内容
     * @return
     * @throws Exception
     */
    public JSONObject sendMessage(String content) throws Exception {
        return sendMessage(content, Arrays.asList(), false);
    }

    /**
     * 发送钉钉消息,
     *
     * @param content 需要发送的内容通
     * @param atList  需要@的群里的人手机号
     * @param isAtAll 是否@群里的全部人员
     * @throws Exception
     */
    public JSONObject sendMessage(String content, List<String> atList, boolean isAtAll) throws Exception {
        //获取系统时间戳
        Long timestamp = System.currentTimeMillis();
        //拼接
        String stringToSign = timestamp + "\n" + this.secret;
        //使用HmacSHA256算法计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        //进行Base64 encode 得到最后的sign，可以拼接进url里
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        //钉钉机器人地址（配置机器人的webhook），为了让每次请求不同，避免钉钉拦截，加上时间戳
        String url = this.url + "&timestamp=" + timestamp + "&sign=" + sign;
        try {
            //钉钉机器人消息内容
            content += timestamp;
            //组装请求内容
            String reqStr = buildReqStr(content, isAtAll, atList);
            //推送消息（http请求）
            return HttpClientUtils.httpPost(url, reqStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 组装请求报文
     *
     * @param content
     * @return
     */
    private String buildReqStr(String content, boolean isAtAll, List<String> mobileList) {
        //消息内容
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("content", content);
        //通知人
        Map<String, Object> atMap = Maps.newHashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);
        Map<String, Object> reqMap = Maps.newHashMap();
        //text可以发送各种类型的消息，包括链接，以及带有图片等等类型
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);
        return JSON.toJSONString(reqMap);
    }

    /**
     * 限速发送
     *
     * @param queue
     * @return
     * @throws Exception
     */
    public List<String> limitSend(Queue<String> queue) throws Exception {
        RateLimiter rateLimiter = RateLimiter.create(SPEED_LIMIT);
        for (String s : queue) {
            rateLimiter.acquire();
            try {
                sendMessage(queue.poll());
            } catch (Exception e) {
                errorList.add(s);
                log.error("{}", e);
            }
        }

        for (String s : errorList) {
            rateLimiter.acquire();
            try {
                sendMessage(s);
            } catch (Exception e) {
                errorList.add(s);
                log.error("redo error{}", e);
                throw e;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 循环等待执行 taskQueue 中的书
     *
     * @throws Exception
     */
    public void startQueueTask() throws Exception {
        while (execute) {
            limitSend(taskQueue);
            wait(2000);
        }
    }

    /**
     * 停止等待上面的全局任务
     */
    public void stopQueueTask() {
        this.execute = false;
    }

    /**
     * 判断对象大小是否超过上线
     *
     * @param obj
     * @return
     */
    public static Boolean sizeOutOf(Object obj) {
        return ObjectSizeCalculator.getObjectSize(obj) < SIZE_LIMIT;
    }


    public static void main(String[] args) throws Exception {
        DingDingMessageUtils dingDingMessageUtils = new DingDingMessageUtils("url", "secret");
        // 默认发送text文本，需要其他类型自己配
        dingDingMessageUtils.sendMessage("xxx");
        // @ 某个人
        dingDingMessageUtils.sendMessage("tset", Arrays.asList("13333333333", "1333333334"), false);
        //异步发送 1.全局使用DingDingMessageUtils.addTask 添加需要发送的消息
        DingDingMessageUtils.addTask("需要发送的消息");
        DingDingMessageUtils.addTask("需要发送的消息1");
        DingDingMessageUtils.addTask("需要发送的消息2");
        //创建线程 2. 创建线程次
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                //3. 开启任务
                dingDingMessageUtils.startQueueTask();
            } catch (Exception e) {
                //处理异常
            }
        });
        //4. 停止任务
        dingDingMessageUtils.stopQueueTask();

    }

}


