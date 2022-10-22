package com.java.threads2.taskserver.br.com.server;

import java.io.IOException;
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

            while (inputClient.hasNextLine()) {
                final var command = inputClient.nextLine();
                System.out.printf("RECEIVED OF %s -> %s%n", this.socket.getPort(), command);
            }

            inputClient.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
