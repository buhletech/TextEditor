package com.texteditor.texteditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TextEditorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TextEditorApplication.class.getResource("texteditor-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();
    }
}
