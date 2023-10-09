package org.stuff;

import org.stuff.games.TicTacToe;

public class Menus {
    public static Menu MainMenu;
    public static Menu GamesMenu;

    public static void init() {
        MainMenu = new Menu("Main Menu", new String[]{
                "Exit",
        }, new Runnable[]{
                () -> System.exit(0),
        }, new Menu[]{
                GamesMenu
        });

        GamesMenu = new Menu("Games", new String[]{
                "Tic Tac Toe"
        }, new Runnable[]{
                TicTacToe::play,
        }, MainMenu);
    }
}
