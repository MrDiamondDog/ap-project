package org.stuff;

public class Main {

    public static void main(String[] args) {
        Console.clear();

        Menus.MainMenu.print().awaitInput();
    }
}