package com.chan.util.id;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.util.UUID;

/**
 * JDK提供一个自动生成主键的方法
 */
public class UUIDWork {

    /**
     * 普通获取(截取后的为长度三十二位)
     */
    public static String getUUID() {
        System.out.println("UUID Version:" + UUID.randomUUID().version());
        String id = UUID.randomUUID().toString().replace("-", "");
        return id;
    }

    /**
     * 基于时间戳获取(截取后的为长度三十二位)
     *
     */
    public static String getUUIDByTime() {
        TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        UUID uuid = gen.generate();
        System.out.println("UUID Version:" + uuid.version());
        String str = uuid.toString().replace("-", "");
        return str;
    }

    public static void main(String[] agrs) {
//        for (int i = 0; i < 10; i++) {
//            String str = getUUID();
//            System.out.println(String.format("id:%s,length:%s", str, str.length()));
//        }

        for (int i = 0; i < 10; i++) {
            String str = getUUIDByTime();
            System.out.println(String.format("id:%s,length:%s", str, str.length()));
        }
    }

}
