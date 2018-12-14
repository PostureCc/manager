//package com.chan.util.redis;
//
//
//import lombok.extern.slf4j.Slf4j;
//import redis.clients.jedis.Jedis;
//
//@Slf4j
//public class JedisUtil {
//
//    RedisConfig getJedis;
//
//    /**
//     * 设置key的有效期，单位是秒
//     *
//     * @param key
//     * @param exTime
//     * @return
//     */
//    public static Long expire(String key, int exTime) {
//        Jedis jedis = null;
//        Long result = null;
//        try {
//            jedis = RedisConfig.getJedis();
//            result = jedis.expire(key, exTime);
//        } catch (Exception e) {
//            log.error("expire key:{} error", key, e);
//            RedisConfig.returnResource(jedis);
//            return result;
//        }
//        RedisConfig.returnResource(jedis);
//        return result;
//    }
//
//    //给某个key设值
//    public void set(String key, Object value) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            byte[] valueBytes = serializer.serializeValue(value);
//            client.set(keyBytes, valueBytes);
//        } finally {
//            returnJedis(client);
//        }
//
//    }
//
//    //根据key获取value
//    public Object get(String key) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            byte[] valueBytes = client.get(keyBytes);
//            return serializer.deserializeValue(valueBytes);
//        } finally {
//            returnJedis(client);
//        }
//    }
//
//    //根据键值获取value
//    public Object hashGet(String key, String field) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            byte[] fieldBytes = serializer.serializeKey(field);
//            byte[] valueBytes = client.hget(keyBytes, fieldBytes);
//            return serializer.deserializeValue(valueBytes);
//        } finally {
//            returnJedis(client);
//        }
//
//    }
//
//    public void hashSet(String key, String field, Object value) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            byte[] fieldBytes = serializer.serializeKey(field);
//            byte[] valueBytes = serializer.serializeValue(value);
//            client.hset(keyBytes, fieldBytes, valueBytes);
//        } finally {
//            returnJedis(client);
//        }
//
//    }
//
//
//    public Map<String, Object> hashAllGet(String key) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            Map<byte[], byte[]> map = client.hgetAll(keyBytes);
//            Map<String, Object> valueMap = Maps.newHashMap();
//            Set<Map.Entry<byte[], byte[]>> valueSet = map.entrySet();
//            for (Map.Entry<byte[], byte[]> entry : valueSet) {
//                valueMap.put(serializer.deserializeKey(entry.getKey()), serializer.deserializeValue(entry.getValue()));
//            }
//            return valueMap;
//        } finally {
//            returnJedis(client);
//        }
//
//    }
//
//    //判断key是否存在
//    public boolean existKey(String key) {
//        Jedis client = getJedis();
//        try {
//            byte[] keyBytes = serializer.serializeKey(keyNamingPolicy.getKeyName(key));
//            return client.exists(keyBytes);
//        } finally {
//            returnJedis(client);
//        }
//    }
//}
