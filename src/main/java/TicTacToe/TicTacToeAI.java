package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Button;


public class TicTacToeAI {
    // Method to make a move for the AI player
    public static void makeMove(ArrayList<Button> buttons) {
        ArrayList<Button> emptyCells = new ArrayList<>();

        // Find all empty cells
        for (Button button : buttons) {
            if (button.getText().isEmpty()) {
                emptyCells.add(button);
            }
        }

        // If there are empty cells, choose a random one and make the move
        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyCells.size());
            Button randomButton = emptyCells.get(randomIndex);
            randomButton.setText("O"); // Assuming AI plays with 'O'
            randomButton.setStyle("-fx-text-fill: lime; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, lime, 3, 0.1, 0, 0);");
            randomButton.setDisable(true);
        }
    }
}