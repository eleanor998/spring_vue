package com.tbc.demo.catalog.forkjoin.utils.utils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * created by gkk
 *
 * @description: 数据分割处理
 */
public final class ResolutionUtils {
    private ResolutionUtils(){}
    /**
     * description 分片数从1开始执行，如果索引是0的要加1
     * 分片处理
     */
    public static Map<Integer, List<Integer>> sharding( int shardingNum, int count ) {
        Map<Integer, List<Integer>> shardingDataMap = new HashMap<>(shardingNum);
        for (int i = 1; i <= count; i++) {
            if (shardingDataMap.containsKey(i % shardingNum)) {
                shardingDataMap.get(i % shardingNum).add(i);
            } else {
                List<Integer> shardingData = new ArrayList<>(count / shardingNum + 1);
                shardingData.add(i);
                shardingDataMap.put(i % shardingNum, shardingData);
            }
        }
        return shardingDataMap;
    }
    /**
     * description cpu资源释放
     */
    public static void releaseCpuSource( long milliseconds ) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * description 释放资源
     */
    public static void releaseSource(Object... objs) {
        if (Objects.nonNull(objs)) {
            Arrays.fill(objs, null);
        }
    }


}
