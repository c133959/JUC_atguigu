package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间的定制化通讯
 */

// 创建资源类
class ShareResource {

    private int flag = 1;// 标识符

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    // 打印5次的方法
    public void print5(int loop){

        //上锁
        lock.lock();

        //干活
        try {

            while (flag != 1) {
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + "::轮数" + loop);
            }
            flag = 2;
            condition2.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }

    }

    // 打印10次的方法
    public void print10(int loop){

        //上锁
        lock.lock();

        //干活
        try {

            while (flag != 2) {
                condition2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + "::轮数" + loop);
            }
            flag = 3;
            condition3.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }

    }

    // 打印15次的方法
    public void print15(int loop){

        //上锁
        lock.lock();

        //干活
        try {

            while (flag != 3) {
                condition3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + "::轮数" + loop);
            }
            flag = 1;
            condition1.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }

    }



}


public class ThreadDemo3 {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print5(i);
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print10(i);
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print15(i);
            }
        },"CC").start();

    }
}
