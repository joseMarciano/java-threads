package com.java.producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Critical Section
 */
public class ProducerConsumer_2 {

    private static final BlockingQueue<Integer> QUEUE =
            new LinkedBlockingQueue<>(5);
    private static volatile boolean needProduce = true;
    private static volatile boolean needConsume = true;

    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        final var producer = new Thread(() -> {
            wrapperTryCatch(unused -> {
                while (true) {
                    simulateProcess();
                    LOCK.lock();
                    if (needProduce) {
                        System.out.println("Producing");
                        int number = new Random().nextInt(10000);
                        QUEUE.add(number);

                        if (QUEUE.size() == 5) {
                            System.out.println("Stopping Producer");
                            needProduce = false;
                        }
                        if (QUEUE.size() == 1) {
                            System.out.println("Initializing Consumer");
                            needConsume = true;
                        }
                    } else System.out.println("Consumer sleeping");
                    LOCK.unlock();
                }
            });
        });

        final var consumer = new Thread(() -> {
            wrapperTryCatch(unused -> {
                while (true) {
                    simulateProcess();
                    LOCK.lock();
                    if (needConsume) {
                        System.out.println("Consuming");
                        Optional<Integer> number = QUEUE.stream().findFirst();
                        number.ifPresent(QUEUE::remove);

                        if (QUEUE.isEmpty()) {
                            System.out.println("Stopping Consumer");
                            needConsume = false;
                        }
                        if (!QUEUE.isEmpty()) {
                            System.out.println("Initializing Producer");
                            needProduce = true;
                        }

                    } else System.out.println("Consumer sleeping");
                    LOCK.unlock();
                }
            });
        });

        producer.start();
        consumer.start();
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

    private static void wrapperTryCatch(Consumer<Void> method) {
        try {
            method.accept(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
