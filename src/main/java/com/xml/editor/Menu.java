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
        FXMLLoader fxmlLoader2 = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Scene mainScene = new Scene(fxmlLoader2.load(), 1280, 720);

        mainStage.setScene(mainScene);
        mainStage.setTitle(" XML Editor");
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photoes/logo1.png.jpg")).toExternalForm()));
        mainStage.setResizable(false);
        mainStage.centerOnScreen();

    }
}
