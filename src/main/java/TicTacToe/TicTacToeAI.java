
package TicTacToe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TicTacToe.tempForData.TempForData;
import javafx.scene.control.Button;


public class TicTacToeAI {

    public static int TicTacToeAI(boolean easy, ArrayList<Button> buttons, String ai, String human) {
        //getting the number of empty cells
        int numOfEmptyCells = 0;
        for (Button button : buttons) {
            if (button.getText().isEmpty()) {
                numOfEmptyCells++;
            }
        }

        //calling a smart or dumb ai
        if (!easy) {
            return smartAI(buttons, ai, human, numOfEmptyCells);
        } else {
            return dumbAI(buttons, numOfEmptyCells);
        }
    }


    private static int smartAI(ArrayList<Button> buttons, String ai, String human, int numOfEmptyCells) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;
        if (TempForData.normalButton) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals("")) {
                    buttons.get(i).setText(ai); // Assuming AI plays with 'O'
                    int score = TicTacToeAI.minimax(buttons, numOfEmptyCells, false, ai, human, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    buttons.get(i).setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int dumbAI(ArrayList<Button> buttons, int numOfEmptyCells)
    {
        int[] moves = new int[numOfEmptyCells];
        int j = 0;
        for (int i = 0; i < buttons.size(); i++)
        {
            if (buttons.get(i).getText().equals(""))
            {
                moves[j] = i;
                j++;
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(numOfEmptyCells);
        return moves[randomIndex];
    }

    private static int minimax(ArrayList<Button> buttons, int depth, boolean maximizingPlayer, String ai, String human, int alpha, int beta) {
        int result = checkWinner(buttons, ai, human);
        if (depth == 0 || result != -2)
        {
            return result;
        }
        if (maximizingPlayer)
        {
            int bestEvaluation = Integer.MIN_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals(""))
                {
                    buttons.get(i).setText(ai);
                    int evaluation = minimax(buttons, depth-1, false, ai, human, alpha, beta);
                    buttons.get(i).setText("");

                    bestEvaluation = Math.max(evaluation, bestEvaluation);
                    alpha = Math.max(alpha, evaluation);
                    if (beta <= alpha)
                    {
                        break;
                    }
                }
            }
            return bestEvaluation;
        }
        else
        {
            int bestEvaluation = Integer.MAX_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals(""))
                {
                    buttons.get(i).setText(human);
                    int evaluation = minimax(buttons, depth-1, true, ai, human, alpha, beta);
                    buttons.get(i).setText("");
                    bestEvaluation = Math.min(evaluation, bestEvaluation);
                    beta = Math.min(beta, evaluation);
                    if (beta <= alpha)
                    {
                        break;
                    }
                }
            }
            return bestEvaluation;
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

    private static int checkWinner(ArrayList<Button> buttons, String ai, String human) {
        int result = -1;
        String symbol = human;

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
            symbol = ai;
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
