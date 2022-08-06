package com.atguigu.ass;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: CountDownLatchDemo
 * @Description: TODO
 * @Author sunsl
 * @Date 2022/6/20 21:48
 * @Version 1.0
 */

public class CountDownLatchDemo {
    // 6位同学陆续离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() +"走出教室");

                //计数-1
                countDownLatch.countDown();

            }, String.valueOf(i)).start();
        }
        // 等待，直到计数器=0
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "锁门走人");
    }

}
