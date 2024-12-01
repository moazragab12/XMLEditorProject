package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public Button newFileMenu;
    public Button openFileMenu;
    FileChooser fileChooser = new FileChooser();
    File selectedFile;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Parent root = fxmlLoader.load();
        mainApplication main = fxmlLoader.getController();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Compressed Files", "*.comp"),
                new FileChooser.ExtensionFilter("json Files", "*.json")
        );
        StringBuilder temp= new StringBuilder();
        selectedFile = fileChooser.showOpenDialog(newFileMenu.getScene().getWindow());
        if (selectedFile != null) {
            try (Scanner scanner = new Scanner(selectedFile)) {
                while (scanner.hasNextLine()) {
                    temp.append(scanner.nextLine()).append("\n");
                }

            } catch (IOException ioException) {
                System.out.println("error");
            }
        }
            main.inputArea.setText(temp.toString());
        mainStage.close();
        Scene startScene = new Scene(root, 1280, 720);
        Stage stage = new Stage();
        stage.setScene(startScene);
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();


    }
}