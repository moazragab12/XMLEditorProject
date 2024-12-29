package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

/**
 * Controller class for handling the interactions in the network analysis UI.
 * It allows the user to toggle themes, analyze mutual followers, suggest users, and perform network analysis on XML data.
 */
public class NetworkAnalysisController {

    public ImageView themeToggleImage; // ImageView for theme toggle button
    public TextFlow output;            // TextFlow to display output messages
    public ImageView xmll;             // ImageView for the logo
    public TextArea draft;             // TextArea for input XML data
    public TextField mutualUsers;      // TextField for entering user IDs to find mutual followers
    public TextField suggestUser;      // TextField for entering user ID to suggest users
    boolean isLightMode = false;       // Flag to track if the light mode is enabled

    /**
     * Toggles the theme between light and dark mode.
     * Updates the stylesheets and image icons based on the current theme.
     *
     * @param actionEvent The action event triggered by the theme toggle button.
     */
    public void toggleTheme(ActionEvent actionEvent) {
        Scene scene = themeToggleImage.getScene();
        if (isLightMode) {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/dark_mode.css").toExternalForm());
            themeToggleImage.setImage(
                    new Image(String.valueOf(getClass().getResource("/photos/light_mode_icon.png"))));
            xmll.setImage(new Image(String.valueOf(getClass().getResource("/photos/logo2.png"))));
        } else {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/light_mode.css").toExternalForm());
            themeToggleImage.setImage(
                    new Image(String.valueOf(getClass().getResource("/photos/dark_mode_icon.png"))));
            xmll.setImage(new Image(String.valueOf(getClass().getResource("/photos/logo1.png.jpg"))));
        }
        isLightMode = !isLightMode;
    }

    /**
     * Handles the action when the "Mutual Users" button is pressed.
     * Parses the input user IDs and finds mutual followers between the users.
     * If the input is invalid, displays an error message.
     *
     * @param actionEvent The action event triggered by the "Mutual Users" button.
     */
    public void mutualuserpress(ActionEvent actionEvent) {
        int i;
        String[] xml = draft.getText().split("\n");
        String[] stringArray = mutualUsers.getText().split(" ");
        int[] intArray = new int[stringArray.length];
        for (i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException e) {
                break;
            }
        }
        if (i == stringArray.length && i != 1) {
            output.getChildren().clear();
            output.getChildren().add(new Text(String.join("\n", Functions.mutualFollowers(xml, intArray))));
            if (String.join("\n", Functions.mutualFollowers(xml, intArray)).isEmpty())
                output.getChildren().add(new Text("No mutual followers between these IDs."));
        } else {
            output.getChildren().clear();
            output.getChildren().add(new Text("Invalid input: " + stringArray[i]));
        }
    }

    /**
     * Handles the action when the "Suggest User" button is pressed.
     * Suggests users for a given user ID.
     * If the input is invalid or there are no suggestions, it displays an appropriate message.
     *
     * @param actionEvent The action event triggered by the "Suggest User" button.
     */
    public void suggestUserPress(ActionEvent actionEvent) {
        String[] xml = draft.getText().split("\n");
        try {
            output.getChildren().clear();
            String s = String.join("\n", Functions.suggest(xml, Integer.parseInt(suggestUser.getText())));
            if (s.isEmpty())
                output.getChildren().add(new Text("No suggestions for the user with ID " + suggestUser.getText()));
            else
                output.getChildren().add(new Text(s));
        } catch (Exception e) {
            output.getChildren().clear();
            output.getChildren().add(new Text("Invalid input: " + suggestUser.getText()));
        }
    }

    /**
     * Handles the action when the "Network Data" button is pressed.
     * Analyzes the network data based on the input XML data and displays the results.
     *
     * @param actionEvent The action event triggered by the "Network Data" button.
     * @throws IOException If an I/O error occurs while processing the data.
     */
    public void networkDataPress(ActionEvent actionEvent) throws IOException {
        String[] xml = draft.getText().split("\n");
        output.getChildren().clear();
        output.getChildren().add(new Text(String.join("\n", Functions.networkAnalysis(xml))));
    }
}
