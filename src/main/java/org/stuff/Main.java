package org.stuff;

import static org.stuff.Menus.MainMenu;

public class Main {
    public static void main(String[] args) {
        Menus.init();
        Console.clear();

        MainMenu.print().awaitInput("Exit");
    }
}