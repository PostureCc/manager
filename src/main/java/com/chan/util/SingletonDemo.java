package com.chan.util;

import org.springframework.stereotype.Service;

@Service
public class SingletonDemo implements Runnable {

    private int num = 0;//出票数

    private int count = 10;//剩余票数

    public static volatile SingletonDemo singletonDemo;

    public static void SingletonDemo() {
        System.out.println("SingletonDemo...");
    }

    public SingletonDemo getSingletonDemo(SingletonDemo singletonDemo) {
        return new SingletonDemo();
    }

    public static SingletonDemo getSingletonDemo() {
        if (null == singletonDemo) {
            synchronized (SingletonDemo.class) {
                if (null == singletonDemo) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    @Override
    public void run() {
        while (true) {
            if (count <= 0) {
                break;
            }
            num++;
            count--;
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("出票信息：" + Thread.currentThread().getName() + "，第" + num + "张，剩余：" + count);
        }
    }
}
