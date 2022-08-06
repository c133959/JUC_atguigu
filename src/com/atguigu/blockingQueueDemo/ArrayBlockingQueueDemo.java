package com.atguigu.blockingQueueDemo;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: ArrayBlockingQueueDemo
 * @Description: TODO
 * @Author sunsl
 * @Date 2022/7/2 21:02
 * @Version 1.0
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        // 阻塞队列
        /**
         * 抛出异常组 add remove
         * 但执行add方法，向已经满的ArrayBlockingQueue中添加元素时候，会抛出异常
         */
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(arrayBlockingQueue.add("1"));
        System.out.println(arrayBlockingQueue.add("2"));
        System.out.println(arrayBlockingQueue.add("3"));
        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        System.out.println(arrayBlockingQueue.add("4"));
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        // Exception in thread "main" java.util.NoSuchElementException
        System.out.println(arrayBlockingQueue.remove());

        /**
         * 布尔类型组 offer poll
         * 我们使用 offer的方法，添加元素时候，如果阻塞队列满了后，会返回false，否者返回true
         * 同时在取的时候，如果队列已空，那么会返回null
         */

        /**
         * 阻塞队列组 put take
         * 我们使用 put的方法，添加元素时候，如果阻塞队列满了后，添加消息的线程，会一直阻塞，直到队列元素减少，会被清空，才会唤醒
         * 一般在消息中间件，比如RabbitMQ中会使用到，因为需要保证消息百分百不丢失，因此只有让它阻塞
         */

        /**
         * 不见不散组
         * offer( ) ， poll 加时间
         * 使用offer插入的时候，需要指定时间，如果2秒还没有插入，那么就放弃插入
         */

    }


}
