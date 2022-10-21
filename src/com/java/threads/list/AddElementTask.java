package com.java.threads.list;

import java.util.List;

public class AddElementTask implements Runnable {

    private final List<String> list;
    private final int threadNumber;

    public AddElementTask(final List<String> list, final int threadNumber) {
        this.list = list;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            list.add("Thread n°" + threadNumber + "| element n° " + i);
        }
    }
}
