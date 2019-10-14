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
        int count = counter.incrementAndGet();
        int finishCount = count / 6;
        if ((finishCount == COUNT_LEVEL_1 || finishCount == COUNT_LEVEL_2 || finishCount == COUNT_LEVEL_3) && count % 6 == 0) {
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            System.out.println("遍历" + finishCount + "次，花费:" + time + "ms");
            countTimeMapping.put(finishCount, time);
            System.out.println(countTimeMapping);
        }
    }

}
