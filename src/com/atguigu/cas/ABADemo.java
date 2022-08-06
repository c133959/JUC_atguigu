package com.atguigu.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    /**
     * 第一次版本号：	1
     * 第二次版本号：	2
     * 第三次版本号：	3
     * t2	修改成功？：false	当前实际版本号：3
     * t2	当前实际值：100
     */

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("第一次版本号：\t" + stamp);

            // 暂停一秒，等待t2获取版本号，因为此时修改会修改版本号，为了模拟ABA问题，暂停等待两线程都拿到相同版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("第二次版本号：\t" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("第三次版本号：\t" + atomicStampedReference.getStamp());

        },"t1").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();

            // 等待4秒等待t1所有的操作做完
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean res = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功？：" + res + "\t当前实际版本号：" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际值：" + atomicStampedReference.getReference());

        },"t2").start();
    }
}
