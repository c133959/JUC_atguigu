package com.atguigu.a0806forkjion;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork 就是把一个大任务切分为若干子任务并行的执行，
 * Join 就是合并这些子任务的执行结果，最后得到这个大任务的结果。
 *
 * Fork/Join 使用两个类来完成以上两件事情：
 * 1. ForkJoinTask：我们要使用 ForkJoin 框架，必须首先创建一个 ForkJoin 任务。它提供在任务中执行 fork() 和 join() 操作的机制，
 * 通常情况下我们不需要直接继承 ForkJoinTask 类，而只需要继承它的子类，Fork/Join 框架提供了以下两个子类：
 *      RecursiveAction：用于没有返回结果的任务。
 *      RecursiveTask ：用于有返回结果的任务。
 * 2. ForkJoinPool ：ForkJoinTask 需要通过 ForkJoinPool 来执行，任务分割出的子任务会添加到当前工作线程所维护的双端队列中，
 * 进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。
 *
 */
class MyTask extends RecursiveTask<Integer> {

    // 拆分差值不能超过10,计算10以内的
    private static final Integer VALUE = 10;
    private int begin;
    private int end;
    private int result;

    // 有参构造
    public MyTask(int begin, int end){
        this.begin = begin;
        this.end = end;
    }

    // 拆分和合并过程
    @Override
    protected Integer compute() {
        // 判断相加的两个数是否大于VALUE
        if (end - begin <= VALUE) {
            // 相加
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            // 进一步拆分
            int mid = (end - begin) / 2 + begin;
            // 拆分左边
            MyTask task01 = new MyTask(begin, mid);
            // 拆分右边
            MyTask task02 = new MyTask(mid + 1, end);
            // 调用方法拆分，执行方法
            task01.fork();
            task02.fork();
            // 合并结果
            result = task01.join() + task02.join();
        }
        return result;
    }
}
public class ForkJoinDemo {
    public static void main(String[] args) {
        MyTask myTask = new MyTask(1,100);
        // 创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭对象
        forkJoinPool.shutdown();
    }
}
