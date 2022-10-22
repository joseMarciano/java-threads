package com.java.threads2.taskserver.br.com.server;

import java.io.PrintStream;

public class CommandThree implements Runnable {
    private final PrintStream output;

    public CommandThree(final PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("EXECUTING COMMAND 3");
        try {
            System.out.println("FINDING REGISTRY ON DATABASE | C3");
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        output.println("REGISTRY FOUND ON DATABASE | C3");
    }
}
