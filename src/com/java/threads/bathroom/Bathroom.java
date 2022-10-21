package com.java.threads.bathroom;

public class Bathroom {

    public void doNumberOne() {
        final var threadName = Thread.currentThread().getName();

        System.out.printf("%s is knocking%n", threadName);

        synchronized (this) {
            System.out.printf("Getting in bathroom - %s%n", threadName);
            System.out.printf("Doing fast thing - %s%n", threadName);


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Washing hands - %s%n", threadName);
            System.out.printf("Getting out - %s%n", threadName);
        }
    }

    public void doNumberTwo() {
        final var threadName = Thread.currentThread().getName();
        System.out.printf("%s is knocking%n", threadName);

        synchronized (this) {
            System.out.printf("Getting in bathroom - %s%n", threadName);
            System.out.printf("Taking time doing things - %s%n", threadName);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Washing hands - %s%n", threadName);
            System.out.printf("Getting out - %s%n", threadName);
        }

    }
}
