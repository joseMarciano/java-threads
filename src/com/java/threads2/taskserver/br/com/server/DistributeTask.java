package com.java.threads2.taskserver.br.com.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistributeTask implements Runnable {
    private final Socket socket;

    public DistributeTask(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Distributing task in separated thread for socket %s%n", this.socket.getPort());
            final var inputClient = new Scanner(socket.getInputStream());
            final var outputClient = new PrintStream(socket.getOutputStream());

            while (inputClient.hasNextLine()) {
                final var command = inputClient.nextLine();
                System.out.printf("RECEIVED OF %s -> %s%n", this.socket.getPort(), command);

                extracted(outputClient, command);

            }

            inputClient.close();
            outputClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static void extracted(final PrintStream outputClient, final String command) {
        switch (command) {
            case "c1" -> outputClient.println("COMMAND C1 RECEIVED");
            case "c2" -> outputClient.println("COMMAND C2 RECEIVED");
            default -> outputClient.println("COMMAND NOT FOUND");
        }
    }
}
