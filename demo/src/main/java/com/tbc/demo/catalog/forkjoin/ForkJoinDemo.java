package com.tbc.demo.catalog.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinDemo extends RecursiveTask<Long> {

	private static final long serialVersionUID = -3454048054107103321L;
	
	//设立一个最大计算容量
	private final int DEFAULT_CAPACITY = 10000;
	
	//用2个数字表示目前要计算的范围
    private int start;
    private int end;
    
    public ForkJoinDemo(int start, int end) {
		this.start = start;
		this.end = end;
	}
    
	@Override
	protected Long compute() {
		//分为两种情况进行出来
        long sum = 0;
        //如果任务量在最大容量之内
        if(end - start < DEFAULT_CAPACITY){
            for (int i = start; i < end; i++) {
                sum += i;
            }
        }else{//如果超过了最大容量，那么就进行拆分处理
            //计算容量中间值
            int middle = (start + end)/2;
            //进行递归
            ForkJoinDemo FockJoinDemo1 = new ForkJoinDemo(start, middle);
            ForkJoinDemo FockJoinDemo2 = new ForkJoinDemo(middle + 1, end);
            //执行任务
            FockJoinDemo1.fork();
            FockJoinDemo2.fork();
            //等待任务执行并返回结果
            sum = FockJoinDemo1.join() + FockJoinDemo2.join();
        }

        return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		ForkJoinDemo forkJoinDemo = new ForkJoinDemo(1, 1000000000);
        long fockhoinStartTime = System.currentTimeMillis();
        //前面我们说过，任务提交中invoke可以直接返回结果
        long result = forkJoinPool.invoke(forkJoinDemo);
        System.out.println("fock/join计算结果耗时"+(System.currentTimeMillis() - fockhoinStartTime));

        long sum = 0;
        long normalStartTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            sum += i;
        }
        System.out.println("普通计算结果耗时"+(System.currentTimeMillis() - normalStartTime));
        
        long start = System.currentTimeMillis();
        
        Long sum2 = LongStream.rangeClosed(0L, 10000000000L)
                             .parallel()
                             .sum();  
        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start));
    }
}
