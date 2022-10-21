package com.java.threads.bathroomdirty;

public class Main {

    public static void main(String[] args) {

        final var bathroom = new Bathroom();

        final var guestOne = new Thread(new TaskNumberOne(bathroom), "John");
        final var guestTwo = new Thread(new TaskNumberTwo(bathroom), "Mark");
        final var cleaner = new Thread(new CleanTask(bathroom), "CLEANER");
//        final var guestThree = new Thread(new TaskNumberTwo(bathroom), "Lary");
//        final var guestFour = new Thread(new TaskNumberTwo(bathroom), "James");
//        final var guestFive = new Thread(new TaskNumberTwo(bathroom), "Sam");

        guestOne.start();
        guestTwo.start();
        cleaner.start();
//        guestThree.start();
//        guestFour.start();
//        guestFive.start();
    }
}
