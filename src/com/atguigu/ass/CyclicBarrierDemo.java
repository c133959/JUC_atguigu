package com.atguigu.ass;

/**
 * @ClassName: CyclicBarrierDemo
 * @Description: TODO
 * @Author sunsl
 * @Date 2022/6/21 20:59
 * @Version 1.0
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 集齐七科龙珠
 */
public class CyclicBarrierDemo {
    private static final int NUMBER = 7;

    public static void main(String[] args) {
        //创建CyclicBarrier
        CyclicBarrier cyclicBarrier =
                new CyclicBarrier(NUMBER, ()->{
                    System.out.println("7颗龙珠被找到");
                });
        for (int i = 1; i <= 7; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "颗龙珠被找到");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }

}
