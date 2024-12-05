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

public class NetworkAnalysisController {
    public ImageView themeToggleImage;
    public TextFlow output;
    public ImageView xmll;
    public TextArea draft;
    public TextField mutualUsers;
    public TextField suggestUser;
    boolean isLightMode = false;

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

    public void mutualuserpress(ActionEvent actionEvent) {
        int i;
        String[] xml =draft.getText().split("\n");
        String[] stringArray =mutualUsers.getText().split(" ");
        int[] intArray = new int[stringArray.length];
        for (i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
                System.out.println(intArray[i]);
            } catch (NumberFormatException e) {
                output.getChildren().clear();
                output.getChildren().add(new Text("Invalid input: " + stringArray[i]));
                break;
            }
        }
        if(i==stringArray.length) {
            output.getChildren().clear();
            output.getChildren().add(new Text(String.join("\n",Functions.mutualFollowers(xml,intArray))));
        }
    }

    public void suggestUserPress(ActionEvent actionEvent) {
        String[] xml =draft.getText().split("\n");
        try {
            output.getChildren().clear();
            String s=String.join("\n",Functions.suggest(xml,Integer.parseInt(suggestUser.getText())));
            if(s.isEmpty())output.getChildren().add(new Text("No Suggestions for the user with id "+suggestUser.getText()));
            else output.getChildren().add(new Text(s));
        } catch (Exception e) {
            output.getChildren().clear();
            output.getChildren().add(new Text("Invalid input: " + suggestUser.getText()));
        }


    }

    public void networkDataPress(ActionEvent actionEvent) throws IOException {
      String[] xml =draft.getText().split("\n");
      output.getChildren().clear();
      output.getChildren().add(new Text(String.join("\n",Functions.networkAnalysis(xml))));
    }
}
