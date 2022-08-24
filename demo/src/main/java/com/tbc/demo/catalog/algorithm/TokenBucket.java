package com.tbc.demo.catalog.algorithm;

import java.util.concurrent.*;

/**
 * 令牌桶 计数法实现
 *
 * @author wenei
 * @date 2021-08-15 10:15
 */
public class TokenBucket {

    private static final String TOKEN = "TOKEN";

    /**
     * 保证其内部方法都安全
     */
    private ArrayBlockingQueue<String> blockingQueue;

    private int limit;

    private int period;

    private ScheduledExecutorService subThreadService;

    public TokenBucket(int limit, int period) {
        this.limit = limit;
        this.period = period;
        blockingQueue = new ArrayBlockingQueue<>(limit);
        for (int i = 0; i < limit; i++) {
            blockingQueue.add(TOKEN);
        }
        subThreadService = Executors.newScheduledThreadPool(1);
        // 定期执行添加令牌任务
        subThreadService.scheduleAtFixedRate(this::addToken, 10, period, TimeUnit.MILLISECONDS);
    }

    public boolean tryAcquire() {
        return blockingQueue.poll() != null;
    }

    private void addToken() {
        blockingQueue.add(TOKEN);
    }

    private void close() {
        subThreadService.shutdownNow();
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        TokenBucket limiter = new TokenBucket(5, 10000);
        ExecutorService threadPool = new ThreadPoolExecutor(
                0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>()
        );
        // 使用锁控制10个线程完成后再处理
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            // 等待4s让令牌加满
            if (finalI == 7) {
                Thread.sleep(4000);
            }
            threadPool.execute(() -> {
                while (true) {
                    if (limiter.tryAcquire()) {
                        System.out.println("线程" + finalI + "获取成功");
                        latch.countDown();
                        break;
                    } else {
                        continue;
                    }
                }
            });
        }
        latch.await();
        threadPool.shutdown();
        limiter.close();
    }
}