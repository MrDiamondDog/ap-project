package org.stuff;

public class Console {
    /**
     * Clears the console.
     */
    public static void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
