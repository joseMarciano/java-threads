package com.java.threads2.taskserver.br.com.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TaskClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        final var socket = new Socket("localhost", 9809);
        System.out.println("--- client connected successfully");

        final var threadToSendCommand = new Thread(() -> {
            try {

                final var clientOutputStream = socket.getOutputStream();
                final var output = new PrintStream(clientOutputStream);
                final var keyBoard = new Scanner(System.in);
                while (keyBoard.hasNextLine()) {
                    final var line = keyBoard.nextLine();

                    if (line.trim().equals("")) break;

                    output.println(line);
                }

                output.close();
                keyBoard.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final var threadToReceiveCommandFromServer = new Thread(() -> {
            try {
                final var clientInputStream = socket.getInputStream();
                final var input = new Scanner(clientInputStream);

                while (input.hasNextLine()) {
                    final var line = input.nextLine();

                    System.out.printf("RETURNED BY SERVER -> %s%n", line);
                }

                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadToReceiveCommandFromServer.start();
        threadToSendCommand.start();

        threadToSendCommand.join(); // this command tell to main thread wait this thread to continue to the nextline.

        System.out.println("Closing socket of client...");
        socket.close();

    }

}
