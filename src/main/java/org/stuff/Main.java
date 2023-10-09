package org.stuff;

import org.stuff.games.TicTacToe;

public class Main {
    public static Menu MainMenu = new Menu("Main Menu", new String[]{
            "Exit",
    }, new Runnable[]{
            () -> System.exit(0),
    }, new Menu[]{
            new Menu("Games", new String[]{
                    "Tic Tac Toe",
                    "help"
            }, new Runnable[]{
                    TicTacToe::play,
                    TicTacToe::play,
            })
    });

    public static void main(String[] args) {
        Console.clear();

        MainMenu.print().awaitInput();
    }
}