package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controller for handling search-related actions and theme toggling in the XML editor.
 * <p>
 * This class provides methods to perform word and topic searches in the editor's text area
 * and toggle between light and dark themes. The theme toggle also updates associated images.
 * </p>
 */
public class SearchController {

    /**
     * The TextArea where the draft content is displayed and searched.
     */
    public TextArea draft;

    /**
     * The TextField where the user inputs the topic for searching.
     */
    public TextField topic;

    /**
     * The TextField where the user inputs the word for searching.
     */
    public TextField word;

    /**
     * The TextFlow component used to display search results in a formatted way.
     */
    public TextFlow output;

    /**
     * The ImageView used to toggle between light and dark theme icons.
     */
    public ImageView themeToggleImage;

    /**
     * The ImageView used to display the logo, which changes based on the theme.
     */
    public ImageView xmll;

    /**
     * A boolean indicating whether the application is currently in light mode.
     */
    boolean isLightMode = false;

    /**
     * Searches for the given word in the draft text and displays the results in the output.
     * <p>
     * This method is triggered when the user clicks the "Wordpress" button. It clears the previous
     * search results and displays the new ones found by calling the {@link Functions#wordSearch(String[], String)}
     * method with the draft's text and the search word.
     * </p>
     *
     * @param actionEvent The event triggered by the "Wordpress" button click.
     */
    public void wordpress(ActionEvent actionEvent) {
        output.getChildren().clear();
        output.getChildren().add(new Text(String.join("\n", Functions.wordSearch(draft.getText().split("\n"), word.getText()))));
    }

    /**
     * Searches for the given topic in the draft text and displays the results in the output.
     * <p>
     * This method is triggered when the user clicks the "Topic" button. It clears the previous
     * search results and displays the new ones found by calling the {@link Functions#topicSearch(String[], String)}
     * method with the draft's text and the search topic.
     * </p>
     *
     * @param actionEvent The event triggered by the "Topic" button click.
     */
    public void topicPress(ActionEvent actionEvent) {
        output.getChildren().clear();
        output.getChildren().add(new Text(String.join("\n", Functions.topicSearch(draft.getText().split("\n"), topic.getText()))));
    }

    /**
     * Toggles the theme between light and dark modes.
     * <p>
     * This method updates the stylesheet of the scene, changes the theme toggle icon, and
     * switches the application logo image based on the current theme state.
     * </p>
     *
     * @param actionEvent The event triggered by clicking the theme toggle button.
     */
    public void toggleTheme(ActionEvent actionEvent) {
        Scene scene = themeToggleImage.getScene();
        if (isLightMode) {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/dark_mode.css").toExternalForm());
            themeToggleImage.setImage(new Image(String.valueOf(getClass().getResource("/photos/light_mode_icon.png"))));
            xmll.setImage(new Image(String.valueOf(getClass().getResource("/photos/logo2.png"))));
        } else {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/light_mode.css").toExternalForm());
            themeToggleImage.setImage(new Image(String.valueOf(getClass().getResource("/photos/dark_mode_icon.png"))));
            xmll.setImage(new Image(String.valueOf(getClass().getResource("/photos/logo1.png.jpg"))));
        }
        isLightMode = !isLightMode;
    }
}
