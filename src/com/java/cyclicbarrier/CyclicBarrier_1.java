package com.java.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrier_1 {

    // (432 * 3) + (3^14) + (45*127/12)
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3); // number of parties(threads)
        final ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(432d*3d);
            await(cyclicBarrier); // await until all 3 threads arrives in await.
            System.out.println("Finished r1");
        };

        Runnable r2 = () -> {
            System.out.println(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println("Finished r2");
        };

        Runnable r3 = () -> {
            System.out.println(45d*127d/12d);
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
