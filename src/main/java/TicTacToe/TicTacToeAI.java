
package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Button;


public class TicTacToeAI
{


    public static int minimax(ArrayList<Button> buttons, int depth, boolean maximizingPlayer) {
        int result = checkWinner(buttons);
        if (depth == 0 || result != -2)
        {
            return result;
        }
        if (maximizingPlayer)
        {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals(""))
                {
                    buttons.get(i).setText("O");
                    int score = minimax(buttons, depth-1, false);
                    buttons.get(i).setText("");

                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        }
        else
        {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals(""))
                {
                    buttons.get(i).setText("X");
                    int score = minimax(buttons, depth-1, true);
                    buttons.get(i).setText("");
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }

    }

//    private static int checkWinner(ArrayList<Button> buttons)
//    {
//
//        String[][] board = new String[3][3];
//        board[0][0] = buttons.get(0).getText();
//        board[1][0] = buttons.get(1).getText();
//        board[2][0] = buttons.get(2).getText();
//        board[0][1] = buttons.get(3).getText();
//        board[1][1] = buttons.get(4).getText();
//        board[2][1] = buttons.get(5).getText();
//        board[0][2] = buttons.get(6).getText();
//        board[1][2] = buttons.get(7).getText();
//        board[2][2] = buttons.get(8).getText();
//
//        String winner = null;
//
//        //hor
//        for (int i = 0; i < 3; i++)
//        {
//            if (equals3(board[i][0],board[i][1],board[i][2]))
//            {
//                winner = board[i][0];
//            }
//        }
//
//        for (int i = 0; i < 3; i++)
//        {
//            if (equals3(board[0][i],board[1][i],board[2][i]))
//            {
//                winner = board[0][i];
//            }
//        }
//
//        if (equals3(board[0][0], board[1][1], board[2][2])) {
//            winner = board[0][0];
//        }
//
//        if (equals3(board[2][0], board[1][1], board[0][2])) {
//            winner = board[2][0];
//        }
//
//        int openSpots = 0;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j].equals("")) {
//                    openSpots++;
//                }
//            }
//        }
//
//        if (winner == null && openSpots == 0) {
//            return 0;
//        } else if (winner == null)
//        {
//            return -2;
//        }
//        else{
//            if (winner.equals("X"))
//            {
//                return -1;
//            }
//            else
//            {
//                return 1;
//            }
//        }
//    }
//
//
//    private static boolean equals3(String a, String b, String c)
//    {
//        return a.equals(b) && b.equals(c) && !a.equals("");
//    }

    private static int checkWinner(ArrayList<Button> buttons) {
        int result = -1;
        String symbol = "X";

        for (int k = 0; k < 2; k++)
        {
            for (int i = 0; i < 3; i++) {
                if (buttons.get(i * 3).getText().equals(symbol) &&
                        buttons.get(i * 3 + 1).getText().equals(symbol) &&
                        buttons.get(i * 3 + 2).getText().equals(symbol)) {
                    return result; // Winning row
                }
            }

            // Check columns
            for (int i = 0; i < 3; i++) {
                if (buttons.get(i).getText().equals(symbol) &&
                        buttons.get(i + 3).getText().equals(symbol) &&
                        buttons.get(i + 6).getText().equals(symbol)) {
                    return result; // Winning column
                }
            }

            // Check diagonals
            if (buttons.get(0).getText().equals(symbol) &&
                    buttons.get(4).getText().equals(symbol) &&
                    buttons.get(8).getText().equals(symbol)) {
                return result; // Winning diagonal
            }
            if (buttons.get(2).getText().equals(symbol) &&
                    buttons.get(4).getText().equals(symbol) &&
                    buttons.get(6).getText().equals(symbol)) {
                return result; // Winning diagonal
            }
            result = 1;
            symbol = "O";
        }
        // Check rows

        int buttonNumber = 0;
        for (int i = 0; i < 9; i++)
        {
            if (!buttons.get(i).getText().equals(""))
            {
                buttonNumber++;
            }
        }
        if (buttonNumber == 9)
        {
            return 0;
        }

        return -2; // No winning combination found and not a tie
    }

}
