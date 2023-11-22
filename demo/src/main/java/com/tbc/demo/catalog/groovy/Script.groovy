package com.tbc.demo.catalog.groovy

import com.tbc.demo.entity.User

import static com.alibaba.fastjson.JSON.toJSONString

/**
 * idea 可直接新建groovy类型文件
 */
class Script {

    static hello = { args ->
        return "${toJSONString(new User())}$args"
    }

    static void main(String[] args) {
        println hello("这个是测试")
    }
}
