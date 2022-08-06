package com.atguigu.a0707DeadLockDemo;

/**
 * 死锁小Demo
 * 死锁是指两个或多个以上的进程在执行过程中，
 * 因争夺资源而造成一种互相等待的现象，
 * 若无外力干涉那他们都将无法推进下去
 */
import java.util.concurrent.TimeUnit;

/**
 * 资源类
 */
class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    // 持有自己的所，还想得到别人的所
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockA + "\t 尝试获取：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockB + "\t 尝试获取：" + lockA);
            }
        }
    }
}
public class demo1 {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "t1").start();
        new Thread(new HoldLockThread(lockB, lockA), "t2").start();

        /**
         * Linux:
         *      ps -ef|grep xxx
         * Win:
         *      jsp -l
         *      jstack  XXX   # 后面参数是 jps输出的该类的pid
         *
         */
    }
}
