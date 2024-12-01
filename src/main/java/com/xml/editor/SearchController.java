package com.xml.editor;


import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SearchController {

    public TextArea draft;
    public TextField topic;
    public TextField word;
    public TextFlow output;
    public ImageView themeToggleImage;
    public ImageView xmll;
    boolean isLightMode = false;

    public void wordpress(ActionEvent actionEvent) {
        output.getChildren().add(new Text(String.join("\n",Functions.wordSearch(draft.getText().split("\n"),word.getText()))));
    }

    public void topicPress(ActionEvent actionEvent) {
        output.getChildren().add(new Text(String.join("\n",Functions.topicSearch(draft.getText().split("\n"),topic.getText()))));

    }

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
}
