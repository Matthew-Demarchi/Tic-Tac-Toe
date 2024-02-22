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


    private Stage mainStage;

    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Button> buttonsUsed = new ArrayList<>();
    private Line winnerLine;
    private ArrayList<Line> lines = new ArrayList<>();


    public void getMainStageAndButtons(Stage mainStage, ArrayList<Button> buttons, Line winnerLine, ArrayList<Button> buttonsUsed, ArrayList<Line> lines)
    {
        this.mainStage = mainStage;
        this.buttons = buttons;
        this.winnerLine = winnerLine;
        this.buttonsUsed = buttonsUsed;
        this.lines = lines;
    }


    public void playAgainNoClicked(ActionEvent event) throws IOException {

        sounds.playButtonClickSound();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicTacMainMenu2.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);

        Stage playAgainStage = (Stage) (noPlayAgain.getScene().getWindow());
        playAgainStage.close();
        buttons.clear();
        lines.clear();
        buttonsUsed.clear();
        if (winnerLine != null)
        winnerLine.setVisible(false);

    }

    public void playAgainYesClicked(ActionEvent event) throws IOException {

        sounds.playButtonClickSound();
        for (int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).setText("");
            buttons.get(i).setDisable(false);
        }
        buttonsUsed.clear();
        if (winnerLine != null)
        {
            winnerLine.setVisible(false);

        }
        Stage playAgainStage = (Stage) yesPlayAgain.getScene().getWindow();
        playAgainStage.close(); // Close the playAgain stage
    }


}
