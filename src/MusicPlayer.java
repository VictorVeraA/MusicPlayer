import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class MusicPlayer extends Application {

    private MediaPlayer player;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Media song = new Media(new File("resources/p5.mp3").toURI().toString());
        player = new MediaPlayer(song);
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
            }
        });

        primaryStage.setScene(escena);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
