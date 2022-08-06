package com.atguigu.blockingQueueDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue没有容量，与其他BlockingQueue不同，
 * SynchronousQueue是一个不存储的BlockingQueue，每一个put操作必须等待一个take操作，否者不能继续添加元素
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        // 创建两个线程， 一个用来生产，一个用来消费
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "\t put A");
                blockingQueue.put("A");
                System.out.println(Thread.currentThread().getName() + "\t put B");
                blockingQueue.put("B");
                System.out.println(Thread.currentThread().getName() + "\t put C");
                blockingQueue.put("C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();

        //消费线程使用take，消费阻塞队列中的内容，并且每次消费前，都等待5秒
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take A");
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take B");
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take C");
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();
    }
    /**
     * 运行结果：
     * prod	 put A
     * consumer	 take A
     * prod	 put B
     * consumer	 take B
     * prod	 put C
     * consumer	 take C
     */

}
