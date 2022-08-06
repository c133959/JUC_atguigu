package com.atguigu.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: CallableDemo
 * @Description: TODO
 * @Author sunsl
 * @Date 2022/6/20 21:01
 * @Version 1.0
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Runnable接口创建线程
        new Thread(new MT1(), "AA").start();

        //Callable
        //new Thread(new MT2(), "BB").start();-报错

        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MT2());

        //lambda表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName() +"\tcallable");
           return 1024;
        });


        // 创建Callable线程
        new Thread(futureTask2, "QQ").start();

        while (!futureTask2.isDone()) {
            System.out.println("running....");
        }

        System.out.println(Thread.currentThread().getName()+"\t is done");

        System.out.println(futureTask2.get());//获取返回值

    }

}

class MT1 implements Runnable {

    @Override
    public void run() {

    }
}

class MT2 implements Callable {

    @Override
    public Integer call() throws Exception {
        return 520;
    }
}
