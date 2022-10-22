package com.java.threads2.taskserver.br.com.server;

import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskServer {

    public static void main(String[] args) throws Exception {
        final var taskServer = new TaskServer();
        taskServer.run();
    }

    private final ServerSocket serverSocket;

    private final ExecutorService threadPool;

    private boolean isRunning;

    public TaskServer() throws Exception {
        System.out.println("---- Server is initializing...");
        this.serverSocket = new ServerSocket(9809);
        this.threadPool = Executors.newCachedThreadPool(); // create a threads on demand and close if don't need
        this.isRunning = false;

    }

    public void run() throws Exception {
        this.isRunning = true;
        while (this.isRunning) {
            try {
                final var socket = this.serverSocket.accept();
                System.out.printf("New client is connected on port %s%n", socket.getPort());

                final var distributeTaskByThread = new DistributeTask(socket, this);
                this.threadPool.execute(distributeTaskByThread);

            } catch (SocketException e) { // TODO: REVIEW
                System.out.println("Socket has been shut down -> isRunning " + this.isRunning);
            }
        }
    }

    public void stop() throws Exception {
        this.isRunning = false;
        this.serverSocket.close();
        this.threadPool.shutdown();
    }
}
