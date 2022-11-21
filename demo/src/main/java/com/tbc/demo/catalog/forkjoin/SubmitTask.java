package com.tbc.demo.catalog.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class SubmitTask<T> extends RecursiveAction {
    static final int THRESHOLD = 5;
    private List<T> list;
    private int start;
    private int end;
    private Consumer consumer;


    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public SubmitTask(List list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (T t : list) {
                consumer.accept(t);
            }
        }
        int middle = (end + start) / 2;
//        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SubmitTask subtask1 = new SubmitTask(list, start, middle);
        SubmitTask subtask2 = new SubmitTask(list, middle, end);
        invokeAll(subtask1, subtask2);
        return;
    }
}