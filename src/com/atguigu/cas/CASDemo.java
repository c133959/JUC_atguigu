package com.atguigu.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS
 *  比较，交换 compare and set
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        // main do thing

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());
        // false	 current data: 2019
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());
    }

}
