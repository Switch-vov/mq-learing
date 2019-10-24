package com.switchvov.cache.priority;

/**
 * 最大优先队列
 * 
 * @author switch
 * @since 2019/10/24
 */
public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        System.out.println(priorityQueue);
        priorityQueue.enque(3);
        System.out.println(priorityQueue);
        priorityQueue.enque(5);
        System.out.println(priorityQueue);
        priorityQueue.enque(10);
        System.out.println(priorityQueue);
        priorityQueue.enque(2);
        System.out.println(priorityQueue);
        priorityQueue.enque(7);
        System.out.println(priorityQueue);
        System.out.println("出队元素：" + priorityQueue.deque());
        System.out.println(priorityQueue);
        System.out.println("出队元素：" + priorityQueue.deque());
        System.out.println(priorityQueue);
    }
}
