package com.atguigu.sync;

/**
 * @ClassName: SaleTicket 售票-方法类：售票
 * @Description: 多线程模拟售票流程
 * @Author sunsl
 * @Date 2022/4/26 22:28
 * @Version 1.0
 */
public class SaleTicket {
    // 创建多个线程，调用资源的操作方法
    public static void main(String[] args) {
        // 创建Ticket对象
        Ticket ticket = new Ticket();
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
