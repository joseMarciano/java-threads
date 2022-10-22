package com.java.threads2.taskserver.br.com.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TaskServer {

    public static void main(String[] args) throws IOException {

        System.out.println("---- Server is initializing...");
        final var server = new ServerSocket(9809);


        while(true) {
            final var socket = server.accept();
            System.out.printf("New client is connected on port %s%n", socket.getPort());
        }



    }
}
