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
    private ArrayList<Button> buttons = new ArrayList<Button>();

    private ArrayList<Button> buttonsUsed = new ArrayList<>();
    private Line winnerLine;
    private ArrayList<Line> lines = new ArrayList<>();

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

    public void getMainStageAndButtons(Stage mainStage, ArrayList<Button> buttons, Line winnerLine, ArrayList<Button> buttonsUsed, ArrayList<Line> lines)
    {
        this.mainStage = mainStage;
        this.buttons = buttons;
        this.winnerLine = winnerLine;
        this.buttonsUsed = buttonsUsed;
        this.lines = lines;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicTacMainMenu2.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);

        Stage optionsStage = (Stage) (easyButton.getScene().getWindow());
        optionsStage.close();

        buttons.clear();
        lines.clear();
        buttonsUsed.clear();
        if (winnerLine != null)
        {
            winnerLine.setVisible(false);
        }
    }

    public void clearButtonClicked(ActionEvent event){
        sounds.playButtonClickSound();
        for (int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).setText("");
            buttons.get(i).setDisable(false);
        }
        buttonsUsed.clear();
        winnerLine.setVisible(false);

    }
}
