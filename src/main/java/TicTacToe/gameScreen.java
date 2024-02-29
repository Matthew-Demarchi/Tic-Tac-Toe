package TicTacToe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import TicTacToe.sounds.sounds;
import TicTacToe.tempForData.TempForData;


public class gameScreen {

    String ai;
    String human;
    String aiStyle[] = new String[3];
    String humanStyle[] = new String[3];

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

    @FXML
    Line topRow, middleRow, bottomRow, leftColumn, middleColumn, rightColumn, diagonalTopLeftToBottomRight, diagonalTopRightToBottomLeft;
    private static ArrayList<Line> lines = new ArrayList<>();

    Line winnerLine = null;

    public void initialize() {
        buttons.add(boardButton1);
        buttons.add(boardButton2);
        buttons.add(boardButton3);
        buttons.add(boardButton4);
        buttons.add(boardButton5);
        buttons.add(boardButton6);
        buttons.add(boardButton7);
        buttons.add(boardButton8);
        buttons.add(boardButton9);

        lines.add(topRow);
        lines.add(middleRow);
        lines.add(bottomRow);
        lines.add(leftColumn);
        lines.add(middleColumn);
        lines.add(rightColumn);
        lines.add(diagonalTopLeftToBottomRight);
        lines.add(diagonalTopRightToBottomLeft);

        gameNumber = 1;
        player1Wins = 0;
        player2Wins = 0;
        numOfDraws = 0;

        gameCountLabel.setText("GAME #" + Integer.toString(gameNumber));
        player1WinsLabel.setText("Player 1: " + Integer.toString(player1Wins));
        player2WinsLabel.setText("Player 2: " + Integer.toString(player2Wins));
        numOfDrawsLabel.setText("Draws: " + Integer.toString(numOfDraws));
        //System.out.print(buttons.size());
        ai = "O";
        human = "X";
        humanStyle[0] = "-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, red, 3, 0.1, 0, 0);";
        humanStyle[1] = "-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;";
        humanStyle[2] = "-fx-stroke: red; -fx-font-weight: bold; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, red, 3, 0.1, 0, 0);";
        aiStyle[0] = "-fx-text-fill: lime; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, lime, 3, 0.1, 0, 0);";
        aiStyle[1] = "-fx-text-fill: lime; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;";
        aiStyle[2] = "-fx-stroke: lime; -fx-font-weight: bold; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, lime, 3, 0.1, 0, 0);";
    }

    @FXML
    protected void boardButtonHovered(MouseEvent event)
    {

        Button button = (Button) event.getSource();

        int temp;
        if (human.equals("X"))
        {
            temp = 2;
        }
        else
        {
            temp = 1;
        }

        if (buttonsUsed.size() % temp == 0)
        {
            button.setText(human);
            button.setStyle(humanStyle[1]);
        }
        else
        {
            button.setText(ai);
            button.setStyle(aiStyle[1]);
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
            button.setText(human);
            button.setStyle(humanStyle[0]); //
            button.setDisable(true);
            buttonsUsed.add(button);
            currentTurnLabel.setText("Current Turn: " + ai);


            // Check for player win or draw
            if (checkWin(null, human)) {
                gameOver(human);
            } else if (isBoardFull(null)) {
                gameOver("draw");
            } else {
                // Trigger AI move
                currentTurnLabel.setText("Current Turn: " + human);
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

        // load the controller file into inGameOptions controller variable
        // then pass the gameScreen controller to the inGameOptions controller

        inGameOptions controller = fxmlLoader.getController();
        controller.setGameScreenController(this);


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
                currentTurnLabel.setText("Current Turn: " + human);
                for (Button button : buttons) {
                    button.setDisable(false);
                }
                if (winnerLine != null)
                {
                    winnerLine.setVisible(false);
                }
            }
    }

    // signal is passed from the inGameOptions controller
    // to this gameScreen controller.
    public void handleOptionsClear(boolean optionsClear){

        if(optionsClear)
            clearBoard();
    }

    // signal is passed from the inGameOptions controller
    // to this gameScreen controller.
    public void handleOptionsQuit(boolean optionsQuit) throws IOException {

        if(optionsQuit)
            quitGame();
    }

    private boolean checkWin(ArrayList<Button> buttons2, String symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons.get(i * 3).getText().equals(symbol) &&
                buttons.get(i * 3 + 1).getText().equals(symbol) &&
                buttons.get(i * 3 + 2).getText().equals(symbol)) {
                showLine(0, i);
                return true; // Winning row
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons.get(i).getText().equals(symbol) &&
                buttons.get(i + 3).getText().equals(symbol) &&
                buttons.get(i + 6).getText().equals(symbol)) {
                showLine(1, i);
                return true; // Winning column
            }
        }

