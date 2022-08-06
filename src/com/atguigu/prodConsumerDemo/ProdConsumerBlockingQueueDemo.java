package com.atguigu.prodConsumerDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在concurrent包发布以前，在多线程环境下，我们每个程序员都必须自己去控制这些细节，尤其还要兼顾效率和线程安全，
 * 则这会给我们的程序带来不小的时间复杂度
 *
 * 现在我们使用新版的阻塞队列版生产者和消费者，使用：volatile、CAS、atomicInteger、BlockQueue、线程交互、原子引用
 *
 * 为ProdConsumerTraditionDemo升级版
 *
 */


// 资源类
class ShareData {
    //默认开始，进行生产+消费
    private volatile boolean FLAG = true;
    // 使用原子包装类，而不用number++
    private AtomicInteger atomicInteger = new AtomicInteger();

    // 这里不能为了满足条件，而实例化一个具体的SynchronousBlockingQueue(要通过注入）
    BlockingQueue<String> blockingQueue = null;
    public ShareData(BlockingQueue<String> queue) {
        // 通过注入
        this.blockingQueue = queue;
        // 反射，获取传入的class信息
        System.out.println("注入的类为：\t" + queue.getClass().getName());
    }

    /**
     * 生产者
     */
    public void prod() throws Exception {
        String data = null;
        boolean retValue;
        // 判断
        while (FLAG) {
            // 干活
            data = atomicInteger.incrementAndGet() + "";
            // 使用offer插入的时候，需要指定时间，如果2秒还没有插入，那么就放弃插入
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t停止生产了");
    }
    /**
     * 消费者
     */
    public void consumer() throws Exception {
        String data = null;
        boolean retValue;
        // 判断
        while (FLAG) {
            // 如果2秒内取不出来，那么就返回null
            data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (data != null && data != "") {
                System.out.println(Thread.currentThread().getName() + "\t消费队列" + data + "成功");
            } else {
                // 生产停了，但是消费还在消费，此时需要退出消费队列
                //FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 消费失败，队列中已为空，退出" );
                // 退出消费队列
                //return;
            }
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + "\t停止消费了");
    }

    /**
     * 停止
     */
    public void stop() {
        this.FLAG = false;
    }


}
public class ProdConsumerBlockingQueueDemo {

    public static void main(String[] args) {
        // 传入具体的实现类
        ShareData shareData = new ShareData(new ArrayBlockingQueue<String>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            System.out.println("");
            System.out.println("");
            try {
                shareData.prod();
                System.out.println("");
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");

            try {
                shareData.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        // 5秒后，停止生产和消费
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("");
        System.out.println("5秒中后，生产和消费线程停止，线程结束");
        shareData.stop();
    }


}
