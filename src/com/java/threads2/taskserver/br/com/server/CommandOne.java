package com.java.threads2.taskserver.br.com.server;

import java.io.PrintStream;

public class CommandOne implements Runnable{
    private final PrintStream output;

    public CommandOne(final PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("EXECUTING COMMAND 1");
        try {
            System.out.println("EXECUTING COMPLEX PROCESS | C1");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        output.println("COMPLEX PROCESS EXECUTED | C1");
    }
}
