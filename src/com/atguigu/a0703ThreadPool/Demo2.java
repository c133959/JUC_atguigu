package com.atguigu.a0703ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用Executors工具类，进行创建线程池
 * Executors.newFixedThreadPool(int i) ：创建一个拥有 i 个线程的线程池
 *      执行长期的任务，性能好很多
 *      创建一个定长线程池，可控制线程数最大并发数，超出的线程会在队列中等待
 * Executors.newSingleThreadExecutor：创建一个只有1个线程的 单线程池
 *      一个任务一个任务执行的场景
 *      创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行
 * Executors.newCacheThreadPool(); 创建一个可扩容的线程池
 *      执行很多短期异步的小程序或者负载教轻的服务器
 *      创建一个可缓存线程池，如果线程长度超过处理需要，可灵活回收空闲线程，如无可回收，则新建新线程
 */
public class Demo2 {
    public static void main(String[] args) {
        // 一池5个处理线程（用池化技术，一定要记得关闭）
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
        // 创建一个只有一个线程的线程池
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        // 创建一个拥有N个线程的线程池，根据调度创建合适的线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        try {
            // 模拟10个用户来办理业务
            for (int i = 0; i < 20; i++) {
                // threadPool1.execute(()->{
                // threadPool2.execute(()->{
                threadPool3.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
                //TimeUnit.SECONDS.sleep(2);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            threadPool1.shutdown();
        }
    }
}
