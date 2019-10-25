package com.switchvov.transfer;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 硬件原语
 *
 * @author switch
 * @since 2019/10/25
 */
public class CASTransfer extends Transfer {
    private static Unsafe UNSAFE;
    private static long VALUE_OFFSET;

    static {
        try {
            //使用反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可存取
            field.setAccessible(true);
            //设置该变量的值
            UNSAFE = (Unsafe) field.get(null);
            //获取value偏移量
            VALUE_OFFSET = UNSAFE.objectFieldOffset(Transfer.class.getDeclaredField("balance"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public CASTransfer(int balance) {
        super(balance);
    }

    @Override
    public void transfer(int amount) {
        int oldValue;
        do {
            oldValue = UNSAFE.getIntVolatile(this, VALUE_OFFSET);
        } while (!UNSAFE.compareAndSwapInt(this, VALUE_OFFSET, oldValue, oldValue + amount));
    }

    @Override
    public String toString() {
        return "balance = " + balance;
    }

    public static void main(String[] args) {
        CASTransfer transfer = new CASTransfer(0);
        transfer.testTransfer(10000, 1);
        System.out.println(transfer);
    }
}
