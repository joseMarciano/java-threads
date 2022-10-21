package com.java.threads.bathroomdirty;

public class TaskNumberTwo implements Runnable {
    private final Bathroom bathroom;

    public TaskNumberTwo(final Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        bathroom.doNumberTwo();
    }
}
