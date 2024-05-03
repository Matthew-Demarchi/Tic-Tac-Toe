package TicTacToe;

import TicTacToe.tempForData.TempForData;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import TicTacToe.sounds.sounds;
import javafx.util.Duration;

import java.io.IOException;
import java.net.Socket;

public class mainMenu
{
    @FXML
    Label exitMessage;

    @FXML
    Button onePlayerButton;

    @FXML
    protected void onePlayerModeClicked() throws IOException
    {
        //code to switch screen/scene to one player game
        TempForData.mode = 1;
        Stage stage = (Stage) onePlayerButton.getScene().getWindow();
        FXMLLoader onePlayer = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
        Parent root = onePlayer.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(600);
        sounds.playButtonClickSound();
    }

    @FXML
    protected void twoPlayerMode() throws IOException {
        TempForData.mode = 2;
        Stage stage = (Stage) onePlayerButton.getScene().getWindow();
        FXMLLoader onePlayer = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
        Parent root = onePlayer.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(600);
        sounds.playButtonClickSound();
    }

    @FXML
    protected void exitGame()
    {
        sounds.playButtonClickSound();
        exitMessage.setText("Exiting Tic Tac Toe... Have a nice day!");

        // Delay closing the stage for 2 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            Stage stage = (Stage) exitMessage.getScene().getWindow();
            stage.close();
        });
        delay.play();
    }
}
