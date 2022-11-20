package com.java.synchronousqueue;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueue_1 {

    private static final SynchronousQueue<String> QUEUE =
            new SynchronousQueue<>();

    public static void main(String[] args) {
        final ExecutorService executorService =
                Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            put("Testing...");
        };

        Runnable r2 = () -> {
            String value = take();
            System.out.println("Value received by another thread: " + value);
        };

        // if we not put item in this queue, the program will always keep waiting
        // if we not get a item, the program will always keep waiting too
        // So... we always need a thread waiting by value(take()) and a thread putting a value (put())
        executorService.execute(r1);
        executorService.execute(r2);

        executorService.shutdown();
    }

    private static String take() {
        try {
            return QUEUE.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return "Some problem occured";
        }
    }

    private static void put(String value) {
        try {
            QUEUE.put(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
