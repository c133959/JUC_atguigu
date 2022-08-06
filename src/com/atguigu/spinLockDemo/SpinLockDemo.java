package com.atguigu.spinLockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");

        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + "等待中");
        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t invoked myUnlock()");
    }



    public static void main(String[] args) throws InterruptedException {

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            //上锁
            spinLockDemo.myLock();
            try {
                System.out.println("t1占用线程中");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "t1").start();

        // 保证AA线程先启动
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{

            spinLockDemo.myLock();
            spinLockDemo.myUnLock();

        }, "t2").start();

    }
}
