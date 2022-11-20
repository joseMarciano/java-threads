package com.java.producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

/**
 * DEADLOCK example
 * In this code, the two threads will keep waiting for each other and in same point, they never will execute the if block code and will keep sleeping
 */
public class ProducerConsumer_1 {

    private static final List<Integer> LIST =
            new ArrayList<>(5);
    private static boolean needProduce = true;
    private static boolean needConsume = true;

    public static void main(String[] args) {
        final var producer = new Thread(() -> {
            wrapperTryCatch(unused -> {
                while (true) {
                    simulateProcess();
                    if (needProduce) {
                        System.out.println("Producing");
                        int number = new Random().nextInt(10000);
                        LIST.add(number);

                        if (LIST.size() == 5) {
                            System.out.println("Stopping Producer");
                            needProduce = false;
                        }
                        if (LIST.size() == 1) {
                            System.out.println("Initializing Consumer");
                            needConsume = true;
                        }
                    } else System.out.println("Consumer sleeping");

                }
            });
        });

        final var consumer = new Thread(() -> {
            wrapperTryCatch(unused -> {
                while (true) {
                    simulateProcess();
                    if (needConsume) {
                        System.out.println("Consuming");
                        Optional<Integer> number = LIST.stream().findFirst();
                        number.ifPresent(LIST::remove);

                        if (LIST.isEmpty()) {
                            System.out.println("Stopping Consumer");
                            needConsume = false;
                        }
                        if (!LIST.isEmpty()) {
                            System.out.println("Initializing Producer");
                            needProduce = true;
                        }

                    } else System.out.println("Consumer sleeping");
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
