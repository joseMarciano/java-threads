package com.java.synchronousqueue;


import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exchanger_1 {

    private static final Exchanger<String> EXCHANGER =
            new Exchanger<>();

    public static void main(String[] args) {
        final ExecutorService executorService =
                Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            final String msg = "Take it";
            String returnValue = exchange(msg);
            System.out.println("Message sent by : " + name + " is " + msg + " | Message returned: " + returnValue);
        };

        Runnable r2 = () -> {
            String name = Thread.currentThread().getName();
            final String msg = "Thanks!!";
            String returnValue = exchange(msg);
            System.out.println("Message sent by : " + name + " is " + msg + " | Message returned: " + returnValue);
        };

        // we need to exchange a value through threads, if not, the program will waiting and never will stop
        executorService.execute(r1);
        executorService.execute(r2);

        executorService.shutdown();
    }

    private static String exchange(String value) {
        try {
            return EXCHANGER.exchange(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return "Some problem occurred";
        }
    }
}
