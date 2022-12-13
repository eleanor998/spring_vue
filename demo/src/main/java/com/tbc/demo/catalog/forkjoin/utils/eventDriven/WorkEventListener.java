package com.tbc.demo.catalog.forkjoin.utils.eventDriven;

import com.tbc.demo.catalog.forkjoin.utils.event.AbstractEventListener;
import com.tbc.demo.catalog.forkjoin.utils.event.EventSource;
import com.tbc.demo.catalog.forkjoin.utils.pool.ThreadPool;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * created by gkk
 *
 * @description: Ӧ�ó��������������ܼ��ʹ���һ���Զ������������ԱȽϴ�
 */
public class WorkEventListener<T, S extends EventSource<T>> extends AbstractEventListener<T, S> {

    private final ThreadPool threadPool;

    private Consumer<S> consumer;

    public WorkEventListener( Consumer<S> consumer ) {
        //���ﲻһ��Ҫʹ��Ĭ�ϵ��̳߳أ�����ҵ��������
        threadPool = ThreadPool.DEFAULT_THREAD_POOL;
        this.consumer = Objects.requireNonNull(consumer, "consumer is undefined");
    }

    @Override
    public void onEventListener( S eventSource ) {
        threadPool.submit(() -> consumer.accept(eventSource));
    }
}
