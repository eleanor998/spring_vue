package com.tbc.demo.catalog;


import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class Demo {


    public static void main(String[] args) {
        String s = "[14:57:04.818]收←◆A5 04 04 00 78 4D 00 00 78 4D 00 00 78 4E 00 00 78 50 00 00 78 53 00 00 78 51 00 00 78 54 00 00 78 55 00 00 78 57 00 00 78 56 00 00 78 4F 00 00 78 4E 00 00 78 58 00 00 78 54 00 00 78 4F 00 00 78 44 00 00 78 42 00 00 78 42 00 00 78 41 00 00 78 46 00 00 78 48 00 00 78 48 00 00 78 4C 00 00 78 48 00 00 78 4C 00 00 78 54 00 00 78 58 00 00 78 5C 00 00 78 54 00 00 78 50 00 00 78 53 00 00 78 55 00 00 78 57 00 00 78 5D 00 00 78 56 00 00 78 54 00 00 78 5A 00 00 78 53 00 00 78 56 00 00 78 5B 00 00 78 68 00 00 78 6A 00 00 78 67 00 00 78 61 00 00 78 5D 00 00 78 65 00 00 78 68 00 00 78 6D 00 00 78 6D 00 00 78 5F 00 00 78 5C 00 00 78 5C 00 00 78 5F 00 00 78 60 00 00 78 65 00 00 78 74 00 00 78 72 00 00 78 6F 00 00 78 73 00 00 78 70 00 00 78 6C 00 00 78 6C 00 00 78 63 00 00 78 60 00 00 78 57 00 00 78 55 00 00 78 50 00 00 78 52 00 00 78 51 00 00 78 5E 00 00 78 66 00 00 78 6A 00 00 78 6B 00 00 78 6F 00 00 78 6D 00 00 78 69 00 00 78 5F 00 00 78 5F 00 00 78 69 00 00 78 76 00 00 78 6A 00 00 78 62 00 00 78 58 00 00 78 57 00 00 78 57 00 00 78 5B 00 00 78 56 00 00 78 4D 00 00 78 4D 00 00 78 53 00 00 78 5D 00 00 78 5F 00 00 78 55 00 00 78 4D 00 00 78 4E 00 00 78 4B 00 00 78 4C 00 00 78 4B 00 00 78 4C 00 00 78 4B 00 00 78 48 00 00 78 45 00 00 78 41 00 00 78 40 00 00 78 41 00 00 78 4A 00 00 78 4B 00 00 78 4E 00 00 78 50 00 00 78 56 00 00 78 54 00 00 78 4D 00 00 78 58 00 00 78 57 00 00 78 55 00 00 78 4F 00 00 78 54 00 00 78 5C 00 00 78 5E 00 00 78 5C 00 00 78 5B 00 00 78 56 00 00 78 52 00 00 78 4B 00 00 78 48 00 00 78 4F 00 00 78 51 00 00 78 41 00 00 78 3F 00 00 78 46 00 00 78 42 00 00 78 49 00 00 78 54 00 00 78 50 00 00 78 4D 00 00 78 52 00 00 78 55 00 00 78 5B 00 00 78 5C 00 00 78 5B 00 00 78 5B 00 00 78 48 00 00 78 49 00 00 78 4B 00 00 78 45 00 00 78 44 00 00 78 4B 00 00 78 4E 00 00 78 46 00 00 78 46 00 00 78 43 00 00 78 3F 00 00 78 45 00 00 78 42 00 00 78 3A 00 00 78 42 00 00 78 45 00 00 78 48 00 00 78 3D 00 00 78 3E 00 00 78 3D 00 00 78 3D 00 00 78 3C 00 00 78 3A 00 00 78 39 00 00 78 43 00 00 78 45 00 00 78 41 00 00 78 ";

        String[] split = s.split("^.{3}(\\w{2}\\s*){3}");
        if (split.length > 1) {
            String head = split[0];
            String data = split[1];
            System.out.println("头部" + head);
            System.out.println("内容是" + data);

            String[] dt = data.split("\\s");
            for (int i = 0; i < dt.length - 4; i = i + 4) {
                String a0 = dt[i];
                String a1 = dt[i + 1];
                String a2 = dt[i + 2];
                String a3 = dt[i + 3];
                System.out.println("四个元素分别是" + a0 + "--" + a1 + "--" + a2 + "--" + a3);
                String grout = a0 + a1 + a2 + a3;
                System.out.println("拼凑出来的group是" + grout);
            }
        }
    }


    private void test1(User user) {
        System.out.println();
        Object parse = JSONObject.parse(JSONObject.toJSONString(user));
        User user1 = JSONObject.parseObject(JSONObject.toJSONString(user), User.class);

    }

}



