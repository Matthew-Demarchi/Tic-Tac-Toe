package TicTacToe;

import TicTacToe.Game.Game;
import TicTacToe.tempForData.TempForData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


import TicTacToe.sounds.sounds;
import TicTacToe.server.*;

//TODO fix bug when quitting before getting into a 2 player game and fix socket not connecting
public class gameScreen {

    String ai;
    String human;
    String[] aiStyle = new String[3];
    String[] humanStyle = new String[3];

    int gameNumber;
    int player1Wins; //letter x
    int player2Wins; //letter o
    BufferedReader reader = null;
    InputStream input = null;
    ObjectInputStream objectInput = null;
    Thread listener;
    Thread notifier;
    Listener listen;
    Notifier notify;

    int numOfDraws;
    String player;
    Game game;
    Socket socket = null;
    boolean notEstablished = true;
    int mode = 0;
    boolean chatVisible = false;
    boolean quit = false;

    boolean sunsetMode = false;

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
    @FXML
    Label errorLabel;
    @FXML
    Label playerNumberLabel;
    @FXML
    Label resultLabel;
    @FXML
    Button OptionsButton;
    @FXML
    Button chatButton;
    @FXML
    AnchorPane chatArea;
    @FXML
    AnchorPane gameArea;
    @FXML
    TextField messageField;
    @FXML
    AnchorPane messagePane;
    @FXML
    TextArea chatBox;
    @FXML
    Circle chatDot;
    @FXML
    Button changeThemeButton;


    @FXML
    private CheckBox sunsetModeCheckBox;

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
            playerNumberLabel.setText("finding opponent...");
            quit = false;
            //System.out.print(buttons.size());

