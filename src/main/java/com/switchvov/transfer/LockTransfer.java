package com.switchvov.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁实现
 *
 * @author switch
 * @since 2019/10/25
 */
public class LockTransfer extends Transfer {
    private static final Lock LOCK = new ReentrantLock();

    public LockTransfer(int balance) {
        super(balance);
    }

    @Override
    public void transfer(int amount) {
        LOCK.lock();
        try {
            balance += amount;
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public String toString() {
        return "balance = " + balance;
    }

    public static void main(String[] args) {
        LockTransfer transfer = new LockTransfer(0);
        transfer.testTransfer(10000, 1);
        System.out.println(transfer);
    }
}
