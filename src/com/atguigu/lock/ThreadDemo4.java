package com.atguigu.lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合线程安全
 */
public class ThreadDemo4 {

    public static void main(String[] args) {
        // 1.创建一个ArrayList（线程不安全）
        //List<String> list = new ArrayList<>();
        // 2.创建一个Vector（线程安全）
        //List<String> list = new Vector<>();
        // 3.Collections解决 返回指定列表支持的同步列表（线程安全）
        //List<String> list = Collections.synchronizedList(new ArrayList<>());

        // 2,3都比较古老，CopyOnWriteArrayList-写时复制技术
        List<String> list = new CopyOnWriteArrayList<>();
        /**
         * 观察其add()方法，并无synchronized修饰
         *
        public boolean add(E e) {
            ensureCapacityInternal(size + 1);  // Increments modCount!!
            elementData[size++] = e;
            return true;
        }*/
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                //向集合中添加内容
                list.add(UUID.randomUUID().toString().substring(0,8));
                //从集合中获取内容
                System.out.println(list);
            },String.valueOf(i)).start();
        }

        /*抛出ConcurrentModificationException并发修改问题
        Exception in thread "0" java.util.ConcurrentModificationException
        at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:911)
        at java.util.ArrayList$Itr.next(ArrayList.java:861)
        at java.util.AbstractCollection.toString(AbstractCollection.java:461)
        at java.lang.String.valueOf(String.java:2994)
        at java.io.PrintStream.println(PrintStream.java:821)
        at com.atguigu.lock.ThreadDemo4.lambda$main$0(ThreadDemo4.java:29)
        at java.lang.Thread.run(Thread.java:748)
        */

    }

}
