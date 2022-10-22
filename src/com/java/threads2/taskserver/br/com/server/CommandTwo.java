package com.java.threads2.taskserver.br.com.server;

import java.io.PrintStream;

public class CommandTwo implements Runnable{
    private final PrintStream output;

    public CommandTwo(final PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("EXECUTING COMMAND 2");
        try {
            System.out.println("FINDING REGISTRY ON DATABASE | C2");
            Thread.sleep(15000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        output.println("REGISTRY FOUND ON DATABASE | C2");
    }
}
