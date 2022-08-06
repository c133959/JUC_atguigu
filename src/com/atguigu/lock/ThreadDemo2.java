package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间通信
 */

// 1.定义一个资源类，包含属性和方法
class Share {

    private int number = 0;//属性

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();//返回绑定此lock实例的新Condition实例

    public void add() throws InterruptedException {//+1的方法

        // 上锁
        lock.lock();
        try {
            // 2.判断 干活 通知
            while (number != 0) {
                condition.await();
            }

            number++;
            System.out.println(Thread.currentThread().getName()+" :: " + number);

            condition.signalAll();

        }finally {
            // 解锁
            lock.unlock();
        }

    }

    public void dec() throws InterruptedException {//-1的方法

        lock.lock();

        try {
            // 2.判断 干活 通知
            while (number != 1) {
                condition.await();
            }

            number--;
            System.out.println(Thread.currentThread().getName()+" :: " + number);

            condition.signalAll();

        }finally {
            lock.unlock();
        }
    }



}

public class ThreadDemo2 {

    public static void main(String[] args) {
        Share share = new Share();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        // BB线程为消费者
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.dec();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }

}
