package com.xml.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(startScene);
        stage.show();
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photoes/logo1.png.jpg")).toExternalForm()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setMinWidth(1300); // Minimum width for the scene
        stage.setMinHeight(770); // Minimum height for the scene
    }

    public static void main(String[] args) {
        launch();
    }
}