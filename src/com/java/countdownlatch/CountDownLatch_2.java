package com.java.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatch_2 {

    private static volatile int i = 0;
    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        final ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i * j;
            System.out.println(i + " x " + j + " = " + x);
            latch.countDown();
        };
        Runnable r2 = () -> {
            await();
            i = new Random().nextInt();
        };
        Runnable r3 = () -> {
            await();
            latch =  new CountDownLatch(4);
        };
        Runnable r4 = () -> {
            await();
            System.out.println("Finished!! Lets start again!");
        };

        scheduledExecutorService
                .scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);
     scheduledExecutorService
                .scheduleWithFixedDelay(r2, 0, 1, TimeUnit.SECONDS);
     scheduledExecutorService
                .scheduleWithFixedDelay(r3, 0, 1, TimeUnit.SECONDS);
     scheduledExecutorService
                .scheduleWithFixedDelay(r4, 0, 1, TimeUnit.SECONDS);

        while (true) { // each 3 execution, the i variable will be updated
            await(); // keep waiting until the  latch.countDown() method decrements 3 times
            i = new Random().nextInt(100);
            latch = new CountDownLatch(3); // the disadvantage is, when CountDownLatch arrives to 0, we need to renew the class because the class is not reusable;
        }
    }

    private static void await()  {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
