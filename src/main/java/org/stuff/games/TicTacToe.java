package org.stuff.games;

import org.stuff.*;

public class TicTacToe {
    public Menu playersMenu = new Menu("How many players?", new String[]{
            "1 Player",
            "2 Players"
    }, new Runnable[]{
            () -> players = 1,
            () -> players = 2
    });
    public int players = 0;
    public String p1 = "X";
    public String p2 = "O";
    public String currentPlayer = p1;
    public String empty = " ";
    public boolean stopped = false;

    public String[][] grid = new String[][]{
            {empty, empty, empty},
            {empty, empty, empty},
            {empty, empty, empty}
    };

    public void play() {
        playersMenu.print().awaitInput(1);

        while (true) {
            if (players == 1) onePlayerLoop();
            else if (players == 2) twoPlayerLoop();
            else return;
            if (stopped) break;
        }

        Menus.GamesMenu.print().awaitInput();
    }

    public void onePlayerLoop() {
        printBoard();

        playerTurn();

        currentPlayer = p2;

        int aiChoice = ai();
        int aiRow = row(aiChoice);
        int aiCol = col(aiChoice);

        grid[aiRow][aiCol] = currentPlayer;
        currentPlayer = p1;

        checkWin(true);
    }

    public int ai() {
        int[] scores = new int[9];
        for (int i = 0; i < 9; i++) {
            int row = row(i);
            int col = col(i);

            if (grid[row][col].equals(empty)) {
                grid[row][col] = currentPlayer;

                if (checkWin(false)) {
                    scores[i] = 1;
                } else {
                    currentPlayer = currentPlayer.equals(p1) ? p2 : p1;
                    int aiChoice = ai();
                    int aiRow = row(aiChoice);
                    int aiCol = col(aiChoice);

                    grid[aiRow][aiCol] = currentPlayer;

                    if (checkWin(false)) {
                        scores[i] = -1;
                    } else {
                        scores[i] = 0;
                    }

                    grid[aiRow][aiCol] = empty;
                    currentPlayer = currentPlayer.equals(p1) ? p2 : p1;
                }

                grid[row][col] = empty;
            } else scores[i] = -2;
        }

        int bestScore = -2;
        int bestIndex = -1;
        for (int i = 0; i < scores.length; i++) {
            int score = scores[i];
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }

        return bestIndex;
    }

    public void twoPlayerLoop() {
        printBoard();
        playerTurn();
        if (checkWin(true)) return;

        currentPlayer = currentPlayer.equals(p1) ? p2 : p1;
    }

    public void playerTurn() {
        String input = "";
        int index = -1;
        while (index == -1) {
            input = Input.awaitInput("Enter a square (1-9): ");
            if (input.equals("e")) {
                stopped = true;
                return;
            }

            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                continue;
            }

            if (index < 0 || index > 8) {
                index = -1;
                continue;
            }

            int row = row(index);
            int col = col(index);

            if (!grid[row][col].equals(empty)) {
                index = -1;
            }
        }

        int row = row(index);
        int col = col(index);

        grid[row][col] = currentPlayer;
    }

    public boolean checkWin(boolean actuallyWin) {
        for (int x = 0; x < grid.length; x++) {
            String[] row = grid[x];
            // Row
            if (!row[0].equals(empty) && row[0].equals(row[1]) && row[1].equals(row[2])) {
                if (actuallyWin) winner(row[0]);
                return true;
            }

            // Column
            String[] col = {
                    grid[0][x],
                    grid[1][x],
                    grid[2][x]
            };
            if (!col[0].equals(empty) && col[0].equals(col[1]) && col[1].equals(col[2])) {
                if (actuallyWin) winner(col[0]);
                return true;
            }
        }

        // Diagonal
        String[] diag = {
                grid[0][0],
                grid[1][1],
                grid[2][2]
        };
        if (!diag[0].equals(empty) && diag[0].equals(diag[1]) && diag[1].equals(diag[2])) {
            if (actuallyWin) winner(diag[0]);
            return true;
        }

        // Diagonal
        String[] diag2 = {
                grid[0][2],
                grid[1][1],
                grid[2][0]
        };

        if (!diag2[0].equals(empty) && diag2[0].equals(diag2[1]) && diag2[1].equals(diag2[2])) {
            if (actuallyWin) winner(diag2[0]);
            return true;
        }

        for (String[] row : grid) {
            for (String cell : row) {
                if (cell.equals(empty)) return false;
            }
        }

        if (actuallyWin) winner("");
        return true;
    }

    public void winner(String player) {
        printBoard();

        if (player.isEmpty()) System.out.println("It's a tie!");
        else System.out.println(player + " wins!");
        stopped = true;
    }

    public int index(int row, int col) {
        return (row * 3) + col;
    }

    public int row(int index) {
        return index / 3;
    }

    public int col(int index) {
        return index % 3;
    }

    public void printBoard() {
        Console.clear();
        System.out.println(ConsoleColor.WHITE + "Input \"e\" to exit." + ConsoleColor.WHITE_BRIGHT);

        int i = 0;
        for (String[] row : grid) {
            int j = 0;
            for (String cell : row) {
                String str =
                        (cell.equals(empty) ?
                                ConsoleColor.GREEN + (index(i, j) + 1) :
                                (cell.equals(p1) ? ConsoleColor.BLUE : ConsoleColor.RED) + cell
                        ) + ConsoleColor.WHITE_BRIGHT;
                if (j == row.length - 1)
                    System.out.print(str);
                else if (j == 0)
                    System.out.print(" " + str + " | ");
                else
                    System.out.print(str + " | ");
                j++;
            }

            if (i == grid.length - 1)
                System.out.println();
            else {
                System.out.println();
                System.out.println("———+———+———");
            }

            i++;
        }
    }
}
