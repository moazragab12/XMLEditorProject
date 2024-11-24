package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class mainApplication implements Initializable {

    public ImageView themeToggleImage;
    public TextArea outputArea;
    public TextFlow feedbackFlow;
    public TextArea inputArea;
    public Button btn_change_mode;
    public ScrollPane spvb;
    public VBox lineNumbers1;
    public VBox lineNumbers11;
    public ScrollPane spo;
    public ScrollPane spi;
    public ScrollPane spto;
    public ScrollPane spti;
    boolean isLightMode = false;
     FileChooser fileChooser = new FileChooser();
     File selectedFile;
     int fontSize = 12;


    // Helper Method: Updates feedback in the feedbackFlow
    private void updateFeedback(String message) {
        Text feedbackText = new Text("\n" + message);
        feedbackText.setFill(Color.GRAY);
        feedbackText.setFont(Font.font("Arial", 24));
        feedbackFlow.getChildren().clear();
        feedbackFlow.getChildren().add(feedbackText);
    }

    // Helper Method: Check if the input text area is empty
    private boolean isInputEmpty() {
        if (Objects.equals(inputArea.getText().trim(), "")) {
            updateFeedback("Input is empty");
            return true;
        }
        return false;
    }

    // Switch between Light and Dark Modes
    public void toggleTheme(ActionEvent actionEvent) {
        Scene scene = themeToggleImage.getScene();
        if (isLightMode) {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/dark_mode.css").toExternalForm());
            themeToggleImage.setImage(
                    new Image(String.valueOf(getClass().getResource("/photoes/light_mode_icon.png"))));
        } else {
            scene.getRoot().getStylesheets().clear();
            scene.getRoot().getStylesheets()
                    .add(getClass().getResource("/com/xml/editor/styles/light_mode.css").toExternalForm());
            themeToggleImage.setImage(
                    new Image(String.valueOf(getClass().getResource("/photoes/dark_mode_icon.png"))));
        }
        isLightMode = !isLightMode;
    }

    // Handles Decompression
    public void decompressFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.decomp(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File decompressed");
        }
    }

    // Handles Compression
    public void compressFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.comp(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File compressed");
        }
    }

    // Handles XML to JSON Conversion
    public void convertToJson(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.xmltoJson(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File converted to JSON");
        }
    }

    // Handles Minification
    public void minifyFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.minify(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File minified");
        }
    }

    // Handles Formatting
    public void formatFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.format(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File formatted");
        }
    }

    // Handles Repair
    public void repairFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.repair(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File repaired");
        }
    }

    // Validate XML File
    public void validateFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] errors = Functions.check(inputLines);
            outputArea.setText(String.join("\n", errors));
            if (errors[0]==null) {
                updateFeedback("File is valid");
            } else {
                updateFeedback("File is not valid. Please repair it.");
            }
        }
    }

    // Import File
    public void importFile(ActionEvent actionEvent) {
        fontSize=12;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        setupFileChooser();
        selectedFile = fileChooser.showOpenDialog(themeToggleImage.getScene().getWindow());
        if (selectedFile != null) {
            outputArea.clear();
            try (Scanner scanner = new Scanner(selectedFile)) {
                inputArea.clear();
                String temp="";
                while (scanner.hasNextLine()) {
                    temp=temp+scanner.nextLine() + "\n";
                }inputArea.appendText(temp);
                updateFeedback("File imported: " + selectedFile.getName());
            } catch (IOException e) {
                updateFeedback("Error reading file: " + e.getMessage());
            }
        } else {
            updateFeedback("File selection cancelled.");
        }
    }

    // Save Current Output
    public void saveFile(ActionEvent actionEvent) {
        if (!outputArea.getText().isEmpty()) {
            try {
                Files.write(Paths.get(selectedFile.toURI()), outputArea.getText().getBytes());
                updateFeedback("File saved successfully.");
            } catch (IOException e) {
                updateFeedback("Error saving file: " + e.getMessage());
            }
        } else {
            updateFeedback("No content to save.");
        }
    }

    // Save As New File
    public void saveAsFile(ActionEvent actionEvent) {
        setupFileChooser();
        if (!outputArea.getText().isEmpty()) {
            File saveFile = fileChooser.showSaveDialog(themeToggleImage.getScene().getWindow());
            if (saveFile != null) {
                try {
                    Files.write(Paths.get(saveFile.toURI()), outputArea.getText().getBytes());
                    updateFeedback("File saved as: " + saveFile.getName());
                } catch (IOException e) {
                    updateFeedback("Error saving file: " + e.getMessage());
                }
            }
        } else {
            updateFeedback("No content to save.");
        }
    }

    // Create a New File
    public void createNewFile(ActionEvent actionEvent) {
        inputArea.clear();
        outputArea.clear();
        fontSize = 12;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateFeedback("New file created. Enter or import content.");
    }

    // Zoom In
    public void zoomIn(ActionEvent actionEvent) {
        fontSize++;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateLineNumbers(inputArea,lineNumbers1);
        updateLineNumbers(outputArea,lineNumbers11);
    }

    // Zoom Out
    public void zoomOut(ActionEvent actionEvent) {
        fontSize--;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateLineNumbers(inputArea,lineNumbers1);
        updateLineNumbers(outputArea,lineNumbers11);
    }

    private void setupFileChooser() {
      // fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outputArea.setEditable(false);
        spvb.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal scrollbar
        spvb.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Vertical scrollbar\
        spo.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal scrollbar
        spo.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Vertical scrollbar
        spi.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal scrollbar
        spi.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Vertical scrollbar
        inputArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumbers(inputArea, lineNumbers1));
        inputArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> lineNumbers1.setLayoutY(-newValue.doubleValue()));
        updateLineNumbers(inputArea, lineNumbers1);
        outputArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumbers(outputArea, lineNumbers11));
        outputArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> lineNumbers11.setLayoutY(-newValue.doubleValue()));
        updateLineNumbers(outputArea, lineNumbers11);
        // Synchronize the vertical scroll values
        spti.vvalueProperty().bindBidirectional(spi.vvalueProperty());
        // Synchronize the vertical scroll values
        spto.vvalueProperty().bindBidirectional(spo.vvalueProperty());


    }
    private void updateLineNumbers(TextArea textArea, VBox lineNumbers) {
        // Clear current line numbers
        lineNumbers.getChildren().clear();

        // Count the lines in the TextArea
        int lineCount = textArea.getText().split("\n", -1).length;

        // Add line numbers
        for (int i = 1; i <= lineCount; i++) {
            Text lineNumber = new Text(String.valueOf(i));
            lineNumber.setFont(textArea.getFont());

            lineNumbers.getChildren().add(lineNumber);
        }
    }

    public void search_but(ActionEvent actionEvent) {
    }

    public void undo_but(ActionEvent actionEvent) {
    }

    public void redo_but(ActionEvent actionEvent) {
    }

    public void gragh_Butt(ActionEvent actionEvent) {
    }

    public void network_but(ActionEvent actionEvent) {
    }

}
