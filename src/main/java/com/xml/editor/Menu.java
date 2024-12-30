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

/**
 * The `Menu` class provides functionality for managing the main menu of the XML Editor application.
 * It handles opening a new file or an existing file via the graphical user interface.
 */
public class Menu {
    public Button newFileMenu;  // Button to create a new file
    public Button openFileMenu; // Button to open an existing file
    FileChooser fileChooser = new FileChooser();  // File chooser for selecting files
    File selectedFile;  // Variable to hold the selected file

    /**
     * Handles the action when the "New File" button is clicked.
     * This method closes the current window and opens the main application window to start a new file.
     *
     * @param actionEvent The action event triggered when the "New File" button is clicked.
     * @throws IOException If an error occurs while loading the new scene.
     */
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

    /**
     * Handles the action when the "Open File" button is clicked.
     * This method opens a file chooser, allows the user to select a file, and displays the contents of the file
     * in the main application window.
     *
     * @param actionEvent The action event triggered when the "Open File" button is clicked.
     * @throws IOException If an error occurs while loading the selected file or the new scene.
     */
    public void openNewFileMenu(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) newFileMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("mainApplication.fxml"));
        Parent root = fxmlLoader.load();
        mainApplication main = fxmlLoader.getController();

        // Set file extensions filter for the file chooser
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Compressed Files", "*.comp"),
                new FileChooser.ExtensionFilter("json Files", "*.json")
        );

        StringBuilder temp = new StringBuilder();
        selectedFile = fileChooser.showOpenDialog(newFileMenu.getScene().getWindow());

        // If a file is selected, read its contents and set the text area in the main application
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

        // Close the current stage and show the new stage with the file content
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
