import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer extends Application {

    private MediaPlayer player;
    private MediaPlayer player2;
    private Media song1;
    private Media song2;
    private Media song3;
    private boolean finished;

    @Override
    public void start(Stage primaryStage) throws Exception {

        song1 = new Media(new File("resources/p5.mp3").toURI().toString());
        song2 = new Media(new File("resources/z.mp3").toURI().toString());
        song3 = new Media(new File("resources/t.mp3").toURI().toString());
        player = new MediaPlayer(song1);
        player.play();

        Group grupo = new Group();

        grupo.getChildren().add(new Canvas(100, 100));

        Scene escena = new Scene(grupo);

        escena.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP:    if(player.getVolume() != 1)
                                player.setVolume(player.getVolume() + 0.1);
                            break;

                case DOWN:  if(player.getVolume() != 0)
                                player.setVolume(player.getVolume() - 0.1);
                            break;
                case LEFT:      //Fade out
                    new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            if(player.getVolume() > 0.01) {
                                player.setVolume(player.getVolume() - 0.01);
                            } else {
                                stop();
                            }
                        }
                    }.start();
                    break;
                case RIGHT:     //Play again
                            player.stop();
                            player.setVolume(1.0);
                            player.play();
                            break;
                case D:         //Mas rapido
                            player.setRate(player.getRate() + 0.1);
                            break;
                case A:         //Mas lento
                            player.setRate(player.getRate() - 0.1);
                            break;
                case P:         //Pause
                            player2 = new MediaPlayer(song2);
                            player2.setVolume(2.0);

                            player2.setOnPlaying(() ->{
                                new AnimationTimer() {
                                    @Override
                                    public void handle(long now) {
                                    if(player.getVolume() > 0.01) {
                                        player.setVolume(player.getVolume() - 0.05);
                                    } else {
                                        player.pause();
                                        stop();
                                    }
                                    }
                                }.start();
                            });

                            player2.setOnEndOfMedia(() -> {
                                finished = true;
                            });
                            player2.play();
                            break;

                case R:         //Resume
                            if(player2.getMedia().equals(song2) && finished) {

                               player2 = new MediaPlayer(song3);
                               player2.setVolume(1.0);

                               player.setOnPlaying( () -> {
                                   new AnimationTimer() {
                                       @Override
                                       public void handle(long now) {
                                           if(player.getVolume() < 1.00) {
                                               player.setVolume(player.getVolume() + 0.01);
                                           } else {
                                               stop();
                                           }
                                       }
                                   }.start();
                               });

                               finished = false;
                               player2.setOnEndOfMedia(() -> {
                                   player.play();
                               });
                               player2.play();
                            }
                            break;

            }
        });

        primaryStage.setScene(escena);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
