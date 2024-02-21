package TicTacToe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import TicTacToe.TicTacToeAI;
import TicTacToe.sounds.sounds;

public class gameScreen {

    int gameNumber;
    int player1Wins; //letter x
    int player2Wins; //letter o

    int numOfDraws;

    @FXML
    Label currentTurnLabel;

    @FXML
    Label gameCountLabel;

    @FXML
    Label player1WinsLabel;

    @FXML
    Label player2WinsLabel;

    @FXML
    Label numOfDrawsLabel;

    private static ArrayList<Button> buttonsUsed = new ArrayList<>();

    @FXML
    Button boardButton1, boardButton2, boardButton3,
            boardButton4, boardButton5, boardButton6,
            boardButton7, boardButton8, boardButton9;

    private static ArrayList<Button> buttons = new ArrayList<>();
    //Arrays.asList(boardButton1, boardButton2, boardButton3,
    //            boardButton4, boardButton5, boardButton6,
    //            boardButton7, boardButton8, boardButton9)


    public void initialize()
    {
        buttons.add(boardButton1);
        buttons.add(boardButton2);
        buttons.add(boardButton3);
        buttons.add(boardButton4);
        buttons.add(boardButton5);
        buttons.add(boardButton6);
        buttons.add(boardButton7);
        buttons.add(boardButton8);
        buttons.add(boardButton9);
        gameNumber = 1;
        player1Wins = 0;
        player2Wins = 0;
        numOfDraws = 0;

        gameCountLabel.setText("GAME #" + Integer.toString(gameNumber));
        player1WinsLabel.setText("Player 1: " + Integer.toString(player1Wins));
        player2WinsLabel.setText("Player 2: " + Integer.toString(player2Wins));
        numOfDrawsLabel.setText("Draws: " + Integer.toString(numOfDraws));
        //System.out.print(buttons.size());
    }

    @FXML
    protected void boardButtonHovered(MouseEvent event)
    {

        Button button = (Button) event.getSource();

        if (buttonsUsed.size() % 2 == 0)
        {
            button.setText("X");
            button.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;");
        }
        else
        {
            button.setText("O");
            button.setStyle("-fx-text-fill: lime; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;");
        }
    }

    @FXML
    protected void boardButtonNotHovered(MouseEvent event)
    {
        Button button = (Button) event.getSource();
        if (button.isDisable())
        {
            return;
        }
        else if (buttonsUsed.size() % 2 == 0)
        {
            button.setText("");
            button.setStyle("-fx-text-fill: transparent; -fx-font-weight: bold; -fx-background-color: transparent;");
        }
        else
        {
            button.setText("");
            button.setStyle("-fx-text-fill: transparent; -fx-font-weight: bold; -fx-background-color: transparent;");
        }
    }

    @FXML
    protected void boardButtonClicked(ActionEvent event) {
        sounds.playButtonClickSound();

        Button button = (Button) event.getSource();
        if (!button.isDisable()) {
            // Set player's move
            button.setText("X");
            button.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, red, 3, 0.1, 0, 0);"); //
            button.setDisable(true);
            buttonsUsed.add(button);
            currentTurnLabel.setText("Current Turn: O");
            
            // Check for player win or draw
            if (checkWin(null, "X")) {
                gameOver("X");
            } else if (isBoardFull(null)) {
                gameOver("draw");
            } else {
                // Trigger AI move
                currentTurnLabel.setText("Current Turn: X");
                makeAIMove();
            }
        }   
    }

    @FXML
    protected void OptionsButtonClicked()
    {
        sounds.playButtonClickSound();
            try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inGameOptions.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        inGameOptions controller = fxmlLoader.getController();
        controller.getMainStageAndButtons((Stage)(currentTurnLabel.getScene().getWindow()), buttonsUsed);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Set as modal dialog
        stage.setTitle("Options");
        stage.setScene(scene);
        stage.showAndWait();
    } catch (IOException e){
                e.printStackTrace();
            }
            if (buttonsUsed.size() == 0)
            {
                currentTurnLabel.setText("Current Turn: X");
                for (Button button : buttons) {
                    button.setDisable(false);
                }
            }
    }

    private boolean checkWin(ArrayList<Button> buttons2, String symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3).getText().equals(symbol) &&
                buttons.get(i * 3 + 1).getText().equals(symbol) && 
                buttons.get(i * 3 + 2).getText().equals(symbol)) {
                return true; // Winning row
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons.get(i).getText().equals(symbol) && 
                buttons.get(i + 3).getText().equals(symbol) && 
                buttons.get(i + 6).getText().equals(symbol)) {
                return true; // Winning column
            }
        }

        // Check diagonals
        if (buttons.get(0).getText().equals(symbol) && 
            buttons.get(4).getText().equals(symbol) && 
            buttons.get(8).getText().equals(symbol)) {
            return true; // Winning diagonal
        }
        if (buttons.get(2).getText().equals(symbol) && 
            buttons.get(4).getText().equals(symbol) && 
            buttons.get(6).getText().equals(symbol)) {
            return true; // Winning diagonal
        }

        return false; // No winning combination found
    }

    private boolean isBoardFull(ArrayList<Button> buttons2) {
        for (Button button : buttons) {
            if (button.getText().isEmpty()) {
                return false; // If any cell is empty, the board is not full
            }
        }
        return true; // All cells are filled
    }

        // Method to make a move for the AI player
        private void makeAIMove() {
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
                buttonsUsed.add(randomButton);

                isGameOver();
            }
        }


    // Method to handle game over
    private void gameOver(String winner) {
        // Disable all buttons
        for (Button button : buttons) {
            button.setDisable(true);
        }

        // Display game over message
        if (winner.equals("draw")) {
            currentTurnLabel.setText("Game Over: Draw!");
        } else {
            currentTurnLabel.setText("Game Over: " + winner + " wins!");
        }

        // Update statistics
        if (winner.equals("X")) {
            // Player 1 wins
            // Update player 1's win count
            player1Wins++;
            player1WinsLabel.setText("Player 1: " + Integer.toString(player1Wins));
        } else if (winner.equals("O")) {
            // Player 2 wins (AI)
            // Update player 2's win count
            player2Wins++;
            player2WinsLabel.setText("Player 2: " + Integer.toString(player2Wins));
        } else {
            // It's a draw
            // Update draw count
            numOfDraws++;
            numOfDrawsLabel.setText("Draws: " + Integer.toString(numOfDraws));
        }
        gameCountLabel.setText("GAME #" + Integer.toString(gameNumber++));
        // Optionally, provide an option to start a new game
    }

    // Method to check if the game is over
    private boolean isGameOver() {
        // Check for win
        if (checkWin(buttons, "X")) {
            gameOver("X");
            return true;
        } else if (checkWin(buttons, "O")) {
            gameOver("O");
            return true;
        }

        // Check for draw
        if (isBoardFull(buttons)) {
            gameOver("draw");
            return true;
        }

        return false;
    }

    }