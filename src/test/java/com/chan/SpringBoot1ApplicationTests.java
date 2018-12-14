package com.chan;

import com.alibaba.fastjson.JSON;
import com.chan.util.SingletonDemo;
import com.chan.util.redis.RedisConfig;
import com.chan.util.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot1ApplicationTests {

    @Autowired
    private SingletonDemo singletonDemo;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    @Qualifier("redisUtil")
    RedisUtil redisUtil;

    class MyThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.err.println(Thread.currentThread().getName() + i);
            }
        }
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void threadDemo3() {
        int count = 50;
        //利用发令枪操作
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread() {
                int j = finalI;
                public void run() {
//                    System.err.println(System.currentTimeMillis() + ":" + finalI + "---"+redisUtil.get("USER_chan"));
                }
            }.start();
            countDownLatch.countDown();
        }
        try {
            //使线程在锁存器倒计数至零之前一直等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadDemo2() {
        //启动同时去抢占cpu，有可能产生并发
        int count = 50;
        //利用发令枪操作
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread() {
                int j = finalI;
                public void run() {
                    //展示时间戳+引用的单例
                    //以下Singleton4为之前博客写的用静态类实现的那种
                    System.err.println(System.currentTimeMillis() + ":" + finalI + "---"+redisUtil.get("USER_chan"));
                }
            }.start();
            //递减锁存器的计数，如果计数到达零，则释放所有等待的线程
            countDownLatch.countDown();
        }
        try {
            //使线程在锁存器倒计数至零之前一直等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadDemo1() {
        //启动同时去抢占cpu，有可能产生并发
        int count = 60;

        //利用发令枪操作
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread() {
                public void run() {

                    //展示时间戳+引用的单例
                    //以下Singleton4为之前博客写的用静态类实现的那种
                    System.err.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName());
                }
            }.start();
            //递减锁存器的计数，如果计数到达零，则释放所有等待的线程
            countDownLatch.countDown();
        }
        try {
            //使线程在锁存器倒计数至零之前一直等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10 ; i++) {
            System.err.println(redisTemplate);
        }
    }

    //    @Test
    public void Sort() {
        Integer[] arr = {1, 3, 2};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = 0; k < arr.length - 1 - i; k++) {
                if (arr[k] > arr[k + 1]) {
                    int temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                }
            }
        }

        for (int i : arr) {
            System.out.println("i:" + arr[i - 1]);
        }

        Integer[] array = {2, 1, 3};
//        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(arr.toString());
        List<Integer> jsonArray = Arrays.asList(array);
        jsonArray.sort((Integer i, Integer b) -> i.compareTo(b));
        for (Integer integer : jsonArray) {
            System.out.println(integer);
        }
    }

    //    @Test
    public void contextLoads() {
        for (int i = 0; i < 10; i++) {
            System.out.println(singletonDemo.getSingletonDemo(null));
        }

        System.out.println("\n=======\n");

        for (int i = 0; i < 10; i++) {
            System.out.println(singletonDemo.getSingletonDemo());
        }

    }

}
