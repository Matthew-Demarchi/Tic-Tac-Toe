package TicTacToe.sounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import TicTacToe.tempForData.TempForData;

import java.io.File;
import java.io.IOException;

public class sounds
{
    private static MediaPlayer mediaPlayer;

    public static void initialize() {
        Media sound = new Media(new File("src/main/resources/TicTacToe/btnclick.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }
    public static void playButtonClickSound() {
        initialize();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume((double)(TempForData.soundVolume/100.0));
        mediaPlayer.play();
    }
}
