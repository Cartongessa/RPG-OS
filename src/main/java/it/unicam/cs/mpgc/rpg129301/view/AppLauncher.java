package it.unicam.cs.mpgc.rpg129301.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/terminal.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("RPG-OS Terminal v2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}