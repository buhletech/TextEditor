module com.texteditor.texteditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.texteditor.texteditor to javafx.fxml;
    exports com.texteditor.texteditor;
}