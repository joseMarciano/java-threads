package com.java.cyclicbarrier;

import java.util.Objects;
import java.util.concurrent.*;

public class CyclicBarrier_3 {

    private static BlockingQueue<Double> results =
            new LinkedBlockingQueue<>();

    private static ExecutorService executorService;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;

    // (432 * 3) + (3^14) + (45*127/12)
    public static void main(String[] args) {
        Runnable summarizer = () -> {
            System.out.println("Summarizing all");
            final Double sum = results.stream()
                    .filter(Objects::nonNull)
                    .reduce(Double::sum)
                    .orElse(0.0);
            results.clear();
            System.out.println("Result: " + sum);
            System.out.println(" --------------- ");
            restart(); // resubmit runnable in the executor
        };


        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, summarizer);
        executorService = Executors.newFixedThreadPool(3);

        r1 = () -> {
            results.add(432d * 3d);
            await(cyclicBarrier);
        };

        r2 = () -> {
            results.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
        };

        r3 = () -> {
            results.add(45d * 127d / 12d);
            await(cyclicBarrier);
        };


        restart();
//        executorService.shutdown(); THE SHUTDOWN is never called to keep application running
    }

    private static void await(final CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void restart() {
        try {
            Thread.sleep(1000);
            executorService.submit(r1);
            executorService.submit(r2);
            executorService.submit(r3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
