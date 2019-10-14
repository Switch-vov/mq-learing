package com.switchvov.network.chat.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.switchvov.network.chat.common.MsgConstant.*;

/**
 * @author switch
 * @since 2019/10/13
 */
public class MsgCounter {
    private static AtomicInteger counter = new AtomicInteger();
    private static Long startTime;
    private static final Object LOCK = new Object();
    private static Map<Integer, Long> countTimeMapping = new LinkedHashMap<>(3);

    public static void start() {
        if (MsgCounter.startTime == null) {
            synchronized (LOCK) {
                if (MsgCounter.startTime == null) {
                    MsgCounter.startTime = System.currentTimeMillis();
                }
            }
        }
    }

    public static void count() {
        start();
        int count = counter.incrementAndGet() / 6;
        if (count == COUNT_LEVEL_1 || count == COUNT_LEVEL_2 || count == COUNT_LEVEL_3) {
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            System.out.println("遍历" + count + "次，花费:" + time + "ms");
            countTimeMapping.put(count, time);
            System.out.println(countTimeMapping);
        }
    }

}
