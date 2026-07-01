package com.texteditor.texteditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextEditorController {
    //where we store all the recent files
    private List<File> files = new ArrayList<>();

    //track the current file
    private File currentFile;
    @FXML
    private TextArea textArea;

    @FXML
    private MenuItem newFile;

    public void initialize() {
        //FileChooser allows you to select one or more files via a file explorer from the user's local computer.
        FileChooser fileChooser = new FileChooser();

        //when the file menuitem is clicked a file is set to initially new.txt
        newFile.setOnAction(event -> {
            //The initial directory displayed by the filechooser is Documents
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

            fileChooser.setInitialFileName("new.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            //Once the newFile is click a Show Save/Open Dialog doesn't show up
            //A new Window is created
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/texteditor/texteditor/texteditor-view.fxml"));
            try {
                VBox vbox = fxmlLoader.load();
                Scene scene = new Scene(vbox, 620, 440);
                Stage stage = (Stage) textArea.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void handleOpenFile(){
        //FileChooser allows you to select one or more files via a file explorer from the user's local computer.
        FileChooser fileChooser = new FileChooser();

        //The initial directory displayed by the dialog is Documents so this is where you can open the file first because of the (new File(etc.))
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

        //The name of the file is set to open.txt
        fileChooser.setInitialFileName("open.txt");
        //The file extensions underneath are these. Basically this is what is supported
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        //The textArea is fetched from the window because that's how we identify which window we want shown
        Stage stage = (Stage) textArea.getScene().getWindow();

        //The file is the selected file
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            try {
                //save the file that was opened in file
                currentFile = file;
                //reads everything inside the file
                String content = new String(Files.readAllBytes(file.toPath()));
                textArea.setText(content);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void handleSaveAsFile(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));
        fileChooser.setInitialFileName("new.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), new FileChooser.ExtensionFilter("HTML Files", "*.html"));

        Stage stage = (Stage) textArea.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        //if user clicks cancel on the dialog, file will be null so that's why the error handling was put for null
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile() throws IOException {
        //There is no dialog that opens up
        //if currentFile is empty
        if (currentFile == null) {
            //No file open yet, create one with the default name
            currentFile = new File(System.getProperty("user.home") + "\\Documents\\new.txt");
        }
        //We'e getting the words typed from the textArea and we're saving them
        FileWriter writer = new FileWriter(currentFile);
        writer.write(textArea.getText());
        writer.close();

    }

    public void moveToRecycle() throws IOException {
        //if file is not null or empty
        if(currentFile != null && currentFile.exists()){
            //delete the current file
            Files.delete(currentFile.toPath());
            //set the text area to nothing
            textArea.clear();
            //the current file is nothing
            currentFile = null;
        }

        //Navigate to recycle view
        FXMLLoader fxmlLoader = new  FXMLLoader(getClass().getResource("/com/texteditor/texteditor/texteditor-view.fxml"));
        VBox vbox = fxmlLoader.load();

        Scene scene = new Scene(vbox, 620, 440);
        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void exitWindow(){
        //Gets the window and textArea is inside that window so that's what we're looking instead of referencing the entire thing
        //we get what's inside the window and the window itself then we close it
        ((Stage)(textArea.getScene().getWindow())).close();
    }

    public void copyFile(){


    }
}