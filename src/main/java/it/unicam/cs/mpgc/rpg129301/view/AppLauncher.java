package it.unicam.cs.mpgc.rpg129301.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppLauncher extends Application {
    /**
     * The main entry point for all JavaFX applications. The start method is called after the init method has returned.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     * Applications may create other stages, if needed, but they will not be primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the terminal view, create a scene with it, and set it on the primary stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/terminal.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("RPG-OS Terminal v2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}