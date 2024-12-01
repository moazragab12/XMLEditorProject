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
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("Menu.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setScene(startScene);
        stage.show();
        stage.setTitle(" XML Editor");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    public static void main(String[] args) throws IOException {
        // CLI PART
//        if (args.length == 0) {
//            System.out.println("No command provided. Please specify an action.");
//            return;
//        }

//        CommandLine cli = new CommandLine(args);
//        cli.execute();
//        // END OF CLI PART
        commandLineV2.processCommand(args);
        launch();

    }
}