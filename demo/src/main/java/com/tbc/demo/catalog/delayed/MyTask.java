package com.tbc.demo.catalog.delayed;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyTask implements Delayed {
    String name;
    //延迟的时间
    long runningTime;

    MyTask(String name, long rt) {
        this.name = name;
        this.runningTime = rt;
    }

    @Override
    public int compareTo(Delayed other) {
        long td = this.getDelay(TimeUnit.MILLISECONDS);
        long od = other.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(td, od);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return name + "-" + runningTime;
    }



    public static void main(String[] args) throws InterruptedException {
        DelayQueue<MyTask> task = new DelayQueue();
        long now = System.currentTimeMillis();
        //每个任务设置延迟的时间，下面都是从当前时间计算
        MyTask t1 = new MyTask("t1",now + 10000);
        MyTask t2 = new MyTask("t2",now + 10000);
        MyTask t3 = new MyTask("t3",now + 10000);
        MyTask t4 = new MyTask("t4",now + 10000);
        MyTask t5 = new MyTask("t5",now + 10000);
        MyTask t6 = new MyTask("t6",now + 10000);
        for (int i = 0; i < 6; i++) {
            System.out.println(task.take());
        }

        System.out.println(ObjectSizeCalculator.getObjectSize(task));

    }


}
