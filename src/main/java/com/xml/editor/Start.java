package com.xml.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The {@code Start} class serves as the main entry point for the XML Editor application.
 * It initializes the JavaFX GUI and processes command-line arguments for CLI functionality.
 */
public class Start extends Application {

    /**
     * The main entry point for launching the JavaFX application.
     *
     * @param stage the primary stage for this application.
     * @throws IOException if the FXML file or application resources cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("menu.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 800, 450);

        stage.setScene(startScene);
        stage.setTitle("XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResource("/photos/logo3.png")).toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * The main method serves as the entry point for the application.
     * It supports both GUI and command-line interface (CLI) modes.
     *
     * @param args command-line arguments. If no arguments are provided, the application
     *             launches in GUI mode. CLI commands can also be processed.
     * @throws IOException if an error occurs while processing command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        // CLI PART (uncomment for enabling CLI functionality)
        // if (args.length == 0) {
        //     System.out.println("No command provided. Please specify an action.");
        //     return;
        // }
        // CommandLine cli = new CommandLine(args);
        // cli.execute();
        // END OF CLI PART

        commandLineV2.processCommand(args); // Process command-line arguments
        launch(); // Launch the JavaFX application
    }
}
