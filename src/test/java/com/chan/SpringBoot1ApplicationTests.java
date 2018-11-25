package com.chan;

import com.alibaba.fastjson.JSON;
import com.chan.util.SingletonDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot1ApplicationTests {

    @Autowired
    private SingletonDemo singletonDemo;

    @Test
    public void Sort(){
        Integer[] arr = {1,3,2};
        for(int i = 0; i < arr.length -1; i++){
            for(int k = 0; k < arr.length - 1 - i; k ++){
                if(arr[k] > arr[k+1]){
                    int temp = arr[k];
                    arr[k] = arr[k+1];
                    arr[k+1] = temp;
                }
            }
        }

        for (int i : arr) {
         System.out.println("i:"+arr[i-1]);
        }

        Integer[] array = {2,1,3};
//        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(arr.toString());
        List<Integer> jsonArray = Arrays.asList(array);
        jsonArray.sort((Integer i,Integer b)->i.compareTo(b));
        for (Integer integer : jsonArray) {
            System.out.println(integer);
        }
    }

    @Test
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
