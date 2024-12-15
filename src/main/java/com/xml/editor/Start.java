package com.xml.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("menu.fxml"));
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

    public static class NetworkAnalysis {
        User[] users;

        public User[] getUsers(){
            return users;
        }

        //get info from xml file and save users in com.xml.editor.User array
        public void ReadFile(String s){

        }

        public User mostInfluencer(){
            int index = 0;
            int mostfollowed = users[0].followers.size();
            for(int i = 0; i < users.length; i++){
                if(mostfollowed < users[i].followers.size()){
                    mostfollowed = users[i].followers.size();
                    index = i;
                }
            }

            return users[index];
        }

        public User mostActive(){
            int index = 0;


            return users[index];
        }

        public ArrayList<User> mutualFollowers(String U1, String U2){
            ArrayList<User> mutualF = new ArrayList<>();

            return mutualF;
        }

        public ArrayList<User> suggestedFollowers(){
            ArrayList<User> suggest = new ArrayList<>();



            return suggest;
        }
    }
}