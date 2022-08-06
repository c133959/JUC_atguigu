package com.atguigu.singleton;

public class SingletonDemo {

    private static SingletonDemo instance = null;

    public SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\tSingletonDemo初始化了");
    }

    /**
     *     1. public static synchronized SingletonDemo getInstance() 使用synchronized
     *     if (instance == null) {
     *             instance = new SingletonDemo();
     *         }
     *         return instance;
     *     2. DCL（Double Check Lock双端检索机制）
     *     不一定线程安全：有指令重排的存在，加入volatile可以禁止指令重排
     */
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // 单线程
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
