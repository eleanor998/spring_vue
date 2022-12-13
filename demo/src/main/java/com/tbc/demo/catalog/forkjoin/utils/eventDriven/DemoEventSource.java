package com.tbc.demo.catalog.forkjoin.utils.eventDriven;


import com.tbc.demo.catalog.forkjoin.utils.event.EventSource;

/**
 * created by gkk
 *
 * @description: √Ë ˆ
 */
public class DemoEventSource<T> extends EventSource<T> {


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DemoEventSource( T source ) {
        super(source);
    }
}
