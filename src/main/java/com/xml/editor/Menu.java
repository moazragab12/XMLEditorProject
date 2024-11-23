package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Menu {
    public Button newFileMenu;
    public void newFileMenu(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) newFileMenu.getScene().getWindow();
        mainStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setScene(startScene);
        stage.show();
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photoes/logo1.png.jpg")).toExternalForm()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setMinWidth(1300); // Minimum width for the scene
        stage.setMinHeight(770); // Minimum height for the scene
    }
}
