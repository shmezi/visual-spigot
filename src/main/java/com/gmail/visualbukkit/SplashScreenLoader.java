package com.gmail.visualbukkit;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenLoader extends Preloader {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        BorderPane rootPane = new BorderPane();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        rootPane.getStylesheets().add("/style.css");
        try (InputStream inputStream = VisualBukkit.class.getResourceAsStream("/Visual_Spigot.png")) {
            Image icon = new Image(inputStream, 250, 250, true, true);
            VBox vBox = new VBox(15, new ImageView(icon));
            vBox.setStyle("-fx-alignment: center; -fx-font-size: 36; -fx-padding: 20; -fx-font-smoothing-type: gray; -fx-background-color: rgba(255,0,0,0); -fx-translate-y: 100;");
            rootPane.setCenter(vBox);
            primaryStage.setTitle("Visual Spigot");
            primaryStage.setScene(new Scene(rootPane, 300, 400, Color.TRANSPARENT));
            primaryStage.getIcons().add(icon);
            rootPane.setStyle("-fx-background-color: transparent;");
            primaryStage.show();
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        if (notification instanceof ProgressNotification && ((ProgressNotification) notification).getProgress() == 1) {
            primaryStage.close();
        }
    }
}
