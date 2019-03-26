package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    Stage window;
    Scene scene1,scene2;

    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;

    public String address = "localhost";
    public static int port = 1700;


    @Override
    public void start(Stage primaryStage) throws Exception{



        window = primaryStage;
        Game_Content g = new Game_Content();
        Pane pane = new Pane();

        Label lbl = new Label("Space Blaster 3000");
        lbl.setStyle(("-fx-text-fill: yellow; -fx-font: 45 'Star Jedi Hollow' "));
        lbl.setTranslateX(55);
        lbl.setTranslateY(10);

        Button button = new Button("Play");
        button.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: deepskyblue;");
        button.setTranslateX(125);
        button.setTranslateY(120);
        TextField userName = new TextField("Enter Username Here");

        userName.setTranslateX(125);
        userName.setTranslateY(180);


        button.setOnAction(new EventHandler<ActionEvent>() {
            String userName1 = userName.getText();

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starts Game");

                try
                {
                    socket = new Socket(address, port);
                    System.out.println("Connected");


                    // sends output to the socket
                    out    = new DataOutputStream(socket.getOutputStream());
                }catch(UnknownHostException u)
                {
                    System.out.println(u);
                }
                catch(IOException i) {
                    System.out.println(i);
                }

                // string to read message from input
                String line = "!";
                String userName1 = userName.getText();

                // keep reading until "Over" is input
                while (line.equals("!"))
                {
                    try
                    {
                        //line = input.readLine();
                        line = userName1;
                        out.writeUTF(line);
                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }
                }

                // close the connection
                try
                {

                    out.close();
                    socket.close();
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }


                window.setScene(scene2);
                scene2.setOnKeyPressed(e -> g.keyPressed(e));
                scene2.setOnKeyReleased(e -> g.keyReleased(e));
                System.out.println();
            }
        });


        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setStyle("-fx-background-color: deepskyblue; -fx-font: 25 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: deepskyblue;");
                    }
                });

        TextField txt = new TextField("Normal");
        txt.setTranslateX(100);
        txt.setTranslateY(325);
        txt.setEditable(false);
        txt.setAlignment(Pos.CENTER);
        txt.setStyle("-fx-background-color: transparent; -fx-font: 20 'Star Jedi'; -fx-text-fill: white");

        Button button2 = new Button("Difficulty");
        button2.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: red;");
        button2.setTranslateX(125);
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
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button2.setStyle("-fx-background-color: red; -fx-font: 25 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button2.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button2.setStyle("-fx-background-color: white; -fx-font: 25 'Star Jedi'; -fx-text-fill: red;");
                    }
                });

        Button button3 = new Button("Music");
        button3.setStyle("-fx-background-color: white; -fx-font: 21 'Star Jedi'; -fx-text-fill: limegreen;");
        button3.setTranslateX(125);
        button3.setTranslateY(385);

        String path = "The Imperial March.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        button3.setOnAction(event -> {
            mediaPlayer.setAutoPlay(true);
            if(mediaPlayer.isAutoPlay()) {
                mediaPlayer.stop();
                mediaPlayer.setAutoPlay(false);
            }

        });
        button3.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button3.setStyle("-fx-background-color: limegreen; -fx-font: 21 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button3.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button3.setStyle("-fx-background-color: white; -fx-font: 21 'Star Jedi'; -fx-text-fill: limegreen;");
                    }
                });

        Button button4 = new Button("Scores");
        button4.setStyle("-fx-background-color: white; -fx-font: 20 'Star Jedi'; -fx-text-fill: darkviolet;");
        button4.setTranslateX(355);
        button4.setTranslateY(125);

        Scanner s = new Scanner(new File("src/sample/leaderboards.txt"));
        ObservableList<String> list = FXCollections.observableArrayList();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();


        ListView<String> list1 = new ListView<String>(list);
        list1.setPrefSize(110,240);
        list1.setTranslateX(355);
        list1.setTranslateY(200);
        Score[] top10 = {new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),
                new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0)};
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0; i < top10.length; i++)
                {
                    String a = "";
                    a += (top10[i].getName() + "          " + top10[i].getScore());
                    //list1.getItems().add(a);
                }
            }
        });
        button4.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button4.setStyle("-fx-background-color: darkviolet; -fx-font: 20 'Star Jedi'; -fx-text-fill: white;");
                    }
                });
        button4.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button4.setStyle("-fx-background-color: white; -fx-font: 20 'Star Jedi'; -fx-text-fill: darkviolet;");
                    }
                });


        ImageView img = new ImageView("sample/SpaceBackGround.jpg");
        img.setFitHeight(500);
        img.setFitWidth(600);





        pane.getChildren().add(img);
        pane.getChildren().add(lbl);
        pane.getChildren().addAll(button,button2,txt,button3,button4,list1,userName);
        scene2 = new Scene(g.getRoot(),800,600);
        scene1 = new Scene(pane,600,500);
        window.setTitle("Main Menu");
        window.setScene(scene1);
        window.show();

    }

    public static void insert(Score[] top10,Score s)
    {
        Score[] copy = top10.clone();
        for(int i = 0; i < 10; i++)
        {
            if(top10[i].score <= s.score)
            {
                for(int j = i; j < 9; j++)
                {
                    top10[j+1] = copy[j];
                }
                top10[i] = s;
                break;
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

