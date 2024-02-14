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

import TicTacToe.sounds.sounds;

public class gameScreen {


    @FXML
    Label currentTurnLabel;

    private static ArrayList<Button> buttons = new ArrayList<Button>();


    @FXML
    protected void boardButtonHovered(MouseEvent event)
    {
        Button button = (Button) event.getSource();
        if (buttons.size() % 2 == 0)
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
        else if (buttons.size() % 2 == 0)
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
        if (buttons.size() % 2 == 0)
        {
            button.setText("X");
            button.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, red, 3, 0.1, 0, 0);"); //
            currentTurnLabel.setText("Current Turn: O");

        }
        else
        {
            button.setText("O");
            button.setStyle("-fx-text-fill: lime; -fx-font-weight: bold; -fx-background-color: transparent; -fx-opacity: 1; -fx-effect: dropshadow(gaussian, lime, 3, 0.1, 0, 0);"); //
            currentTurnLabel.setText("Current Turn: X");
        }
        button.setDisable(true);
        buttons.add(button);

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
        controller.getMainStageAndButtons((Stage)(currentTurnLabel.getScene().getWindow()), buttons);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Set as modal dialog
        stage.setTitle("Options");
        stage.setScene(scene);
        stage.showAndWait();
    } catch (IOException e){
                e.printStackTrace();
            }
            if (buttons.size() == 0)
            {
                currentTurnLabel.setText("Current Turn: X");
            }
}}