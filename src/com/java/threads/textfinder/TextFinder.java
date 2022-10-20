package com.java.threads.textfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFinder implements Runnable {

    private final String fileName;
    private final String authorName;

    public TextFinder(final String fileName, final String authorName) {
        this.fileName = fileName;
        this.authorName = authorName.toLowerCase();
    }

    @Override
    public void run() {
        final var file = new File("").getAbsolutePath() + "/textfinder/" + this.fileName;
        Scanner scanner = null;
        try {
            int lineNumber = 1;
            scanner = new Scanner(new File(file));
            while (scanner.hasNextLine()) {
                final var line = scanner.nextLine().toLowerCase();

                if (line.contains(this.authorName)) {
                    System.out.printf("File name: %s - %s - %s%n", this.fileName, lineNumber, line);
                }

                lineNumber++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
