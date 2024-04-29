package TicTacToe;

import TicTacToe.sounds.sounds;
import TicTacToe.tempForData.TempForData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class playAgain {

    @FXML
    private Button yesPlayAgain;

    @FXML
    private Button noPlayAgain;

    private gameScreen gameScreenController;


    public void setGameScreenController(gameScreen gameScreenController){

        this.gameScreenController = gameScreenController;
    }


    public void playAgainNoClicked(ActionEvent event) throws IOException {

        sounds.playButtonClickSound();
//        gameScreenController.handlePlayAgain(false);
        ((Stage) noPlayAgain.getScene().getWindow()).close();

    }

    public void playAgainYesClicked(ActionEvent event) throws IOException {

        sounds.playButtonClickSound();
//        gameScreenController.handlePlayAgain(true);
        ((Stage) yesPlayAgain.getScene().getWindow()).close();

    }


}
