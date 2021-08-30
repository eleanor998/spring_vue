package com.tbc.demo.catalog.json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.common.model.Student;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试json解析
 */
public class JsonReader {

    @Test
    public void test1() {
        //测试实体
        Student student = new Student();
        student.setName("张三");
        student.setCourse(Arrays.asList("语文", "数学"));
        String s = JSONObject.toJSONString(student);
        System.out.println("对象序列化=======" + s);
        Student student1 = JSON.parseObject(s, Student.class);
        System.out.println("JSON解析对象=======" + student1);
        //测试map
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("course", Arrays.asList("语文", "数学"));
        String s1 = JSON.toJSONString(map);
        System.out.println("map序列化=======" + s1);
        Student student3 = JSON.parseObject(s1, Student.class);
        System.out.println("map转json=======" + student3);
    }
}
