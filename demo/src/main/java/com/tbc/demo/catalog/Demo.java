package com.tbc.demo.catalog;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;

import java.io.ObjectStreamClass;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Demo {


    public static void main(String[] args) {
        Map<Object, String> jsonObject = new HashMap<>();
        jsonObject.put(0L, "CURRENT_DEPARTMENT_AND_CHILDREN");
        System.out.println(JSONObject.toJSONString(jsonObject));

    }


    private void test1(User user) {
        System.out.println();
        Object parse = JSONObject.parse(JSONObject.toJSONString(user));
        User user1 = JSONObject.parseObject(JSONObject.toJSONString(user), User.class);

    }

}



