package me.challenges.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label l = new Label("AHAHAHHA");
        Scene scene = new Scene(l);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
