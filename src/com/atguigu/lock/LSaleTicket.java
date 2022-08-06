package com.atguigu.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LSaleTicket
 * @Description: Lock方法实现卖票
 * @Author sunsl
 * @Date 2022/5/18 21:03
 * @Version 1.0
 */
// 1.创建资源类
class LTicket{
    private int number = 30;   // 票的数量
    private final ReentrantLock lock = new ReentrantLock();// 定义可重入锁
    public void sale() { //卖票方法
        // 上锁
        lock.lock();

        try {
            // 逻辑
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出：" + (number--) + " 剩余：" + number);
            }
        } finally {
            /**
             * 防止在执行逻辑中出现异常导致退出而不释放锁，所以用try{}代码块
             */
            // 手动释放锁
            lock.unlock();
        }
    }
}

public class LSaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        // 创建多个线程
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "CC").start();
    }

}
