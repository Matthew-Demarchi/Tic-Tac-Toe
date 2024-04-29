package TicTacToe;

import TicTacToe.tempForData.TempForData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import TicTacToe.sounds.sounds;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class inGameOptions {
    private Stage mainStage;

    private Line winnerLine;


    @FXML
    private ToggleButton sidesOnButton;

    @FXML
    private ToggleButton sidesOffButton;

    @FXML
    private ToggleButton normalButton;

    @FXML
    private ToggleButton easyButton;

    @FXML
    Slider musicSlider;
    @FXML
    Slider soundSlider;
    @FXML
    Button clearButton;

    private gameScreen gameScreenController;


    public void setGameScreenController(gameScreen gameScreenController){

        this.gameScreenController = gameScreenController;
    }
    public void isVSRealPlayer (boolean vsRealPlayer)
    {
        if (vsRealPlayer)
        {
            sidesOnButton.setDisable(true);
            sidesOffButton.setDisable(true);
            normalButton.setDisable(true);
            easyButton.setDisable(true);
            clearButton.setDisable(true);
        }
    }

    private void setToggleButtonColors()
    {
        if (normalButton.isSelected())
        {
            normalButton.setStyle("-fx-background-color: lightblue;");
            easyButton.setStyle("-fx-background-color: transparent;");
        }
        else
        {
            easyButton.setStyle("-fx-background-color: lightblue;");
            normalButton.setStyle("-fx-background-color: transparent;");
        }

        if (sidesOnButton.isSelected())
        {
            sidesOnButton.setStyle("-fx-background-color: lightgreen;");
            sidesOffButton.setStyle("-fx-background-color: transparent;");
        }
        else
        {
            sidesOffButton.setStyle("-fx-background-color: pink;");
            sidesOnButton.setStyle("-fx-background-color: transparent;");
        }
    }


    public void initialize(){
        easyButton.setSelected(TempForData.easyButton);
        sidesOffButton.setSelected(TempForData.sidesOffButton);
        normalButton.setSelected(TempForData.normalButton);
        sidesOnButton.setSelected(TempForData.sidesOnButton);
    

        setToggleButtonColors();

        soundSlider.setValue(TempForData.soundVolume);
        musicSlider.setValue(TempForData.musicVolume);

        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            TempForData.soundVolume = newValue.intValue();
        });
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            TempForData.musicVolume = newValue.intValue();
            sounds.updateMusicVolume();
        });
    }

    public void sidesOnButtonClicked(ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.sidesOffButton = false;
        TempForData.sidesOnButton = true;


        if (sidesOnButton.isSelected()) {
            // Change color when selected
            sidesOnButton.setStyle("-fx-background-color: lightgreen;");
            sidesOffButton.setStyle("-fx-background-color: transparent;");
        }
    }

        public void sidesOffButtonClicked (ActionEvent event) {
            sounds.playButtonClickSound();
            TempForData.sidesOffButton = true;
            TempForData.sidesOnButton = false;

            if (sidesOffButton.isSelected()) {
                // Change color when selected
                sidesOnButton.setStyle("-fx-background-color: transparent;");
                sidesOffButton.setStyle("-fx-background-color: pink;");
            }

        }


    public void easyButtonClicked (ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.easyButton = true;
        TempForData.normalButton = false;

        if(easyButton.isSelected()){
            easyButton.setStyle("-fx-background-color: lightblue;");
            normalButton.setStyle("-fx-background-color: transparent;");
        }

    }

    public void normalButtonClicked (ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.easyButton = false;
        TempForData.normalButton = true;

        if(normalButton.isSelected()){
            easyButton.setStyle("-fx-background-color: transparent;");
            normalButton.setStyle("-fx-background-color: lightblue;");
        }
    }

    public void quitButtonClicked(ActionEvent event) throws IOException {
        sounds.playButtonClickSound();

        Stage optionsStage = (Stage) (easyButton.getScene().getWindow());
        optionsStage.close();

        gameScreenController.handleOptionsQuit(true);
        ((Stage) easyButton.getScene().getWindow()).close();
    }

    public void clearButtonClicked(ActionEvent event){
        sounds.playButtonClickSound();

        Stage optionsStage = (Stage) (easyButton.getScene().getWindow());
        gameScreenController.handleOptionsClear(true);

        optionsStage.close();


    }
}
