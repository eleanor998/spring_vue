package com.tbc.demo.catalog.forkjoin.utils.event;

import java.util.EventObject;

/**
 * @author gkk
 * @description: 事件源
 */
public class EventSource<T> extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventSource( T source ) {
        super(source);
    }
}
