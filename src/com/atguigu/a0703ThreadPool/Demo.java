package com.atguigu.a0703ThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 01 概述
 */

// Runnable接口
class MyThread implements Runnable {

    @Override
    public void run() {

    }
}
class MyThread2 implements Callable {

    @Override
    public Object call() throws Exception {
        return 1024;
    }
}

public class Demo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Expression expected 编译报错 MyThread2为Callable，Thread不支持此类对象注入
        // Thread param = new Thread(MyThread2);

        // FutureTask：实现了Runnable接口，构造函数又需要传入 Callable接口
        // 这里通过了FutureTask接触了Callable接口
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        Thread t1 = new Thread(futureTask, "AA");
        t1.start();

        // 也就是说 futureTask.get() 需要放在最后执行，这样不会导致主线程阻塞
        System.out.println("******result:" + futureTask.get().toString());
    }

}
