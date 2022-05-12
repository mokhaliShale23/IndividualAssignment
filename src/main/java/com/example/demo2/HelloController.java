package com.example.demo2;

import com.sun.javafx.binding.DoubleConstant;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.Callable;

public class HelloController {


    @FXML
    private MediaView mediaView;

    @FXML
    private Tooltip tooltip;
    private MediaPlayer player;
    @FXML
    private Slider progressBar;

    @FXML
    private ImageView playing;

    @FXML
    private ImageView pausing;

    @FXML
    private ImageView stopping;

    @FXML
    private ImageView skipping;

    @FXML
    private ImageView backward;

    @FXML
    private  Slider volume;

    @FXML
    private Button play;

    @FXML
    private HBox box1;

    @FXML
    private Button pause;

    @FXML
    private Button stop;

    @FXML
    private Button negative;

    @FXML
    private Button positive;
@FXML
private Label Times;
    @FXML
    public void initialize() {
        String video = getClass().getResource("/\uD835\uDDEA\uD835\uDDF6\uD835\uDDF9\uD835\uDDF9 \uD835\uDDE6\uD835\uDDFA\uD835\uDDF6\uD835\uDE01\uD835\uDDF5 \uD835\uDDE7\uD835\uDDFF\uD835\uDDF6\uD835\uDDF2\uD835\uDDF1 \uD835\uDDE7\uD835\uDDFC \uD835\uDDDE\uD835\uDDF6\uD835\uDE00\uD835\uDE00 \uD835\uDDE6\uD835\uDDFC\uD835\uDDFD\uD835\uDDF5\uD835\uDDF6\uD835\uDDEE \uD835\uDDD4\uD835\uDDDC \uD835\uDDE5\uD835\uDDFC\uD835\uDDEF\uD835\uDDFC\uD835\uDE01 - \uD835\uDDE6\uD835\uDDF2\uD835\uDDF2 \uD835\uDDEA\uD835\uDDF5\uD835\uDDEE\uD835\uDE01 \uD835\uDDDB\uD835\uDDEE\uD835\uDDFD\uD835\uDDFD\uD835\uDDF2\uD835\uDDFB\uD835\uDDF2\uD835\uDDF1 \uD835\uDDE1\uD835\uDDF2\uD835\uDE05\uD835\uDE01.mp4").toExternalForm();
        Media media = new Media(video);
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);

        Tooltip tooltip=new Tooltip();
        tooltip.setId("tips");


        Image imageP=new Image(Objects.requireNonNull(getClass().getResource("/play.png")).toExternalForm());
        playing=new ImageView(imageP);
        playing.setFitHeight(20);
        playing.setFitWidth(20);

        Image imagePP=new Image(Objects.requireNonNull(getClass().getResource("/payes.png")).toExternalForm());
        pausing=new ImageView(imagePP);
        pausing.setFitHeight(20);
        pausing.setFitWidth(20);

        Image imageS=new Image(Objects.requireNonNull(getClass().getResource("/stop.png")).toExternalForm());
        stopping=new ImageView(imageS);
        stopping.setFitHeight(20);
        stopping.setFitWidth(20);

        Image imageB=new Image(Objects.requireNonNull(getClass().getResource("/back.png")).toExternalForm());
        backward=new ImageView(imageB);
        backward.setFitHeight(20);
        backward.setFitWidth(20);


        Image imageK=new Image(Objects.requireNonNull(getClass().getResource("/backward-arrows-couple.png")).toExternalForm());
        skipping=new ImageView(imageK);
        skipping.setFitHeight(20);
        skipping.setFitWidth(20);

        pause.setGraphic(pausing);
        play.setGraphic(playing);
        stop.setGraphic(stopping);
        negative.setGraphic(skipping);
        positive.setGraphic(backward);

        DoubleProperty withPro=mediaView.fitWidthProperty();
        DoubleProperty heightPro=mediaView.fitHeightProperty();
        withPro.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        heightPro.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));

        //settting up ID and Classes to be used in css
        play.getStyleClass().add("plays");
        pause.setId("pauses");
        box1.getStyleClass().add("box");
        stop.setId("stops");
        progressBar.setId("bar");
        positive.setId("skip");
        negative.setId("back");
        progressBar.setId("bars");

        //creating or implementing sliding bar
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        });
        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(progressBar.getValue()));
            }
        });

        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total=media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
        volume.setValue(player.getVolume()*100);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volume.getValue()/100);
            }
        });
        //implementation of time function
        Shale();
    }
    public void Shale(){
        Times.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(player.getCurrentTime()) + " ";
            }
        },player.currentTimeProperty()));
    }

    public String getTime(Duration time){
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        if(hours > 0 ) return String.format("%d:%02d%02d",
                hours, minutes, seconds);

        else return String.format("%02d:%02d",
                minutes,seconds);
    }
    //creating function for play button
    @FXML
    void playVideo(ActionEvent event) {


        player.play();
        player.setRate(1);
    }
    //creating method for pause button
    @FXML
    void pauseVideo(ActionEvent event) {
        player.pause();
    }
    //creating method for stop button
    @FXML
    void stopVideo(ActionEvent event) {
        player.stop();
    }
    //creating method for skip backward button
    @FXML
    void skip10(ActionEvent event) {
        player.seek(player.getCurrentTime().add(Duration.seconds(10)));
    }
    //creating method for skip forward button
    @FXML
    void back10(ActionEvent event) {
        player.seek(player.getCurrentTime().add(Duration.seconds(-10)));
    }
}