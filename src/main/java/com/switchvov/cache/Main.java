package com.switchvov.cache;

import com.alibaba.fastjson.JSON;
import com.switchvov.cache.linked.DuLinkListLruCache;
import com.switchvov.cache.priority.Priority;
import com.switchvov.cache.priority.PriorityLruCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author switch
 * @since 2019/10/24
 */
public class Main {
    private static final int CYCLE_COUNT = 10000;
    private static final int KEY_COUNT = 100;
    private static final int CAPACITY = 10;
    private static final int PRIORITY_RANDOM_BOUND = 10000;

    public static void main(String[] args) {
        testDuLink();
        testPriority();
    }

    private static void testDuLink() {
        Storage<String, String> cache = new DuLinkListLruCache<>(CAPACITY, key -> "value");

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < CYCLE_COUNT; i++) {
                System.out.println(cache.get("key" + i % KEY_COUNT));
            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < CYCLE_COUNT; i++) {
                System.out.println(cache.get("key" + i % KEY_COUNT));
            }
        });
        future1.join();
        future2.join();
    }

    private static void testPriority() {
        Storage<String, Priority> priorityCache = new PriorityLruCache<>(CAPACITY, key -> new Priority(ThreadLocalRandom.current().nextInt(PRIORITY_RANDOM_BOUND)));
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < CYCLE_COUNT; i++) {
                System.out.println(JSON.toJSONString(priorityCache.get("key" + i % KEY_COUNT)));
            }

        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < CYCLE_COUNT; i++) {
                System.out.println(JSON.toJSONString(priorityCache.get("key" + i % KEY_COUNT)));
            }

        });
        future1.join();
        future2.join();
    }
}
