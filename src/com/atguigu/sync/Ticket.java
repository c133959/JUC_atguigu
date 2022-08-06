package com.atguigu.sync;

/**
 * @ClassName: Ticket 售票案例-资源类：票
 * @Description: 多线程模拟售票
 * @Author sunsl
 * @Date 2022/4/26 22:14
 * @Version 1.0
 */
public class Ticket {
    // 模拟系统余票
    private int number = 30;
    // 操作方法：卖票
    public synchronized void sale() {
        // 先判断是否有余票
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出：" + (number--) + " 剩余：" + number);
        }
    }
}


