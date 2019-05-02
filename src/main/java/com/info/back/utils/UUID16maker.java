package com.info.back.utils;


import lombok.extern.slf4j.Slf4j;
/**
 * 随机生成16位数的uuid
 *
 */
@Slf4j
public class UUID16maker {

    private final long workerId;

    private final static long twepoch = 1288834974657L;

    private long sequence = 0L;

    private final static long workerIdBits = 4L;

    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;

    private final static long sequenceBits = 10L;

    private final static long workerIdShift = sequenceBits;

    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    @SuppressWarnings("static-access")
    public UUID16maker(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    @SuppressWarnings("static-access")
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        this.lastTimestamp - timestamp));
            } catch (Exception e) {
                log.error("nextId error:{}",e);
            }

        }
        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << this.workerIdShift)
                | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    /**
     * 
     * @return l 16位uuid数字
     */
    public static long getUUID16Id() {
        UUID16maker uuid16maker = new UUID16maker(1);
        long l = uuid16maker.nextId();
        log.info("=========>uuid16位数字:{}" ,l);
        return l;
    }
}
