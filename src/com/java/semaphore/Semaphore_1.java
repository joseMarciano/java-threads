package com.java.semaphore;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore_1 {

    private static Semaphore SEMAPHORE = new Semaphore(3); // just three threads would be executed in parallel/concurrent

    public static void main(String[] args) {
        final ExecutorService executorService =
                Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String name =
                    Thread.currentThread().getName();
            int user =
                    new Random().nextInt(1000);

            aquire(); // if 3 threads is already be executing... will wait.
            System.out.println("User " + user + " is using thread" + name);
            release(); // release the current thread for another to be executed
        };

        for (int i = 0; i < 500; i++) {
            executorService.execute(r1);
        }

        executorService.shutdown();

    }

    private static void release() {
        SEMAPHORE.release();
    }

    private static void aquire() {
        try {
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
