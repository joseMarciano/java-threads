package com.java.threads.textfinder;

public class Main {

    public static void main(String[] args) {

        final var name = "da";

        Thread threadSignatureOne = new Thread(new TextFinder("assinaturas1.txt", name));
        Thread threadSignatureOTwo = new Thread(new TextFinder("assinaturas2.txt", name));
        Thread threadAuthors = new Thread(new TextFinder("autores.txt", name));


        threadSignatureOne.start();
        threadSignatureOTwo.start();
        threadAuthors.start();

    }
}
