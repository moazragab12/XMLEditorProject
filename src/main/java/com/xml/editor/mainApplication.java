package com.xml.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
/**
 * The mainApplication class handles the functionality of an XML editor application.
 * It provides methods for theme switching, file operations (compression, decompression, import/export),
 * and text formatting and validation. The application allows users to work with XML, JSON, and other file types.
 */
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
    public ImageView xmll;
    boolean isLightMode = false;
     FileChooser fileChooser = new FileChooser();
     File selectedFile;
     int fontSize = 12;
    /**
     * Updates the feedback displayed in the feedbackFlow TextFlow.
     *
     * @param message the feedback message to be displayed.
     */

    // Helper Method: Updates feedback in the feedbackFlow
    public void updateFeedback(String message) {
        Text feedbackText = new Text("\n" + message);
        feedbackText.setFill(Color.GRAY);
        feedbackText.setFont(Font.font("Arial", 24));
        feedbackFlow.getChildren().clear();
        feedbackFlow.getChildren().add(feedbackText);
    }
    /**
     * Checks if the input area is empty. If so, it updates the feedback area.
     *
     * @return true if the input area is empty, false otherwise.
     */
    // Helper Method: Check if the input text area is empty
    private boolean isInputEmpty() {
        if (Objects.equals(inputArea.getText().trim(), "")) {
            updateFeedback("Input is empty");
            return true;
        }
        return false;
    }    /**
     * Toggles between light and dark themes.
     * This method updates the stylesheets and theme-related images in the UI.
     *
     * @param actionEvent the event triggered by the theme toggle button.
     */

    // Switch between Light and Dark Modes
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
     * Decompresses the content in the input area and displays it in the output area.
     * It uses a helper method to perform the decompression.
     *
     * @param actionEvent the event triggered by the "Decompress" button.
     */
    // Handles Decompression
    public void decompressFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.decompress(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File decompressed");
        }
    }
    /**
     * Compresses the content in the input area and displays it in the output area.
     * It uses a helper method to perform the compression.
     *
     * @param actionEvent the event triggered by the "Compress" button.
     */
    // Handles Compression
    public void compressFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.compress(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File compressed");
        }
    }   /**
     * Converts XML content in the input area to JSON format and displays the result in the output area.
     *
     * @param actionEvent the event triggered by the "Convert to JSON" button.
     */

    // Handles XML to JSON Conversion
    public void convertToJson(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.xmltoJson(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File converted to JSON");
        }
    }
    /**
     * Minifies the content in the input area and displays it in the output area.
     *
     * @param actionEvent the event triggered by the "Minify" button.
     */
    // Handles Minification
    public void minifyFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.minify(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File minified");
        }
    }
    /**
     * Formats the content in the input area and displays it in the output area.
     *
     * @param actionEvent the event triggered by the "Format" button.
     */
    // Handles Formatting
    public void formatFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] output = Functions.format(inputLines);
            outputArea.setText(String.join("\n", output));
            updateFeedback("File formatted");
        }
    }
    /**
     * Repairs the content in the input area by checking for errors and correcting them if necessary.
     *
     * @param actionEvent the event triggered by the "Repair" button.
     */
    // Handles Repair
    public void repairFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] errors = Functions.check(inputLines);
            if (errors.length==0) {
                outputArea.setText("");
                updateFeedback("File is valid");
            } else {
                String[] output = Functions.repair(inputLines);
                outputArea.setText(String.join("\n", output));
                updateFeedback("File repaired");
            }
        }
    }    /**
     * Validates the XML file in the input area and shows whether it is valid or contains errors.
     *
     * @param actionEvent the event triggered by the "Validate" button.
     */

    // Validate XML File
    public void validateFile(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            String[] inputLines = inputArea.getText().split("\n");
            String[] errors = Functions.check(inputLines);
         //   outputArea.setText(String.join("\n", errors));
           // updateFeedback("File is checked");
            if (errors.length==0) {
                outputArea.setText("");
                updateFeedback("File is valid");
            } else {
                outputArea.setText(String.join("\n", errors));
                updateFeedback("File is not valid. Please repair it.");
            }
        }
    }
    /**
     * Imports a file into the input area.
     * Allows the user to choose a file using a file chooser dialog.
     *
     * @param actionEvent the event triggered by the "Import File" button.
     */
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
    /**
     * Saves the current output area content to the selected file.
     *
     * @param actionEvent the event triggered by the "Save" button.
     */
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
    /**
     * Saves the content from the output area into a file chosen by the user.
     * The user can choose between different file formats such as XML, text, or JSON.
     *
     * @param actionEvent The event triggered by the user action.
     */
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
    /**
     * Clears the input and output areas and resets the font size, effectively creating a new file.
     *
     * @param actionEvent The event triggered by the user action.
     */
    // Create a New File
    public void createNewFile(ActionEvent actionEvent) {
        inputArea.clear();
        outputArea.clear();
        fontSize = 12;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateFeedback("New file created. Enter or import content.");
    }    /**
     * Increases the font size for the input and output areas.
     *
     * @param actionEvent The event triggered by the user action.
     */

    // Zoom In
    public void zoomIn(ActionEvent actionEvent) {
        fontSize++;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateLineNumbers(inputArea,lineNumbers1);
        updateLineNumbers(outputArea,lineNumbers11);
    }
    /**
     * Decreases the font size for the input and output areas.
     *
     * @param actionEvent The event triggered by the user action.
     */
    // Zoom Out
    public void zoomOut(ActionEvent actionEvent) {
        fontSize--;
        inputArea.setFont(Font.font(fontSize));
        outputArea.setFont(Font.font(fontSize));
        updateLineNumbers(inputArea,lineNumbers1);
        updateLineNumbers(outputArea,lineNumbers11);
    }
    /**
     * Configures the file chooser with different file extension filters,
     * allowing the user to choose from various file types.
     */
    public  void setupFileChooser() {
      // fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Compressed Files", "*.comp"),
                new FileChooser.ExtensionFilter("json Files", "*.json"),
                new FileChooser.ExtensionFilter("image", "*.jpg")
                );
    }
    /**
     * Initializes the editor by setting up the text areas, scroll bars, and line number display.
     * Also, binds the vertical scroll values for synchronization between input/output areas.
     *
     * @param url The location of the FXML file.
     * @param resourceBundle The resource bundle for localization.
     */
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


    }  /**
     * Updates the line numbers displayed in the specified VBox based on the current text in the TextArea.
     *
     * @param textArea The TextArea whose lines are being counted.
     * @param lineNumbers The VBox displaying the line numbers.
     */
    private void updateLineNumbers(TextArea textArea, VBox lineNumbers) {
        // Clear current line numbers
        lineNumbers.getChildren().clear();

        // Count the lines in the TextArea
        int lineCount = textArea.getText().split("\n", -1).length;

        // Add line numbers
        for (int i = 1; i <= lineCount; i++) {
            Text lineNumber = new Text(String.valueOf(i));
            lineNumber.setFont(textArea.getFont());
            lineNumber.setFill(Color.valueOf("#ac72ff"));
            lineNumbers.getChildren().add(lineNumber);
        }
    }
    /**
     * Opens a search window where the user can search for content within the input area.
     *
     * @param actionEvent The event triggered by the user action.
     * @throws IOException If an error occurs while loading the search window.
     */
    public void search_but(ActionEvent actionEvent) throws IOException {
        if (!isInputEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("search.fxml"));
            Parent root = fxmlLoader.load();
            SearchController main=fxmlLoader.getController();
            main.draft.setText(inputArea.getText());
            main.output.getChildren().add(new Text(inputArea.getText()));
            Scene startScene = new Scene(root, 800, 450);
            Stage stage = new Stage();
            stage.setScene(startScene);
            stage.show();
            stage.setTitle("Search");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
            stage.setResizable(false);
            stage.centerOnScreen();
            updateFeedback("");
        }

    }    /**
     * Opens a new window to display a graph based on the content in the input area.
     * Allows the user to save the generated graph.
     *
     * @param actionEvent The event triggered by the user action.
     * @throws IOException If an error occurs while creating the graph.
     */

    public void graph_but(ActionEvent actionEvent) throws IOException {
        if (!isInputEmpty()) {
            Pane root = new Pane();
            String[] inputLines = inputArea.getText().split("\n");
            SocialNetworkGraph output = Functions.draw(inputLines);
            Canvas graphCanvas = output.drawGraphOnCanvas(800, 450);
            root.getChildren().add(graphCanvas);
            // Create a "Save" button
            Button saveButton = new Button("Save");
            saveButton.setLayoutX(700); // Position the button on the Pane (X-coordinate)
            saveButton.setLayoutY(400); // Position the button on the Pane (Y-coordinate)
            root.getChildren().add(saveButton);

            // Set up button action to save the Canvas
            saveButton.setOnAction(e -> {
                    SocialNetworkGraph.saveCanvasAsJPG(graphCanvas); // Call the save function
                 });

            // Set up the scene and stage
            Scene startScene = new Scene(root, 800, 450);
            Stage stage = new Stage();
            stage.setScene(startScene);
            stage.show();
            stage.setTitle("Graph");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
            stage.setResizable(false);
            stage.centerOnScreen();
            updateFeedback("file is drawn");
        }
    }

    /**
     * Opens a network analysis window where the user can analyze the content of the input area.
     *
     * @param actionEvent The event triggered by the user action.
     * @throws IOException If an error occurs while loading the network analysis window.
     */

    public void network_but(ActionEvent actionEvent) throws IOException {
        if (!isInputEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("networkAnalysis.fxml"));
            Parent root = fxmlLoader.load();
            NetworkAnalysisController main=fxmlLoader.getController();
            main.draft.setText(inputArea.getText());
            Scene startScene = new Scene(root, 800, 450);
            Stage stage = new Stage();
            stage.setScene(startScene);
            stage.show();
            stage.setTitle(" Network Analysis");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/photos/logo3.png")).toExternalForm()));
            stage.setResizable(false);
            stage.centerOnScreen();
            updateFeedback("");
        }

    }
}
