package com.switchvov.transfer;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author switch
 * @since 2019/10/25
 */
public abstract class Transfer {
    protected volatile int balance;

    public Transfer(int balance) {
        this.balance = balance;
    }

    public void testTransfer(int count, int amount) {
        IntStream.range(0, count)
                .parallel()
                .mapToObj((index) -> CompletableFuture.runAsync(() -> transfer(amount)))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public abstract void transfer(int amount);

    @Override
    public String toString() {
        return "Transfer{" +
                "balance=" + balance +
                '}';
    }
}
