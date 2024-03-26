package TicTacToe.sounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import TicTacToe.tempForData.TempForData;

import java.io.File;
import java.io.IOException;

public class sounds
{
    private static MediaPlayer mediaPlayer;
    public static MediaPlayer backgroundMediaPlayer;

    public static void initialize() {
        Media sound = new Media(new File("src/main/resources/TicTacToe/btnclick.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

        Media backgroundMusic = new Media(new File("src/main/resources/TicTacToe/subway_surfers.mp3").toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
    public static void playButtonClickSound() {
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume((double)(TempForData.soundVolume/100.0));
        mediaPlayer.play();
    }
    public static void playBackgroundMusic(){
        backgroundMediaPlayer.seek(backgroundMediaPlayer.getStartTime());
        backgroundMediaPlayer.play();
        backgroundMediaPlayer.setVolume((double)(TempForData.musicVolume/100.0));
    }
    public static void setBackgroundMusicVolume(double volume) {
        backgroundMediaPlayer.setVolume(volume);
    }
}
