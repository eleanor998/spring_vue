package com.tbc.demo.catalog.forkjoin.utils.handler;

import java.util.function.Consumer;

/**
 * @author gkk
 * @version V1.0 数据处理器
 */
public interface ComputerHandler {

    /**
     * created by gkk
     */
    void execut( Integer sharding, Consumer<Object> resultConsumer, Object conditions );

    void execut( Consumer<Object> resultConsumer, Object conditions );


    /**
     * create by gkk
     */
    int count( Object conditions );

}

