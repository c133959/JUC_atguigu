package com.atguigu.cas;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
@Data
@AllArgsConstructor
class User {
    String name;
    int age;
}
public class AtomicReferenceDemo {

    public static void main(String[] args) {

        User z3 = new User("zhangsan", 12);
        User li4 = new User("lisi", 23);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);// z3加入主物理内存
        // true	当前主物理内存中：User(name=lisi, age=23)
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t当前主物理内存中：" + atomicReference.get());
        // false 当前主物理内存中：User(name=lisi, age=23)
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t当前主物理内存中：" + atomicReference.get());

    }

}
