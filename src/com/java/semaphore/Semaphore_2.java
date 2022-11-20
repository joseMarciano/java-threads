package com.java.semaphore;


import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Semaphore_2 {

    private static final Semaphore SEMAPHORE = new Semaphore(20); // just twenty threads would be executed in parallel/concurrent
    private static final AtomicInteger PENDING_TASKS_QTY = new AtomicInteger();

    public static void main(String[] args) {
        final ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(600);

        Runnable r1 = () -> {
            String name =
                    Thread.currentThread().getName();
            int user =
                    new Random().nextInt(1000);

            boolean gotIt = tryAquire(); // if not got a vacancy, will try again until get it
            PENDING_TASKS_QTY.incrementAndGet();
            while (!gotIt) {
                gotIt = tryAquire();
            }
            System.out.println("User " + user + " is using thread" + name);
            sleep();
            release(); // release the current thread for another to be executed
            PENDING_TASKS_QTY.decrementAndGet();
        };

        Runnable r2 = () -> {
            final int qtyPendingTasks = PENDING_TASKS_QTY.get();
            System.out.println(qtyPendingTasks);

            if (qtyPendingTasks <= 0)
                scheduledExecutorService.shutdown();

        };

        for (int i = 0; i < 500; i++) {
            scheduledExecutorService.execute(r1);
        }

        scheduledExecutorService.scheduleWithFixedDelay(r2, 0, 100, TimeUnit.MILLISECONDS);

//        scheduledExecutorService.shutdown();

    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void release() {
        SEMAPHORE.release();
    }

    private static boolean tryAquire() {
        try {
            return SEMAPHORE.tryAcquire(1, TimeUnit.SECONDS); // will try for a vacancy for 1 second. If get it return true, if not, return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return false;
        }
    }
}
