package com.tbc.demo.catalog.forkjoin.utils.event;
import java.util.EventListener;

/**
 * @author gkk
 * @description: 事件监听器
 */
public abstract class AbstractEventListener<T, S extends EventSource<T>> implements EventListener {
    /**
     * description 监听事件源
     * @param eventSource 事件源
     */
    public abstract void onEventListener( S eventSource );

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals(obj);
    }
}
