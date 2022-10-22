package com.java.threads2.taskserver.br.com.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DistributeTask implements Runnable {
    private final Socket socket;
    private final TaskServer taskServer;
    private final ExecutorService threadPool;

    public DistributeTask(final Socket socket, final TaskServer taskServer, final ExecutorService threadPool) {
        this.socket = socket;
        this.taskServer = taskServer;
        this.threadPool = threadPool;
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

    private void extracted(final PrintStream outputClient, final String command) throws Exception {
        switch (command) {
            case "c1" -> threadPool.execute(new CommandOne(outputClient));
            case "c2" -> threadPool.execute(new CommandTwo(outputClient));
            case "c3" -> threadPool.execute(new CommandThree(outputClient));
            case "shut down" -> {
                outputClient.println("Shutting down server");
                this.taskServer.stop();
            }
            default -> outputClient.println("COMMAND NOT FOUND");
        }
    }
}
