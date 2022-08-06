package com.atguigu.lock;

import java.util.concurrent.TimeUnit;

/**
 * 演示死锁
 *
 * 持有A，等待B
 * 持有B，等待A
 */
public class DeadLock {
    //创建两个对象
    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (a) {
                System.out.println("持有A，等待B");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + "获取锁B");
                }
            }
        }, "AA").start();

        new Thread(()-> {
            synchronized (b) {
                System.out.println("持有B，等待A");
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + "获取A");
                }
            }
        },"BB").start();

    }
}
