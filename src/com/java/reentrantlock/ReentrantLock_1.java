package com.java.reentrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_1 {

    private static int i = -1;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            lock.lock();
            final String name = Thread.currentThread().getName();
            i++;
            System.out.println(name + " reading and incrementing: " + i);
            lock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executorService.execute(r1);
        }

        executorService.shutdown();


    }
}
