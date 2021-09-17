package com.tbc.demo.catalog.jdk8;

import cn.hutool.core.map.MapUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试默认接口
 */
public interface DefaultIntface {
    Map map = new HashMap();

    default Map getMap() {
        if (MapUtil.isEmpty(map)) {
            synchronized (DefaultIntface.class) {
                if (MapUtil.isEmpty(map)) {
                    map.put("test", new Date());
                }
            }
        }
        return map;
    }
}
