package com.tbc.demo.catalog.fastJson;

import com.alibaba.fastjson.annotation.JSONField;


public class JsonTestPO {
    @JSONField(name = "test_id")
    private Integer id;
    private String name;
    private String addr;
    @JSONField(format = "yyyy-mm-dd hh:MM:ss")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
