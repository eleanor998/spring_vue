package com.tbc.demo.catalog.groovy

import com.alibaba.fastjson.JSONObject
import com.tbc.demo.entity.User

/**
 * idea 可直接新建groovy类型文件
 */
class Script {

    static String hello(String args) {
        def user = new User()
        return JSONObject.toJSONString(user) + args
    }
}
