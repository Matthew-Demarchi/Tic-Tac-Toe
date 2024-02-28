package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Button;


public class TicTacToeAI {

    public static int evaluation (ArrayList<Button> buttons)
    {
        int score = 0;
        int temp = 0;

        for (int i = 1; i < 4; i++)
        {
            for (int j = 1; j < 4; j++)
            {
                if (buttons.get((j + (i - 1) * 3) - 1).getText() == "X")
                {
                    temp -= 1;
                }
                else if (buttons.get((j + (i - 1) * 3) - 1).getText() == "O")
                {
                    temp += 1;
                }
                else
                {
                    temp += 0;
                }
            }
            if (temp == 3)
            {
                score = 10;
                return score;
            }
            else if (temp == -3)
            {
                score = -10;
                return score;
            }
            else
            {
                score += temp;
            }
            temp = 0;
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (buttons.get(i * 3 + j).getText() == "X") {
                    temp -= 1;
                } else if (buttons.get(i * 3 + j).getText() == "O") {
                    temp += 1;
                }
            }
            if (temp == 3) {
                score = 10;
                return score;
            } else if (temp == -3) {
                score = -10;
                return score;
            } else {
                score += temp;
            }
            temp = 0;
        }


        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3 + i).getText() == "X") {
                temp -= 1;
            } else if (buttons.get(i * 3 + i).getText() == "O") {
                temp += 1;
            }
        }
        if (temp == 3) {
            score = 10;
            return score;

        } else if (temp == -3) {
            score = -10;
            return score;

        } else {
            score += temp;
        }

        temp = 0;

        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3 + (2 - i)).getText() == "X") {
                temp -= 1;
            } else if (buttons.get(i * 3 + (2 - i)).getText() == "O") {
                temp += 1;
            }
        }
        if (temp == 3) {
            score = 10;
            return score;

        } else if (temp == -3) {
            score = -10;
            return score;

        } else {
            score += temp;
        }
        temp = 0;

        return score;
    }

    public static int[] minimax(ArrayList<Button> buttons, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || evaluation(buttons) == 10 || evaluation(buttons) == -10) {
            return new int[]{evaluation(buttons), -1}; // Return evaluation score and no move
        }

        int bestMove = -1;
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().isEmpty()) {
                String currentPlayer = maximizingPlayer ? "O" : "X";
                buttons.get(i).setText(currentPlayer); // Make a hypothetical move
                int[] eval = minimax(buttons, depth - 1, alpha, beta, !maximizingPlayer);
                buttons.get(i).setText(""); // Undo the move

                if (maximizingPlayer) {
                    if (eval[0] > bestScore) {
                        bestScore = eval[0];
                        bestMove = i;
                    }
                    alpha = Math.max(alpha, bestScore);
                } else {
                    if (eval[0] < bestScore) {
                        bestScore = eval[0];
                        bestMove = i;
                    }
                    beta = Math.min(beta, bestScore);
                }

                if (beta <= alpha) {
                    break; // Alpha-beta pruning
                }
            }
        }
        return new int[]{bestScore, bestMove}; // Return evaluation score and selected move
    }
    public static int findBestMoveIndex(ArrayList<Button> buttons, int depth) {
        int[] result = minimax(buttons, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return result[1]; // Return the index of the best move
    }

}





//

/*

package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Button;


public class TicTacToeAI
{

    int evaluation (ArrayList<Button> buttons)
    {
        int score = 0;
        int temp = 0;

        for (int i = 1; i < 4; i++)
        {
            for (int j = 1; j < 4; j++)
            {
                if (buttons.get((j + (i - 1) * 3) - 1).getText() == "X")
                {
                    temp -= 1;
                }
                else if (buttons.get((j + (i - 1) * 3) - 1).getText() == "O")
                {
                    temp += 1;
                }
                else
                {
                    temp += 0;
                }
            }
            if (temp == 3)
            {
                score = 10;
                return score;
            }
            else if (temp == -3)
            {
                score = -10;
                return score;
            }
            else
            {
                score += temp;
            }
            temp = 0;
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (buttons.get(i * 3 + j).getText() == "X") {
                    temp -= 1;
                } else if (buttons.get(i * 3 + j).getText() == "O") {
                    temp += 1;
                }
            }
            if (temp == 3) {
                score = 10;
                return score;
            } else if (temp == -3) {
                score = -10;
                return score;
            } else {
                score += temp;
            }
            temp = 0;
        }


        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3 + i).getText() == "X") {
                temp -= 1;
            } else if (buttons.get(i * 3 + i).getText() == "O") {
                temp += 1;
            }
        }
        if (temp == 3) {
            score = 10;
            return score;

        } else if (temp == -3) {
            score = -10;
            return score;

        } else {
            score += temp;
        }

        temp = 0;

        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3 + (2 - i)).getText() == "X") {
                temp -= 1;
            } else if (buttons.get(i * 3 + (2 - i)).getText() == "O") {
                temp += 1;
            }
        }
        if (temp == 3) {
            score = 10;
            return score;

        } else if (temp == -3) {
            score = -10;
            return score;

        } else {
            score += temp;
        }
        temp = 0;

        return score;
    }


    int minimax(ArrayList<Button> buttons, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || evaluation(buttons) == 10 || evaluation(buttons) == -10) {
            return evaluation(buttons);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Button button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText("O"); // Make a hypothetical move for player O
                    int eval = minimax(buttons, depth - 1, alpha, beta, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    button.setText(""); // Undo the move
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Button button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText("X"); // Make a hypothetical move for player X
                    int eval = minimax(buttons, depth - 1, alpha, beta, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    button.setText(""); // Undo the move
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }


}

 */