package org.stuff;

import java.util.Scanner;

public class Input {
    public static Scanner scanner = new Scanner(System.in);

    public static String awaitInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int awaitInputInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            awaitInputInt(prompt);
        }

        return -1;
    }
}
