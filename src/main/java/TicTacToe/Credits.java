package TicTacToe;


import TicTacToe.tempForData.TempForData;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Credits
{
    @FXML
    AnchorPane creditsPane;

    public void initialize()
    {
        switch (TempForData.currentTheme)
        {
            case 0:
                creditsPane.setStyle("-fx-background-color: linear-gradient(to bottom, #B0E0E6, #FFFFFF, #C0C0C0)");

                break;
            case 1:
                creditsPane.setStyle("-fx-background-color: linear-gradient(to bottom, lightgreen, lime, gold);");
                break;
            case 2:
                creditsPane.setStyle("-fx-background-color: linear-gradient(to bottom, Skyblue, yellow, darkorange,seagreen);");
                break;
            case 3:
                creditsPane.setStyle("-fx-background-color: linear-gradient(to bottom, crimson, lightcoral, skyblue, deepskyblue);");
                break;
        }
    }
}
