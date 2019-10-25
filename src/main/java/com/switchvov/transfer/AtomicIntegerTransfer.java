package com.switchvov.transfer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类
 *
 * @author switch
 * @since 2019/10/25
 */
public class AtomicIntegerTransfer extends Transfer {
    private AtomicInteger balance;

    public AtomicIntegerTransfer(int balance) {
        super(balance);
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public void transfer(int amount) {
        balance.addAndGet(amount);
    }

    @Override
    public String toString() {
        return "balance = " + balance;
    }

    public static void main(String[] args) {
        AtomicIntegerTransfer transfer = new AtomicIntegerTransfer(0);
        transfer.testTransfer(10000, 1);
        System.out.println(transfer);
    }
}
