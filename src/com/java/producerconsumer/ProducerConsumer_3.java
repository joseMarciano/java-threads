package com.java.producerconsumer;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Critical Section
 */
public class ProducerConsumer_3 {

    private static final BlockingQueue<Integer> QUEUE =
            new LinkedBlockingQueue<>(5);

    public static void main(String[] args) {
        final Runnable producer = () -> {
            wrapperTryCatch(unused -> {
                simulateProcess();
                System.out.println("Producing");
                int number = new Random().nextInt(10000);
                put(number); // will put a number if have space, if not, keep waiting
            });
        };

        final Runnable consumer = () -> {
            wrapperTryCatch(unused -> {
                simulateProcess();
                System.out.println("Consuming");
                take(); // will remove a item of queue, if not have item, keep waiting
            });
        };

        final ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(2);

        scheduledExecutorService
                .scheduleWithFixedDelay(consumer, 0, 10, TimeUnit.MILLISECONDS);
        scheduledExecutorService
                .scheduleWithFixedDelay(producer, 0, 10, TimeUnit.MILLISECONDS);

    }




    private static void simulateProcess() {
        int time = new Random().nextInt(10);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void put(final int number) {
        try {
            QUEUE.put(number);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void take() {
        try {
            QUEUE.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void wrapperTryCatch(Consumer<Void> method) {
        try {
            method.accept(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
