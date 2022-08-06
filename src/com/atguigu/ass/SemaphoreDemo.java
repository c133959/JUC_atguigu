package com.atguigu.ass;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 模拟6辆车停3个车位
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 创建semaphore初始化许可数量
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {

            new Thread(()->{

                try {
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() +"获得了车位");

                    // 停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    System.out.println(Thread.currentThread().getName() +"------离开了车位");


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }, String.valueOf(i)).start();

        }

    }

}
