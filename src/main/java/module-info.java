module com.xml.editor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens com.xml.editor to javafx.fxml;
    exports com.xml.editor;
}