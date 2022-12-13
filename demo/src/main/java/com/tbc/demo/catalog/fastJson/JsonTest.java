package com.tbc.demo.catalog.fastJson;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class JsonTest {
    public static void main(String[] args) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test_id", 1);
        jsonObject.put("name", "12313");
        jsonObject.put("addr", "12313");
        jsonObject.put("date", new Date());
        String s = jsonObject.toString();
        System.out.println(s);
        JsonTestPO jsonTestPO = JSON.parseObject(s, JsonTestPO.class);
        System.out.println(jsonTestPO);
    }
}
