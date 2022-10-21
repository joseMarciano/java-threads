package com.java.threads.bathroomdirty;

public class TaskNumberOne implements Runnable {
    private final Bathroom bathroom;

    public TaskNumberOne(final Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        bathroom.doNumberOne();
    }
}
