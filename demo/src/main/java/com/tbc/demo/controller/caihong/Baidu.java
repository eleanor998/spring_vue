package com.tbc.demo.controller.caihong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tbc.demo.catalog.caihong.utils.AppSigner;
import com.tbc.demo.catalog.caihong.utils.BaiduUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("baidu")
@RestController
@Slf4j
public class Baidu {


    @GetMapping("orderDetail")
    @ResponseBody
    public String getOrderDetail(@RequestParam("storeId") String storeId, @RequestParam("orderId") String orderId) throws URISyntaxException {
        BaiduUtils baiduUtils = new BaiduUtils(new AppSigner());
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("storeId", storeId);
        detail.put("orderId", orderId);
        data.put("data", detail);
        return baiduUtils.sendPostHttp(new URI("https://jiankang.baidu.com/seller-open/order/detail"), data);
    }

}
