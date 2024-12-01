package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public Button newFileMenu;
    public Button openFileMenu;

    public void newFileMenu(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) newFileMenu.getScene().getWindow();
        mainStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setScene(startScene);
        stage.show();
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
    }


    public void openNewFileMenu(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) newFileMenu.getScene().getWindow();
        //mainStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Parent root = fxmlLoader.load();
        mainApplication main = fxmlLoader.getController();

//        main.inputArea();
//        main.fontSize = 12;
//        main.inputArea.setFont(Font.font(main.fontSize));
//        main.outputArea.setFont(Font.font(main.fontSize));
//        main.setupFileChooser();
//        main.selectedFile = main.fileChooser.showOpenDialog(main.themeToggleImage.getScene().getWindow());
//        if (main.selectedFile != null) {
//            main.outputArea.clear();
//            try (Scanner scanner = new Scanner(main.selectedFile)) {
//                main.inputArea.clear();
//                String temp = "";
//                while (scanner.hasNextLine()) {
//                    temp = temp + scanner.nextLine() + "\n";
//                }
//                main.inputArea.appendText(temp);


        Scene startScene = new Scene(root, 1280, 720);
        Stage stage = new Stage();
        stage.setScene(startScene);
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
        //stage.show();


    }
}