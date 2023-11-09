package com.tbc.demo.catalog.caihong;

import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.catalog.caihong.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度接口联调
 */
public class Sign {


    static String orderId = Dev.orderId;
    static String storeId = Dev.storeId;
    static String shopId = Dev.shopId;
    static String opType = Dev.opType;
    static String channelProductId = Dev.channelProductId;


    public static void main(String[] args) throws URISyntaxException {
        getOrderDetail();
//        getSql();

    }


    //获取订单详情
    private static void getSql() throws URISyntaxException {
        System.out.println(System.currentTimeMillis());

        String resultSql = SqlConstant.sql
                .replaceAll(SqlConstant.CHANNEL_ID, storeId)
                .replaceAll(SqlConstant.CHANNEL_PRODUCT_ID, channelProductId);
        String sql = SqlConstant.DELETE_DATA
                .replaceAll(SqlConstant.ORDER_ID, orderId)
                .replaceAll(SqlConstant.CHANNEL_PRODUCT_ID, orderId);
        System.out.println(sql);
        System.out.println(resultSql);
    }

    private static void getOrderDetail() throws URISyntaxException {

        BaiduUtils baiduUtils = new BaiduUtils(new AppSigner());
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("storeId", storeId);
        detail.put("orderId", orderId);
        data.put("data", detail);
        baiduUtils.sendPostHttp(new URI("https://jiankang.baidu.com/seller-open/order/detail"), data);
    }

    //确认接种
    private static void confirmUserInoculate() throws URISyntaxException {
        String str = ConfirmUserAppointDTO.builder().opType(1).shopId(shopId).storeId(storeId).orderId(orderId).build().toInternalDataJsonString();
        BaiduUtils baiduUtils = new BaiduUtils(new AppSigner());
        baiduUtils.sendPostHttp(new URI("https://jiankang.baidu.com/seller-open/order/confirmUserInoculate"), str);
    }


    private static void getSign(String msg) {
        String s = new AppSigner().msgSign(msg);
        System.out.println(s);
    }

    private static void init() {
        try {
            // 指定要读取的JSON文件路径
            String filePath = "C:\\Users\\54453\\IdeaProjects\\spring_vue\\demo\\src\\main\\resources\\JSON.json";
            // 创建一个BufferedReader对象来读取文件
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // 读取文件的内容
            String line;
            StringBuilder jsonContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // 关闭读取器
            reader.close();

            // 输出读取到的JSON内容
            JSONObject jsonObject = JSONObject.parseObject(jsonContent.toString());
            JSONObject data = jsonObject.getJSONObject("data");
            storeId = data.getString("newStoreId");
            shopId = data.getString("shopId");
            orderId = data.getString("orderId");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
