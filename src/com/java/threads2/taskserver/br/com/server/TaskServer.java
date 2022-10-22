package com.java.threads2.taskserver.br.com.server;

import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskServer {

    public static void main(String[] args) throws Exception {
        final var taskServer = new TaskServer();
        taskServer.run();
    }

    private final ServerSocket serverSocket;

    private final ExecutorService threadPool;

//    private volatile boolean isRunning;

    private AtomicBoolean isRunning; // wrapper for volatile boolean;

    public TaskServer() throws Exception {
        System.out.println("---- Server is initializing...");
        this.serverSocket = new ServerSocket(9809);
        this.threadPool = Executors.newCachedThreadPool(); // create a threads on demand and close if don't need
        this.isRunning = new AtomicBoolean(false);

    }

    public void run() throws Exception {
        this.isRunning.set(true);
        while (this.isRunning.get()) {
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
        this.isRunning.set(false);
        this.serverSocket.close();
        this.threadPool.shutdown();
    }
}
