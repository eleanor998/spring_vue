package com.tbc.demo.catalog.forkjoin.utils.eventDriven;

import com.tbc.demo.catalog.forkjoin.utils.event.AbstractEventListener;
import com.tbc.demo.catalog.forkjoin.utils.event.EventSource;
import com.tbc.demo.catalog.forkjoin.utils.pool.ThreadPool;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * created by gkk
 *
 * @description: 应用场景：基于任务密集型处理；一次性多个任务（任务相对比较大）
 */
public class WorkEventListener<T, S extends EventSource<T>> extends AbstractEventListener<T, S> {

    private final ThreadPool threadPool;

    private Consumer<S> consumer;

    public WorkEventListener( Consumer<S> consumer ) {
        //这里不一定要使用默认的线程池，根据业务需求定义
        threadPool = ThreadPool.DEFAULT_THREAD_POOL;
        this.consumer = Objects.requireNonNull(consumer, "consumer is undefined");
    }

    @Override
    public void onEventListener( S eventSource ) {
        threadPool.submit(() -> consumer.accept(eventSource));
    }
}
