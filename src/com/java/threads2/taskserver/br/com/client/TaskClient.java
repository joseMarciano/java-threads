package com.java.threads2.taskserver.br.com.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TaskClient {

    public static void main(String[] args) throws IOException {
        final var socket = new Socket("localhost", 9809);
        System.out.println("--- client connected successfully");

        final var keyBoard = new Scanner(System.in);
        keyBoard.nextLine();


        socket.close();
    }

}
