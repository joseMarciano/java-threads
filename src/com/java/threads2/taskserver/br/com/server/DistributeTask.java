package com.java.threads2.taskserver.br.com.server;

import java.net.Socket;

public class DistributeTask implements Runnable {
    private final Socket socket;

    public DistributeTask(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.printf("Distributing task in separated thread for socket %s%n", this.socket.getPort());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
