package com.java.threads.list;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final var list = new Vector<String>(); // Vector is an array list with synchronized methods

        for (int index = 0; index < 10; index++) {
            new Thread(new AddElementTask(list, index)).start();
        }

        Thread.sleep(2000);

        for (String s : list) {
            System.out.println(s);
        }

    }
}