        // Check diagonals
        if (buttons.get(0).getText().equals(symbol) &&
            buttons.get(4).getText().equals(symbol) &&
            buttons.get(8).getText().equals(symbol)) {
            showLine(2, 0);
            return true; // Winning diagonal
        }
        if (buttons.get(2).getText().equals(symbol) &&
            buttons.get(4).getText().equals(symbol) &&
            buttons.get(6).getText().equals(symbol)) {
            showLine(2, 1);
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
            for (Button button : buttons) {
                if (button.getText().isEmpty()) {
                    emptyCells.add(button);
                }
            }


            int bestScore = Integer.MIN_VALUE;
            int bestMove = 0;
            if (TempForData.normalButton) {
                for (int i = 0; i < buttons.size(); i++) {
                    if (buttons.get(i).getText().equals("")) {
                        buttons.get(i).setText(ai); // Assuming AI plays with 'O'
                        int score = TicTacToeAI.minimax(buttons, emptyCells.size(), false, ai, human, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        buttons.get(i).setText("");
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = i;
                        }
                    }

                }
                buttons.get(bestMove).setText(ai);
                buttons.get(bestMove).setStyle(aiStyle[0]);
                buttons.get(bestMove).setDisable(true);
                buttonsUsed.add(buttons.get(bestMove));
            }
            else {
                if (!emptyCells.isEmpty()) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(emptyCells.size());
                    Button randomButton = emptyCells.get(randomIndex);
                    randomButton.setText(ai); // Assuming AI plays with 'O'
                    randomButton.setStyle(aiStyle[0]);
                    randomButton.setDisable(true);
                    buttonsUsed.add(randomButton);
                }
            }
            isGameOver();
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
        if (winner.equals(human)) {
            // Player 1 wins
            // Update player 1's win count
            player1Wins++;
            player1WinsLabel.setText("Player 1: " + Integer.toString(player1Wins));
        } else if (winner.equals(ai)) {
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

        gameNumber++;
        gameCountLabel.setText("GAME #" + Integer.toString(gameNumber));

        // Optionally, provide an option to start a new game
        playAgain();
    }

    // Method to check if the game is over
    private boolean isGameOver() {
        // Check for win
        if (checkWin(buttons, human)) {
            gameOver(human);
            return true;
        } else if (checkWin(buttons, ai)) {
            gameOver(ai);
            return true;
        }

        // Check for draw
        if (isBoardFull(buttons)) {
            gameOver("draw");
            return true;
        }

        return false;
    }


    private void showLine(int type, int number)
    {
        switch (type)
        {
            case 0: // rows
                winnerLine = lines.get(0 + number);
                break;
            case 1:
                winnerLine = lines.get(3 + number);
                break;
            case 2:
                winnerLine = lines.get(6 + number);
                break;
        }
        winnerLine.setVisible(true);

        int temp;
        if (human.equals("X"))
        {
            temp = 2;
        }
        else
        {
            temp = 1;
        }

        if(buttonsUsed.size() % temp != 0)
        {
            winnerLine.setStyle(humanStyle[2]);
        }
        else
        {
            winnerLine.setStyle(aiStyle[2]);
        }
    }

    // function to prompt the user whether to play again
    // Passes the gameScreen controller to the playAgain controller,
    // which sends a signal back to gameScreen controller

    private void playAgain(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playAgain.fxml"));
            Parent root = fxmlLoader.load();

            playAgain controller = fxmlLoader.getController();
            controller.setGameScreenController(this); // pass gameScreen controller to playAgain controller

            Scene scene = new Scene(root);
            Stage playAgainStage = new Stage();

            playAgainStage.setMaxWidth(300);
            playAgainStage.setMaxHeight(200);

            playAgainStage.setMinWidth(300);
            playAgainStage.setMinHeight(200);

            playAgainStage.initModality(Modality.APPLICATION_MODAL); // Set as modal dialog
            playAgainStage.setScene(scene);

            playAgainStage.showAndWait();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }



    public void handlePlayAgain(boolean playAgain) throws IOException {
        if (playAgain) {

            clearBoard();
            if (TempForData.sidesOnButton)
            {
                String temp;
                temp = human;
                human = ai;
                ai = temp;

                for (int i = 0; i < 3; i++)
                {
                    temp = humanStyle[i];
                    humanStyle[i] = aiStyle[i];
                    aiStyle[i] = temp;
                }
            }
            if (human.equals("O"))
            {
                makeAIMove();
            }


        } else {

            quitGame();
        }
    }

    // simple function to clear board and reset state
    private void clearBoard(){

        for (Button button : buttons) {
            button.setText("");
            button.setDisable(false);
        }
        buttonsUsed.clear();

        if (winnerLine != null) {
            winnerLine.setVisible(false);
            winnerLine = null;
        }

    }

    // simple function to quit game and return to main menu

    private void quitGame() throws IOException {

        buttonsUsed.clear();
        buttons.clear();
        lines.clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicTacMainMenu2.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage primaryStage = (Stage) currentTurnLabel.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    }

