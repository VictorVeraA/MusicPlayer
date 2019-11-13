import javafx.animation.AnimationTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    private MediaPlayer player;
    private Media song;
    private String pathToFile;

    public MusicPlayer() {

    }

    public MusicPlayer(String file) {
        pathToFile = "resources/";
        song = new Media(new File( pathToFile + file).toURI().toString());
        player = new MediaPlayer(song);
    }

    public MusicPlayer(String pathToFile, String file) {

        this.pathToFile = pathToFile;
        song = new Media(new File(pathToFile + file).toURI().toString());
        player = new MediaPlayer(song);
    }

    public int play() {
        if(player != null && !isSongPLaying()) {
            player.play();
            return 0;
        }

        return -1;
    }

    public int pause() {
        if(player != null && !isSongPLaying()) {
            player.pause();
            return 0;
        }

        return -1;
    }

    public int stop() {
        if(player != null && isSongPLaying()) {
            player.stop();
            return 0;
        }

        return -1;
    }

    public int fadeOut() {
        if(isSongPLaying()) {

            new Thread(() -> new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if(player.getVolume() > 0.01) {
                        player.setVolume(player.getVolume() - 0.01);
                    } else {
                        player.stop();
                        stop();
                    }
                }
            }.start()).start();

            return 0;
        }

        return -1;
    }

    public boolean isSongPLaying() {
        if(player != null)
            return player.getStatus() == MediaPlayer.Status.PLAYING;

        return false;
    }

    public int setSong(String file) {
        if(isSongPLaying()) {
            player.stop();
        }

        song = new Media(new File(pathToFile + file).toURI().toString());

        if(song != null) {
            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }

    public int setSongAndPlay(String file) {
        if(setSong(file) != -1) {
            player.play();
            return 0;
        }

        return -1;
    }

    public int setVolume(double volume) {
        if(volume >= 0.0 && volume <= 1.0) {
            player.setVolume(volume);
            return 0;
        }

        return -1;
    }

    public int fadeToVolume(double volume) {
        if(volume >= 0.0 && volume <= 1.0 && volume != player.getVolume()) {

            if(volume < player.getVolume()) {

                new Thread(() -> new AnimationTimer() {
                    @Override
                    public void handle(long now) {

                        if(player.getVolume() > volume) {
                            player.setVolume(player.getVolume() - volume/240);  //Fade en 4 segundos
                        } else {
                            stop();
                        }
                    }
                }.start()).start();

            } else if(volume > player.getVolume()) {

                new Thread(() -> new AnimationTimer() {
                    @Override
                    public void handle(long now) {

                        if(player.getVolume() < volume) {
                            player.setVolume(player.getVolume() + volume/240);  //Fade en 4 segundos
                        } else {
                            stop();
                        }
                    }
                }.start()).start();

            }

            return 0;
        }

        return -1;
    }



    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public int createPlayer() {
        if(player == null && song != null) {
            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }

    public int createPlayer(String file) {
        if(player == null) {

            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }


}
