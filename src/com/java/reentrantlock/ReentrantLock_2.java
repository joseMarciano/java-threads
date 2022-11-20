package com.java.reentrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLock_2 {

    private static int i = -1;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            final Lock writeLock = lock.writeLock();
            writeLock.lock();
            System.out.println("Writing... " + i);
            i++;
            System.out.println("Wrote... " + i);
            writeLock.unlock();
        };
        Runnable r2 = () -> {
            final Lock readLock = lock.readLock();
            readLock.lock();
            System.out.print("Reading... ");
            System.out.println(i);
            System.out.println("Read..." + i);
            readLock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executorService.execute(r1);
            executorService.execute(r2);
        }

        executorService.shutdown();


    }
}
