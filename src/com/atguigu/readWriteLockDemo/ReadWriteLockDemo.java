package com.atguigu.readWriteLockDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 多个线程同时读，没有问题，为了满足高并发，应该允许
 * 但是，如果一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写
 *
 * 读-读：能共存
 * 读-写：不能共存
 * 写-写：不能共存
 *
 * 写操作：理应为原子+独占，整个过程必须是一个完整的统一体，中间不许被分割、被打断
 *
 */

// 资源类
class MyCache{
    /**
     * 缓存中的东西，必须保持可见性，因此使用volatile修饰
     */
    private volatile Map<String, Object> map = new HashMap<>();

    /**
     * 创建一个读写锁
     * 它是一个读写融为一体的锁，在使用的时候，需要转换
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        // 创建写锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入：" + key);

            // 模拟网络拥堵，暂停0.3秒
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        } finally {
            // 释放锁
            readWriteLock.writeLock().unlock();
        }
    }

    //这里的读锁和写锁的区别在于，写锁一次只能一个线程进入，执行写操作，而读锁是多个线程能够同时进入，进行读取的操作

    public void get(String key) {
        // 创建读锁
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取：" + key);
            // 模拟网络拥堵，暂停0.3秒
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + o);
        } finally {
            // 释放读锁
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 清空缓存
     */
    public void clean() {
        map.clear();
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //5线程写
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt + "", tempInt + "");
            },String.valueOf(i)).start();
        }
        //5线程读
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt + "");
            },String.valueOf(i)).start();
        }
    }
    /** 运行结果-不用读写锁
     * 0	正在写入：0
     * 1	正在写入：1
     * 3	正在写入：3
     * 2	正在写入：2
     * 4	正在写入：4
     * 0	正在读取：0
     * 2	正在读取：2
     * 1	正在读取：1
     * 3	正在读取：3
     * 4	正在读取：4
     * 4	 读取完成：null
     * 3	 读取完成：null
     * 4	写入完成
     * 2	 读取完成：null
     * 0	 读取完成：null
     * 1	 读取完成：null
     * 2	写入完成
     * 0	写入完成
     * 3	写入完成
     * 1	写入完成
     * 我们可以看到，在写入的时候，写操作都被其它线程打断了(没有保证原子性）
     * 这就造成了，还没写完，其它线程又开始写，这样就造成数据不一致
     *
     * 加Lock:是可以保证原子性，同一时间只有一个线程独占，但是无法保证并发读
     *
     * 读写锁：ReentrantReadWriteLock
     * 保证写时只有一个线程在写（共享资源）
     * 在读时可以保证多线程的并发
     *
     */
}
