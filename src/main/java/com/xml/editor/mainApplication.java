package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;



public class mainApplication implements Initializable {

    public ImageView image_change;
    public TextArea output;
    public TextFlow cmd;
    public TextArea input;
    public AnchorPane background;
    public ScrollPane scrollpan;
    boolean isLightMode = false;
    FileChooser fileChooser = new FileChooser();

    File selectedFile;
    int fontSize=12;

    public boolean checkStringEmpty(){
       String x= input.getText();
        if (Objects.equals(x, "")){
            Text t1=new Text(" \nfile is empty ");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
            return true;
        }
        else {
            return false;
        }

    }

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

        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.decomp(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is decompressed");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_comp(ActionEvent actionEvent) {
        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.comp(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is compressed");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_json(ActionEvent actionEvent) {
        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.xmltoJson(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is transferred");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_minify(ActionEvent actionEvent) {
        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.minify(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is minified");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_format(ActionEvent actionEvent) {
        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.format(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is formated");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_correct(ActionEvent actionEvent) {
        if (!checkStringEmpty()) {
            // Split the input into lines
            String[] xmlInputCode = input.getText().split("\n");

            // Process the input with your  function
            String[] x = Functions.repair(xmlInputCode);

            // Create a StringBuilder to construct the output
            StringBuilder sb = new StringBuilder();
            for (String s : x) {
                sb.append(s).append("\n"); // Append each string in the array
            }

            // Print and set the output
            output.setText(sb.toString());

            // Display feedback
            Text t1 = new Text("\nFile is repaired");
            t1.setFill(Color.GRAY);
            t1.setFont(Font.font("Arial", 24));
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void but_play(ActionEvent actionEvent) {
        if(!checkStringEmpty()){
        String[] xmlInputCode = input.getText().split("\n");

        if(Functions.check(xmlInputCode)){
            Text t1=new Text("\nfile is correct");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);


        }else {
            Text t1=new Text("\nfile is not correct u can fix it");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);

        }}

    }



    public void import_but(ActionEvent actionEvent) {
        fontSize=12;
        input.setFont(Font.font(fontSize));
        output.setFont(Font.font(fontSize));
        // Set initial directory (optional, here set to Desktop)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        // Add file extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        // Set a title for the FileChooser window
        fileChooser.setTitle("Select a File");

        // Show the open file dialog
         selectedFile = fileChooser.showOpenDialog(image_change.getScene().getWindow());

        // Handle the selected file
        if (selectedFile != null) {
            Text t1=new Text("\nFile selected: " + selectedFile.getAbsolutePath());
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);

            // Read the content of the file
            try (Scanner scanner = new Scanner(selectedFile)) {
                input.clear(); // Clear previous output if necessary
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    input.appendText(line + "\n");
                }
            } catch (IOException e) {
               t1=new Text("\nError reading file.");
                t1.setFill(
                        Color.GRAY
                );
                t1.setFont(
                        Font.font("Arial", 24)
                );
                cmd.getChildren().clear();
                cmd.getChildren().add(t1);
                e.printStackTrace();
            }
        } else {
            // If file selection is canceled
            Text t1=new Text("\n File selection cancelled.");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }


    public void new_file_but(ActionEvent actionEvent) {
        fontSize=12;
        input.setFont(Font.font(fontSize));
        output.setFont(Font.font(fontSize));
        output.clear();
        input.clear();
        Text t1=new Text("\n Enter the code or Import file");
        t1.setFill(
                Color.GRAY
        );
        t1.setFont(
                Font.font("Arial", 24)
        );
        cmd.getChildren().clear();
        cmd.getChildren().add(t1);
    }



    public void save_but(ActionEvent actionEvent) {
        if(!Objects.equals(output.getText(), "")){
        try {
            // Write the edited content back to the file
            Files.write(Paths.get(selectedFile.toURI()), output.getText().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
            Text t1=new Text("\n file saved");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
        else{
            Text t1=new Text("\n no editing to save it");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }

    }

    public void saveAs_but(ActionEvent actionEvent) {
        if(!Objects.equals(output.getText(), "")){
            File file = fileChooser.showSaveDialog(image_change.getScene().getWindow());
            if (file != null) {
                try {
                    // Write the edited content back to the file
                    Files.write(Paths.get(file.toURI()), output.getText().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Text t1=new Text("\n file saved");
                t1.setFill(
                        Color.GRAY
                );
                t1.setFont(
                        Font.font("Arial", 24)
                );
                cmd.getChildren().clear();
                cmd.getChildren().add(t1);
            }}
        else{
            Text t1=new Text("\n no editing to save it");
            t1.setFill(
                    Color.GRAY
            );
            t1.setFont(
                    Font.font("Arial", 24)
            );
            cmd.getChildren().clear();
            cmd.getChildren().add(t1);
        }
    }

    public void zoomIn_but2(ActionEvent actionEvent) {
        input.setFont(Font.font(++fontSize));
        output.setFont(Font.font(++fontSize));
    }

    public void zoomOut_but3(ActionEvent actionEvent) {
        input.setFont(Font.font(--fontSize));
        output.setFont(Font.font(--fontSize));
    }
    public void redo_but(ActionEvent actionEvent) {

    }

    public void undo_but(ActionEvent actionEvent) {

    }
    public void search_but(ActionEvent actionEvent) {
        //next phase
    }
    public void gragh_Butt(ActionEvent actionEvent) {
        //next phase
    }

    public void network_but(ActionEvent actionEvent) {
        //next phase
    }

    public void addational_icon(ActionEvent actionEvent) {
        //next phase
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollpan.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide vertical scrollbar
        output.setEditable(false);



    }

}