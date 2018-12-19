package com.chan.util.id;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高。
 * 1符号位 + 41位时间戳 + 6位机器位 + 4位线程区域位 + 12位序列号
 */
public class SnowflakeIdWorker {

    static Logger LOG = LoggerFactory.getLogger(SnowflakeIdWorker.class);

    public static final ThreadLocal<SnowflakeIdWorker> snowflackIdWorkers = new ThreadLocal<SnowflakeIdWorker>() {
        @Override
        protected SnowflakeIdWorker initialValue() {
            System.setProperty("current_node_num","0");
            if(System.getProperty("current_node_num") == null || !NumberUtils.isDigits(System.getProperty("current_node_num"))){
                LOG.warn("未获取到当前应用节点编号");
                Runtime.getRuntime().exit(-1);
            }
            SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(Long.parseLong(System.getProperty("current_node_num")));
            return snowflakeIdWorker;
        }
    };
    /**
     * 机器id所占的位数
     */
    private static final long workerIdBits = 6L;
    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    public static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 线程标识id所占的位数
     */
    private static final long threadSegmentBits = 4L;
    /**
     * 支持的最大线程标识id，结果是31
     */
    private static final long threadSegmentCnt = 1L << threadSegmentBits;
    private static LockAndSequenceWrapper[] locks = new LockAndSequenceWrapper[(int) threadSegmentCnt];
    /**
     * 开始时间戳 (2015-01-01)
     */
    private final long twepoch = 1420041600000L;
    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;
    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long threadSegmentShift = sequenceBits;


    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits + threadSegmentBits;

    /**
     * 时间戳向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + threadSegmentBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private volatile LockAndSequenceWrapper lockWrapper;
    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 线程ID模(0~31)
     */
    private long threadSegment;

    /**
     * 构造函数
     *
     * @param workerId 工作ID (0~31)
     */
    private SnowflakeIdWorker(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long threadSegment = Thread.currentThread().getId() % threadSegmentCnt;
        this.workerId = workerId;
        this.threadSegment = threadSegment;
        synchronized (SnowflakeIdWorker.class) {
            if (null == locks[(int) threadSegment]) {
                locks[(int) threadSegment] = new LockAndSequenceWrapper();
            }
            this.lockWrapper = locks[(int) threadSegment];
        }
    }

    public static void main(String[] args) {
        final int cntPerThread = 10000000;
        System.setProperty("current_node_num","0");
        final ConcurrentHashMap<Long, Integer> concurrentHashMap = new ConcurrentHashMap<Long, Integer>((int) threadSegmentCnt * cntPerThread + 1);
        Thread[] threads = new Thread[(int) threadSegmentCnt];
        boolean isCheckDuplicate = false;

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread() {
                public void run() {
                    SnowflakeIdWorker snowflakeIdWorker = snowflackIdWorkers.get();
                    for (int i = 0; i < cntPerThread; i++) {
                        Long s = snowflakeIdWorker.nextId();
                        if(isCheckDuplicate){
                            concurrentHashMap.put(s, null == concurrentHashMap.get(s) ? 1 : concurrentHashMap.get(s) + 1);
                        }
                    }
                }
            };
        }
        long start = System.nanoTime();
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.nanoTime();
        System.out.println("生成:" +cntPerThread * threads.length+"个;\t总时长:" + NumberFormat.getNumberInstance().format((end - start)/1000000)+"毫秒");
        int duplicated = 0;
        for (Long key : concurrentHashMap.keySet()) {
            if (concurrentHashMap.get(key) > 1) {
                System.out.println(key + "\t\t" + Long.toBinaryString(key) + "\t\t个数:" + concurrentHashMap.get(key));
                duplicated += (concurrentHashMap.get(key) - 1);
            }
        }
        System.out.println("总线程数:" + threads.length + "\t共" + concurrentHashMap.size() + "个\t重复个数:" + duplicated);
    }

    public long nextId() {
        return this.nextId(null);
    }

    public long nextId(StringBuilder sb) {
        while (true) {
            if (lockWrapper.tryLock()) {
                try {
                    long timestamp = timeGen();
                    AtomicLong sequence = lockWrapper.getSequence();
                    //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
                    if (timestamp < lockWrapper.lastTimestamp) {
                        throw new RuntimeException(
                                String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lockWrapper.lastTimestamp - timestamp));
                    }

                    if (lockWrapper.lastTimestamp == timestamp) {
                        sequence.compareAndSet(sequence.get(), (sequence.get() + 1) & sequenceMask);
                        if (sequence.get() == 0) {
                            timestamp = tilNextMillis(lockWrapper.lastTimestamp);
                        }
                    } else {
                        sequence.compareAndSet(sequence.get(), 0);
                    }
                    lockWrapper.lastTimestamp = timestamp;
                    return ((timestamp - twepoch) << timestampLeftShift)
                            | (workerId << workerIdShift)
                            | (threadSegment << threadSegmentShift)
                            | sequence.get();
                } finally {
                    lockWrapper.unlock();
                }
            }
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    class LockAndSequenceWrapper extends ReentrantLock {

        /**
         * 上次生成ID的时间戳
         */
        private long lastTimestamp = -1L;

        private AtomicLong sequence = new AtomicLong(0);

        public LockAndSequenceWrapper() {
            super(false);
        }

        public Thread getOwner() {
            return super.getOwner();
        }


        public AtomicLong getSequence() {
            return sequence;
        }

        public long getLastTimestamp() {
            return lastTimestamp;
        }
    }
}