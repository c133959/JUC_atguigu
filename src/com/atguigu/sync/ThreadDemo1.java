package com.atguigu.sync;

// 1.定义一个资源类，包含属性和方法
class Share {

    private int number = 0;//属性

    public synchronized void add() throws InterruptedException {//+1的方法
        // 2.判断 干活 通知
        //if (number != 0) {//如果不是0，等待
        while (number != 0) {//防止虚假唤醒问题，让醒来的线程继续走判断条件
            this.wait();// 在哪里睡，就在哪里醒来
        }
        // 如果是0, +1
        number++;
        System.out.println(Thread.currentThread().getName()+" :: " + number);
        // 通知其他线程
        this.notifyAll();
    }

    public synchronized void dec() throws InterruptedException {//-1的方法
        // 2.判断 干活 通知
        while (number != 1) {//如果不是1，等待
            this.wait();
        }
        // 如果是0, -1
        number--;
        System.out.println(Thread.currentThread().getName()+" :: " + number);
        // 通知其他线程（唤醒所有等待的线程
        notifyAll();
//        notify();唤醒一个等待的线程
    }



}

public class ThreadDemo1 {

    // 创建多个线程，实现
    public static void main(String[] args) {
        Share share = new Share();

        // AA 线程为生产者
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



        // AA 线程为生产者
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

        // BB线程为消费者
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.dec();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();




    }
}
