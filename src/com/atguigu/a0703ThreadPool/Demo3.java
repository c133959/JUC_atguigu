package com.atguigu.a0703ThreadPool;

import java.util.concurrent.*;

/**
 * @ClassName: Demo3
 * @Description: TODO
 * @Author sunsl
 * @Date 2022/7/6 21:32
 * @Version 1.0
 */
public class Demo3 {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());// 展示当前系统处理器几核

        // 手写线程池
        final Integer corePoolSize = 2;
        final Integer maximumPoolSize = 5;
        final Long keepAliveTime = 1L;

        // 自定义线程池，只改变了LinkBlockingQueue的队列大小
        ExecutorService threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        try {
            // 模拟10个用户办理业务，每个用户就是一个来自外部的请求
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() +"\t办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}
