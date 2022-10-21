package com.java.threads.bathroomdirty;

public class CleanTask  implements Runnable {

    private final Bathroom bathroom;
    public CleanTask(final Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        this.bathroom.clear();
    }
}
