package com.tbc.demo.catalog.zhongwen;


import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ZhongWen {

    /**
     * 如果代码中使用了中文，idea一旦切换了编码格式可能会出现中文数据丢失的情况。不建议使用中文！
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        String test = URLEncoder.encode("中文", "UTF-8");
        map.put(test,"test");
        String s = JSONObject.toJSONString(map);
        System.out.println(s);
    }
}