            ai = "O";
            human = "X";
            humanStyle[0] = "-fx-text-fill: blue; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, blue, 3, 0.1, 0, 0);";
            humanStyle[1] = "-fx-text-fill: blue; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;";
            humanStyle[2] = "-fx-stroke: blue; -fx-font-weight: bold; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, blue, 3, 0.1, 0, 0);";
            aiStyle[0] = "-fx-text-fill: black; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, black, 3, 0.1, 0, 0);";
            aiStyle[1] = "-fx-text-fill: black; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 0.35;";
            aiStyle[2] = "-fx-stroke: black; -fx-font-weight: bold; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, black, 3, 0.1, 0, 0);";
            UIToggleOff();
            if (TempForData.mode == 1) {
                chatButton.setDisable(true);
                chatButton.setVisible(false);
            } else {
                chatButton.setDisable(false);
                chatButton.setVisible(true);
            }
            changeTheme();
    }
    public void connectToServer(Socket socket)
    {
        this.socket = socket;
        try {
            System.out.println("startInitialize");


            System.out.println("socket connected");

            listen = new Listener(socket, this); // notify
            listener = new Thread(listen);

            listener.start();
            System.out.println(mode + " -- mode");
            new Thread(new Notifier(socket, "/mode" + TempForData.mode, this)).start();
            System.out.println("Listener started");



        } catch(Exception e){
                throw new RuntimeException(e);
            }

    }

    @FXML
    protected void chatButtonClicked()
    {
        chatVisible = chatArea.isVisible();
        chatArea.setVisible(!chatVisible);
        System.out.println(chatVisible + " chat visible");

        Stage stage = (Stage) chatArea.getScene().getWindow();
        if (!chatVisible) {
            chatDot.setVisible(false);
            chatArea.setVisible(true);
            stage.setWidth(stage.getWidth() + chatArea.getPrefWidth());
        } else {
            chatArea.setVisible(false);
            stage.setWidth(stage.getWidth() - chatArea.getPrefWidth());
        }

    }
    @FXML
    protected void keyPressedInTextEntry(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER)
        {
            sendButtonClicked();
        }
    }

    @FXML
    protected void sendButtonClicked() {
        String text = isNull(messageField);
        if (!text.equalsIgnoreCase("")) {
            if (text.contains("/"))
            {
                errorLabel.setText("The the character \"/\" is not allowed in messages");
            }
            else
            {
                new Thread(new Notifier(socket, "/messagePlayer " + player + ":   " + text, this)).start();
                messageField.setText("");
                errorLabel.setText("");
            }

        }

    }
    public void newMessage(String text) {
        chatBox.appendText(text + "\n");
        if (!chatArea.isVisible())
        {
            chatDot.setVisible(true);
        }
    }


    private String isNull(TextField field) {
        if (field == null) {
            return "";
        } else {
            return field.getText();
        }
    }

    @FXML
    protected void boardButtonHovered(MouseEvent event)
    {
        if (notEstablished)
        {
            return;
        }
        System.out.println("hovered");
        Button button = (Button) event.getSource();

            if (player.equalsIgnoreCase(Integer.toString(game.getxGoesTo())))
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
        if (notEstablished)
        {
            return;
        }
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
        if (notEstablished)
        {
            return;
        }
        sounds.playButtonClickSound();

        Button button = (Button) event.getSource();
        if (!button.isDisable()) {

                if (player.equalsIgnoreCase(Integer.toString(game.getxGoesTo())))
                {
                    button.setText(human);
                    button.setStyle(humanStyle[0]);
                    currentTurnLabel.setText("Current Turn: O");

                }
                else
                {
                    button.setText(ai);
                    button.setStyle(aiStyle[0]);
                    currentTurnLabel.setText("Current Turn: X");
                }


            button.setDisable(true);
            buttonsUsed.add(button);
            UIToggleOff();
            for (int i = 0; i < buttons.size(); i++)
            {
                System.out.println("i is equal to " + i);
                if (button == buttons.get(i))
                {
                    new Thread(new Notifier(socket, "/move" + i, this)).start();
                    break;
                }
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
        controller.setGameScreenController(this, socket);
        if (TempForData.mode == 2)
        {
            controller.isVSRealPlayer(true);
        }
        else
        {
            controller.isVSRealPlayer(false);
        }


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Set as modal dialog
        stage.setTitle("Options");
        stage.setScene(scene);
        stage.showAndWait();

    } catch (IOException e){
                e.printStackTrace();
            }
    }

    public void handleOptionsQuit() throws IOException
    {
        new Thread(new Notifier(socket, "/quit", this)).start();
        quitGame();
    }

    public void closeSocket()
    {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gameOver()
    {
        Platform.runLater(() -> {
            if (OptionsButton != null)
            {
                OptionsButton.setDisable(true);

            }
            int[] winner = game.getWinner();
            if (winner[0] == 1 || winner[0] == 2) {
                showLine(winner[1], winner[2], winner[0]);
            }

            if (player.equalsIgnoreCase(Integer.toString(winner[0]))) {
                resultLabel.setText("You won!");
            } else if (winner[0] == 4) {
                resultLabel.setText("You tied!");
            } else {
                resultLabel.setText("You lost!");
            }
        });
    }


    private void showLine(int type, int number, int winner)
    {
        switch (type)
        {
            case 0: // rows
                winnerLine = lines.get(number);
                break;
            case 1:
                winnerLine = lines.get(3 + number);
                break;
            case 2:
                winnerLine = lines.get(6 + number);
                break;
        }
        winnerLine.setVisible(true);


        if (winner == game.getxGoesTo())
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


    public void quitGame() throws IOException {
        Platform.runLater(() -> {
            messageField.setText("");
            chatBox.setText("");
            if (game != null)
            {
                game.resetGame();
            }
            buttonsUsed.clear();
            buttons.clear();
            lines.clear();
            TempForData.sidesOffButton = false;
            TempForData.sidesOnButton = true;
            TempForData.easyButton = false;
            TempForData.normalButton = true;
            quit = true;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicTacMainMenu2.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) currentTurnLabel.getScene().getWindow();
            primaryStage.setWidth(scene.getWidth());
            primaryStage.setScene(scene);
            primaryStage.show();
        });

    }
    public void setErrorLabel(String message)
    {
        Platform.runLater(() -> {
            errorLabel.setText(message);
        });

    }
    public void UIToggleOn()
    {
        Platform.runLater(() -> {
            for (int i = 0; i < buttons.size(); i++) {
                boolean used = false;
                if (game.getButton(i) != 0) {
                    used = true;
                }
                if (!used) {
                    System.out.println(buttons.get(i).getId() + " is not disabled");
                    buttons.get(i).setDisable(false);
                }
            }
        });


    }
    public void UIToggleOff()
    {
        Platform.runLater(() -> {
            System.out.println("UI Toggle Off");
            for (int i = 0; i < buttons.size(); i++)
            {
                buttons.get(i).setDisable(true);
                System.out.println(buttons.get(i).getId() + " is disabled");
            }
            System.out.println("UI Toggle Off Done");

        });

    }
    public void setPlayerLabel(String player)
    {

        Platform.runLater(() -> {
            this.player = player;
            playerNumberLabel.setText("You are player " + player);
            if (player.equals("2"))
            {
                UIToggleOff();
            }
        });

        //add something to set x and o
    }
    public void update(Game game)
    {
        System.out.println("update start");
        Platform.runLater(() -> {
            System.out.println("update2");
            if (winnerLine != null)
            {
                winnerLine.setVisible(false);
            }
            if (resultLabel != null)
            {
                resultLabel.setText("");
            }
            if (OptionsButton != null)
            {
                OptionsButton.setDisable(false);
            }
            this.game = game;
            for (int i = 0; i < buttons.size(); i++)
            {
                if (game.getButton(i) == 0)
                {
                    buttons.get(i).setText("");
                    buttons.get(i).setStyle("-fx-text-fill: transparent; -fx-font-weight: bold; -fx-background-color: transparent;");

                }
                else if (game.getButton(i) == game.getxGoesTo())
                {
                    buttons.get(i).setText(human);
                    buttons.get(i).setStyle(humanStyle[0]);
                }
                else
                {
                    buttons.get(i).setText(ai);
                    buttons.get(i).setStyle(aiStyle[0]);
                }
            }
            if (player.equalsIgnoreCase(Integer.toString(game.getCurrentPlayer())))
            {
                if (player.equalsIgnoreCase(Integer.toString(game.getxGoesTo())))
                {
                    currentTurnLabel.setText("Current Turn: " + human);
                }
                else
                {
                    currentTurnLabel.setText("Current Turn: " + ai);
                }
            }
            else
            {
                if (player.equalsIgnoreCase(Integer.toString(game.getxGoesTo())))
                {
                    currentTurnLabel.setText("Current Turn: " + ai);
                }
                else
                {
                    currentTurnLabel.setText("Current Turn: " + human);
                }
            }
            player1WinsLabel.setText("Player1: " + Integer.toString(game.getPlayer1WinCounter()));
            if (TempForData.mode == 2)
            {
                player2WinsLabel.setText("Player2: " + Integer.toString(game.getPlayer2WinCounter()));
            }
            else
            {
                player2WinsLabel.setText("AI: " + Integer.toString(game.getPlayer2WinCounter()));
            }
            numOfDrawsLabel.setText("Draws: " + Integer.toString(game.getDraws()));
            notEstablished = false;
        });


    }
    public Socket getSocket()
    {
        return socket;
    }



    public void changeThemeClicked(ActionEvent actionEvent) {
        TempForData.currentTheme++;
        if (TempForData.currentTheme > 3)
        {
            TempForData.currentTheme = 0;
        }
        changeTheme();
    }
    private void changeTheme()
    {
        switch (TempForData.currentTheme)
        {
            //Winter theme
            case 0:
                gameArea.setStyle("-fx-background-color: linear-gradient(to bottom, #B0E0E6, #FFFFFF, #C0C0C0)");
                chatArea.setStyle("-fx-background-color: linear-gradient(to bottom, #B0E0E6, #FFFFFF, #C0C0C0)");
                changeThemeButton.setText("Spring");
                break;

            //spring theme
            case 1:
                gameArea.setStyle("-fx-background-color: linear-gradient(hotpink, #AEEEEE, #98FF98, gold);");
                chatArea.setStyle("-fx-background-color: linear-gradient(hotpink, #AEEEEE, #98FF98, gold);");


                changeThemeButton.setText("Summer");
                break;
            case 2:
                //summer theme
                gameArea.setStyle("-fx-background-color: linear-gradient(to bottom, Skyblue, yellow, darkorange, seagreen);");
                chatArea.setStyle("-fx-background-color: linear-gradient(to bottom, Skyblue, yellow, darkorange, seagreen);");
                changeThemeButton.setText("Fall");
                break;
            case 3:
                //fall theme
                gameArea.setStyle("-fx-background-color: linear-gradient(to bottom, crimson, lightcoral, skyblue, deepskyblue);");
                chatArea.setStyle("-fx-background-color: linear-gradient(to bottom, crimson, lightcoral, skyblue, deepskyblue);");
                changeThemeButton.setText("Winter");
                break;
        }
    }
}

