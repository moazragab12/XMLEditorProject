package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class mainApplication {

    public ImageView image_change;
    public TextFlow output;
    public TextFlow cmd;
    public TextArea input;
    public AnchorPane background;
    boolean isLightMode = false;

    public void btn_change_mode(ActionEvent actionEvent) {
        Scene scene = image_change.getScene();
        if (isLightMode) {
            // change to dark
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets().add(getClass().getResource("/com/xml/editor/styles/dark_mode.css").toExternalForm());
            image_change.setImage(new Image(String.valueOf(getClass().getResource("/photoes/light_mode_icon.png"))));

        } else {
            // change to light
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets().add(getClass().getResource("/com/xml/editor/styles/light_mode.css").toExternalForm());
            image_change.setImage(new Image(String.valueOf(getClass().getResource("/photoes/dark_mode_icon.png"))));
        }
        isLightMode = !isLightMode;
    }

    public void but_decomp(ActionEvent actionEvent) {
    }

    public void but_comp(ActionEvent actionEvent) {
    }

    public void but_json(ActionEvent actionEvent) {
    }

    public void but_minify(ActionEvent actionEvent) {
    }

    public void but_format(ActionEvent actionEvent) {
    }

    public void but_correct(ActionEvent actionEvent) {
    }

    public void but_play(ActionEvent actionEvent) {
    }

    public void search_but(ActionEvent actionEvent) {
    }

    public void import_but(ActionEvent actionEvent) {
    }

    public void new_file_but(ActionEvent actionEvent) {
        cmd.getChildren().clear();
        output.getChildren().clear();
        input.clear();
    }
}