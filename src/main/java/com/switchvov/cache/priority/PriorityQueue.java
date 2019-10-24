package com.switchvov.cache.priority;

import java.util.Arrays;

/**
 * 最大优先队列
 *
 * @author switch
 * @since 2019/10/24
 */
public class PriorityQueue<T extends Comparable<T>> {

    private Object[] array;
    private int size;

    public PriorityQueue() {
        //队列初始长度32
        array = new Object[32];
    }

    /**
     * 入队
     *
     * @param key 入队元素
     */
    public void enque(T key) {
        //队列长度超出范围，扩容
        if (size >= array.length) {
            resize();
        }
        array[size++] = key;
        upAdjust();
    }


    /**
     * 出队
     */
    public T deque() {
        if (size <= 0) {
            throw new RuntimeException("the queue is empty !");
        }
        //获取堆顶元素
        T head = get(0);
        //最后一个元素移动到堆顶
        array[0] = array[--size];
        downAdjust();
        return head;
    }

    @SuppressWarnings("unchecked")
    private T get(int index) {
        return (T) array[index];
    }


    /**
     * 上浮调整
     */
    private void upAdjust() {
        int childIndex = size - 1;
        int parentIndex = childIndex / 2;
        // temp保存插入的叶子节点值，用于最后的赋值
        T temp = get(childIndex);
        while (childIndex > 0 && temp.compareTo(get(parentIndex)) > 0) {
            //无需真正交换，单向赋值即可
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = parentIndex / 2;
        }
        array[childIndex] = temp;
    }


    /**
     * 下沉调整
     */
    private void downAdjust() {
        // temp保存父节点值，用于最后的赋值
        int parentIndex = 0;
        T temp = get(parentIndex);
        int childIndex = 1;
        while (childIndex < size) {
            // 如果有右孩子，且右孩子大于左孩子的值，则定位到右孩子
            if (childIndex + 1 < size && get(childIndex + 1).compareTo(get(childIndex)) > 0) {
                childIndex++;
            }
            // 如果父节点大于任何一个孩子的值，直接跳出
            if (temp.compareTo(get(childIndex)) >= 0) {
                break;
            }
            //无需真正交换，单向赋值即可
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[parentIndex] = temp;
    }


    /**
     * 扩容调整
     */
    private void resize() {
        //队列容量翻倍
        int newSize = this.size * 2;
        this.array = Arrays.copyOf(this.array, newSize);
    }

    @Override
    public String toString() {
        return "PriorityQueue{" +
                "array=" + Arrays.toString(array) +
                ", size=" + size +
                '}';
    }
}
