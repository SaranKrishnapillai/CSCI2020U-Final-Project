package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
class Score
{
    String userName;
    int score;
    public Score(String name, int score)
    {
        this.score = score;
        this.userName = name;
    }
    String getUserName()
    {
        return this.userName;
    }
    int getScore()
    {
        return this.score;
    }
    void setUserName(String name)
    {
        this.userName = name;
    }
    void setScore(int score)
    {
        this.score = score;
    }
}

public class Controller {
    public Button button1;

    public Button button2;

    public Button button3;

    public Button button4;

    public Label lbl1;

    public ListView list;


    public void switchPlay() {
        button1.setStyle("-fx-background-color: deepskyblue; -fx-font: 22 'Star Jedi'; -fx-text-fill: white;");
    }

    public void switchPlay1() {
        button1.setStyle("-fx-background-color: white; -fx-font: 22 'Star Jedi'; -fx-text-fill: deepskyblue;");
    }

    public void switchDiffi() {
        button2.setStyle("-fx-background-color: red; -fx-font: 22 'Star Jedi'; -fx-text-fill: white;");
    }

    public void switchDiffi1() {
        button2.setStyle("-fx-background-color: white; -fx-font: 22 'Star Jedi'; -fx-text-fill: red;");
    }

    public void switchMusic() {
        button3.setStyle("-fx-background-color: limegreen; -fx-font: 18 'Star Jedi'; -fx-text-fill: white;");
    }

    public void switchMusic1() {
        button3.setStyle("-fx-background-color: white; -fx-font: 18 'Star Jedi'; -fx-text-fill: limegreen;");
    }

    public void switchScore() {
        button4.setStyle("-fx-background-color: darkviolet; -fx-font: 14 'Star Jedi'; -fx-text-fill: white;");
    }

    public void switchScore1() {
        button4.setStyle("-fx-background-color: white; -fx-font: 14 'Star Jedi'; -fx-text-fill: darkviolet;");
    }

    public void playPress()
    {
        System.out.println("Starts Game");
    }
    public void diffiPress()
    {
        if(lbl1.getText().equals("Easy"))
            lbl1.setText("Normal");
        else if(lbl1.getText().equals("Normal"))
            lbl1.setText("Hard");
        else if(lbl1.getText().equals("Hard"))
            lbl1.setText("Easy");
    }
    String path = "The Imperial March.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    public void musicPress()
    {
        mediaPlayer.setAutoPlay(true);
        if(mediaPlayer.isAutoPlay()) {
            mediaPlayer.stop();
            mediaPlayer.setAutoPlay(false);
        }
    }

    Score[] top10 = {new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),
            new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0),new Score("---",0)};
    public void scorePress()
    {
        for(int i = 0; i < top10.length; i++)
        {
            String a = "";
            a += (top10[i].getUserName() + "          " + top10[i].getScore());
            list.getItems().add(a);
        }
    }


}
