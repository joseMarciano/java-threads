package com.java.cyclicbarrier;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * Cyclic Barrier is a way to synchronize threads and aggregate the value in another thread or do anything else
 */
public class CyclicBarrier_2 {

    private static BlockingQueue<Double> results =
            new LinkedBlockingQueue<>();

    // (432 * 3) + (3^14) + (45*127/12)
    public static void main(String[] args) {
        Runnable finalizer = () -> { // this runnable will execute when all threads were called
            System.out.println("Summarizing all");
            final Double sum = results.stream()
                    .filter(Objects::nonNull)
                    .reduce(Double::sum)
                    .orElse(0.0);
            results.clear();
            System.out.println("Result: " + sum);
        };


        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finalizer); // number of parties(threads)
        final ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            results.add(432d * 3d);
            await(cyclicBarrier); // await until all 3 threads arrives in await.
            System.out.println("Finished r1");
        };

        Runnable r2 = () -> {
            results.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println("Finished r2");
        };

        Runnable r3 = () -> {
            results.add(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println("Finished r3");
        };


        executorService.submit(r1);
        executorService.submit(r2);
        executorService.submit(r3);

        executorService.shutdown();
    }

    private static void await(final CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
