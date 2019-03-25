package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane pane = new Pane();

        Label lbl = new Label("Game Title");
        lbl.setStyle(("-fx-text-fill: yellow; -fx-font: 50 'Star Jedi Hollow' "));
        lbl.setTranslateX(145);
        lbl.setTranslateY(10);

        Button button = new Button("Play");
        button.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: deepskyblue;");
        button.setTranslateX(125);
        button.setTranslateY(120);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starts Game");
            }
        });
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setStyle("-fx-background-color: deepskyblue; -fx-font: 25 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: deepskyblue;");
                    }
                });

        TextField txt = new TextField("Normal");
        txt.setTranslateX(180);
        txt.setTranslateY(325);
        txt.setEditable(false);
        txt.setAlignment(Pos.CENTER);
        txt.setStyle("-fx-background-color: transparent; -fx-font: 20 'Star Jedi'; -fx-text-fill: white");

        Button button2 = new Button("Difficulty");
        button2.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: red;");
        button2.setTranslateX(205);
        button2.setTranslateY(235);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(txt.getText().equals("Easy"))
                    txt.setText("Normal");
                else if(txt.getText().equals("Normal"))
                    txt.setText("Hard");
                else if(txt.getText().equals("Hard"))
                    txt.setText("Easy");

            }
        });
        button2.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button2.setStyle("-fx-background-color: red; -fx-font: 25 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button2.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button2.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: red;");
                    }
                });

        Button button3 = new Button("Music");
        button3.setStyle("-fx-background-color: white; -fx-font: 21 'Star Jedi'; -fx-text-fill: limegreen;");
        button3.setTranslateX(250);
        button3.setTranslateY(385);

        String path = "C:/Users/Saran Sanchez/IdeaProjects/javathingy/src/The Imperial March.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        button3.setOnAction(event -> {
            System.out.println(mediaPlayer.isAutoPlay());
            mediaPlayer.setAutoPlay(true);
            if(mediaPlayer.isAutoPlay()) {
                mediaPlayer.stop();
                mediaPlayer.setAutoPlay(false);
            }

        });
        button3.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button3.setStyle("-fx-background-color: limegreen; -fx-font: 21 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button3.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button3.setStyle("-fx-background-color: white; -fx-font: 21 'Star Jedi'; -fx-text-fill: limegreen;");
                    }
                });

        Button button4 = new Button("Multiplayer");
        button4.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: darkviolet;");
        button4.setTranslateX(317);
        button4.setTranslateY(120);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starts Multiplayer");
            }
        });
        button4.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button4.setStyle("-fx-background-color: darkviolet; -fx-font: 25 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button4.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button4.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: darkviolet;");
                    }
                });

        ImageView img = new ImageView("SpaceBackGround.jpg");
        img.setFitHeight(500);
        img.setFitWidth(600);



        pane.getChildren().add(img);
        pane.getChildren().add(lbl);
        pane.getChildren().addAll(button,button2,txt,button3,button4);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(pane, 600, 500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
