package com.atguigu.reentranDemo;
/**
 * 资源类
 * 验证synchronized是可重入锁
 *      同一个线程在外层函数获得锁之后，其内层的递归函数仍然能够获得该锁的代码
 *      即，线程可以进入到所获得的锁 所同步的代码块
 */
class Phone {
    // 发送短信
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoke sendSMS()");
        // 调用另一个同步方法
        sendMail();
    }
    // 发送邮件
    public synchronized void sendMail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoke sendMail()");
    }
}
public class synDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        // 定义两个线程操作资源
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
        /**
         * 执行结果：
         * t1	 invoke sendSMS()
         * t1	 invoke sendMail()
         * t2	 invoke sendSMS()
         * t2	 invoke sendMail()
         */

    }
}
