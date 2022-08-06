package com.atguigu.prodConsumerDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个初始值为0的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
 *
 * 关于多线程：
 *      线程 操作 资源列
 *      判断 干活 通知
 *      防止虚假唤醒
 */

// 资源类
class shareData {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 内聚
    public void increment() throws Exception {
        // 同步代码块
        lock.lock();
        try {
            // 判断
            while (num != 0) {
                // 等待，不能进行生产
                condition.await();
            }
            // 干活
            num++;

            System.out.println(Thread.currentThread().getName() + "\t " + num);

            // 通知 唤醒
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {
        // 同步代码块
        lock.lock();
        try {
            /**
             * 但是我们在进行判断的时候，为了防止出现虚假唤醒机制，不能使用if来进行判断，而应该使用while
             */
            // 判断
            if (num == 0) {
                // 等待，不能进行消费
                condition.await();
            }
            // 消费
            num--;

            System.out.println(Thread.currentThread().getName() + "\t " + num);

            // 通知 唤醒
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
public class ProdConsumerTraditionDemo {

    public static void main(String[] args) {
        shareData shareData = new shareData();

        // prod线程
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"prod").start();

        // consumer线程
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"consumer1").start();

        // consumer线程
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"consumer2").start();
        /**
         * while判断时，多个消费者线程不会出现虚假唤醒
         * prod	 1
         * consumer1	 0
         * prod	 1
         * consumer1	 0
         * prod	 1
         * consumer1	 0
         * prod	 1
         * consumer2	 0
         * prod	 1
         * consumer2	 0
         *
         *
         * if 判断时，出现虚假唤醒：
         *prod	 1
         * consumer1	 0
         * prod	 1
         * consumer2	 0
         * consumer1	 -1
         * consumer1	 -2
         * consumer1	 -3
         * consumer1	 -4
         * consumer2	 -5
         * consumer2	 -6
         * consumer2	 -7
         * consumer2	 -8
         */

    }
}
